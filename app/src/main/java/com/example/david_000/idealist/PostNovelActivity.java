package com.example.david_000.idealist;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by david_000 on 7/5/2015.
 */
public class PostNovelActivity extends AppCompatActivity {

    final String myTag = "DocsUpload";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_novel_activity);


        Button button = (Button)findViewById(R.id.postIdea);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView image = (ImageView)findViewById(R.id.lightbulb_image);
                    }
                }, 2000);
                Thread t = new Thread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        final EditText titleBox= (EditText)findViewById(R.id.post_idea_title);
                        final String titleString = titleBox.getText().toString();

                        final EditText categoryBox = (EditText)findViewById(R.id.post_idea_category);
                        final String catString = categoryBox.getText().toString();

                        final EditText descriptionBox = (EditText)findViewById(R.id.post_idea_text);
                        final String textString = descriptionBox.getText().toString();

                        String fullURL = "https://docs.google.com/forms/d/1a1xshlLA79WO3TL8Kws84mCHT863ZLbabo4UsMoww2Y/formResponse";
                        HttpRequest mReq = new HttpRequest();
                        String ideaTitle = titleString;
                        String ideaCategory = catString;
                        String ideaText = textString;
                        String data = null;
                        try {
                            data = "entry.1516079358=" + URLEncoder.encode(ideaTitle, "UTF-8") + "&" +
                                    "entry.830188614=" + URLEncoder.encode(ideaCategory, "UTF-8") + "&" +
                                    "entry.1248400614=" + URLEncoder.encode(ideaText, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String response = mReq.sendPost(fullURL, data);
                        Log.i(myTag, response);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                titleBox.setText("");
                                categoryBox.setText("");
                                descriptionBox.setText("");
                                Toast.makeText(PostNovelActivity.this, "Novel Uploaded!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                t.start();
            }
        });
    }
}
