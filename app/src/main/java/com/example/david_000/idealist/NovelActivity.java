package com.example.david_000.idealist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david_000 on 7/7/2015.
 */
public class NovelActivity extends AppCompatActivity implements ObservableScrollViewCallbacks{

    private ObservableListView listView;
    private NovelListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novel_activity);

        listView = (ObservableListView)findViewById(R.id.novel_list);
        listView.setScrollViewCallbacks(this);

        feedItems = new ArrayList<FeedItem>();
        listAdapter = new NovelListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(NovelActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        feedItems.remove(positionToRemove);
                        listAdapter.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });

        toolbar = (Toolbar)findViewById(R.id.novel_toolbar);
        setSupportActionBar(toolbar);


        listView.setOnTouchListener(new OnSlidingTouchListener(this) {
            public boolean onSlideRight() {
                System.out.println("slide left");
                Intent novelIntent = new Intent(NovelActivity.this, MainActivity.class);
                novelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(novelIntent);
                return true;
            }

            public boolean onSlideLeft() {
                System.out.println("slide right");
                return true;
            }

        });

        getSupportActionBar().setTitle(null);

        fetchData();

        ImageButton post_button = (ImageButton)findViewById(R.id.newNovel);
        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postNovelIntent = new Intent(NovelActivity.this, PostNovelActivity.class); //TODO Clean up PostActivity so as to trivialize the need for a separate activity
                NovelActivity.this.startActivity(postNovelIntent);
            }
        });

    }

    private void fetchData() {
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1lWhkH0Pe2pFqwJ6f_eK1Lb-ZJ1Ig4Hi8oPFFGrhllGE&tq=select%20B%2C%20C%2C%20D"); //TODO: Later build, allow user to enter in the link to their spreadsheet

        //TODO: In later build, allow user to dynamically create new spreadsheets
    }

    private void processJson(JSONObject object){
        try{
            JSONArray rows = object.getJSONArray("rows");

            System.out.println("row: " + rows);
            for(int r = rows.length() - 1; r >= 0; r--){
                FeedItem item = new FeedItem();

                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String novelConcept = columns.getJSONObject(0).getString("v");
                String novelCategory = columns.getJSONObject(1).getString("v");
                String novelSummary = columns.getJSONObject(2).getString("v");

                System.out.println(novelConcept);
                System.out.println(novelCategory);
                System.out.println(novelSummary);

                item.setIdeaTitle(novelConcept);
                item.setIdeaCategory(novelCategory);
                item.setIdeaText(novelSummary);

                if(novelConcept.equals("test") || novelConcept.equals("null")){
                    System.out.println("Shit outta luck");
                } else {
                    feedItems.add(item);
                }
            }
            listAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    //Toolbar scrolling animations -> this will hide the toolbar when the feed is scrolled up
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
            if (toolbarIsShown()) {
                hideToolbar();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (toolbarIsHidden()) {
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown(){
        return ViewHelper.getTranslationY(toolbar) == 0;
    }

    private boolean toolbarIsHidden(){
        return ViewHelper.getTranslationY(toolbar) == -toolbar.getHeight();

    }
    private void showToolbar(){
        moveToolbar(0);
    }

    private void hideToolbar(){
        moveToolbar(-toolbar.getHeight());
    }

    private void moveToolbar(float toTranslationY){
        if (ViewHelper.getTranslationY(toolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(toolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(toolbar, translationY);
                ViewHelper.setTranslationY((View) listView, translationY);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ((View) listView).getLayoutParams();
                lp.height = (int) -translationY + getScreenHeight() - lp.topMargin;
                ((View) listView).requestLayout();
            }
        });
        animator.start();
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

}
