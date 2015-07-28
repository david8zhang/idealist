package com.example.david_000.idealist;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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


        Button postButton = (Button)findViewById(R.id.postIdea);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView)findViewById(R.id.lightbulb_image);
                image.setImageResource(R.drawable.lightbulb_post_lit);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView image = (ImageView) findViewById(R.id.lightbulb_image);
                        image.setImageResource(R.drawable.lightbulb_post);
                    }
                }, 2000);
                Thread t = new Thread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        final EditText titleBox= (EditText)findViewById(R.id.post_idea_title);
                        final String titleString = titleBox.getText().toString();

                        final EditText categoryBox = (EditText)findViewById(R.id.post_idea_category);
                        final String categoryString = categoryBox.getText().toString();

                        final EditText descriptionBox = (EditText)findViewById(R.id.post_idea_text);
                        final String descriptionString = descriptionBox.getText().toString();

                        // if any of the Strings are empty, do not make new entry and display a message
                        if (titleString.equals("") || categoryString.equals("") || descriptionString.equals(""))
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PostActivity.this, "All fields are necessary", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }

                        String fullURL = "https://docs.google.com/forms/d/1LPb6g2ON_ft5x1046fcDZcpz3DFm8gk_25y1Wnw5CoQ/formResponse";
                        HttpRequest mReq = new HttpRequest();
                        String ideaTitle = titleString;
                        String ideaCategory = categoryString;
                        String ideaDescription = descriptionString;
                        String data = null;
                        try {
                            data = "entry.1868572889=" + URLEncoder.encode(ideaTitle, "UTF-8") + "&" +
                                          "entry.155408609=" + URLEncoder.encode(ideaCategory, "UTF-8") + "&" +
                                          "entry.1889598341=" + URLEncoder.encode(ideaDescription, "UTF-8");
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
                                Toast.makeText(PostActivity.this, "Idea Uploaded!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                t.start();
            }
        });

        ImageButton cancelButton = (ImageButton)findViewById(R.id.cancel_post);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(PostActivity.this, MainActivity.class);
                PostActivity.this.startActivity(cancelIntent);
            }
        });
    }
}
