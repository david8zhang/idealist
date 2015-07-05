package com.example.david_000.idealist;


import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks{

    AsyncResult callback;
    private ObservableListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ObservableListView)findViewById(R.id.list);
        listView.setScrollViewCallbacks(this);

        feedItems = new ArrayList<FeedItem>();
        listAdapter = new FeedListAdapter(this, feedItems);

        listView.setAdapter(listAdapter);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        fetchData();
    }

    private void fetchData() {
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1-03IU1yVAZgl2-yIaf_b73xbMHnUAWmcnKv-cGkp75Y");
    }

    private void processJson(JSONObject object){
        try{
            JSONArray rows = object.getJSONArray("rows");
            System.out.println("row: " + rows);
            for(int r = 1; r < rows.length(); r++){
                System.out.println(rows.length());
                FeedItem item = new FeedItem();

                JSONObject row = rows.getJSONObject(r);
                System.out.println("row: " + row);
                JSONArray columns = row.getJSONArray("c");
                System.out.println("columns: " + columns);

                String ideaTitle = columns.getJSONObject(0).getString("v");
                String ideaCategory = columns.getJSONObject(1).getString("v");
                String ideaText = columns.getJSONObject(2).getString("v");

                System.out.println(ideaTitle);
                System.out.println(ideaCategory);
                System.out.println(ideaText);

                item.setIdeaTitle(ideaTitle);
                item.setIdeaCategory(ideaCategory);
                item.setIdeaText(ideaText);

                if(item == null){
                    System.out.print("shit outta luck");
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
