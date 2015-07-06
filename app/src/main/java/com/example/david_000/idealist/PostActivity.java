package com.example.david_000.idealist;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by david_000 on 7/5/2015.
 */
public class PostActivity extends AppCompatActivity {

    final String myTag = "DocsUpload";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);


        Button button = (Button)findViewById(R.id.postIdea);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        EditText titleBox= (EditText)findViewById(R.id.post_idea_title);
                        final String titleString = titleBox.getText().toString();

                        EditText categoryBox = (EditText)findViewById(R.id.post_idea_category);
                        final String catString = categoryBox.getText().toString();

                        EditText descriptionBox = (EditText)findViewById(R.id.post_idea_text);
                        final String textString = descriptionBox.getText().toString();

                        String fullURL = "https://docs.google.com/forms/d/1LPb6g2ON_ft5x1046fcDZcpz3DFm8gk_25y1Wnw5CoQ/formResponse";
                        HttpRequest mReq = new HttpRequest();
                        String ideaTitle = titleString;
                        String ideaCategory = catString;
                        String ideaText = textString;
                        String data = null;
                        try {
                            data = "entry.1868572889=" + URLEncoder.encode(ideaTitle, "UTF-8") + "&" +
                                          "entry.155408609=" + URLEncoder.encode(ideaCategory, "UTF-8") + "&" +
                                          "entry.1889598341=" + URLEncoder.encode(ideaText, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String response = mReq.sendPost(fullURL, data);
                        Log.i(myTag, response);
                    }
                });
                t.start();
            }
        });
    }
}
