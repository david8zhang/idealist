package com.example.david_000.idealist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by david_000 on 7/5/2015.
 */
public class PostActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        EditText titleBox= (EditText)findViewById(R.id.post_idea_title);
        String ideaTitle = titleBox.getText().toString();

        EditText categoryBox = (EditText)findViewById(R.id.post_idea_category);
        String ideaCategory = categoryBox.getText().toString();

        EditText descriptionBox = (EditText)findViewById(R.id.post_idea_text);
        String ideaText = descriptionBox.getText().toString();

        Button button = (Button)findViewById(R.id.postIdea);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
