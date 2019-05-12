package com.tabo.interactivestory.ui;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tabo.interactivestory.R;
import com.tabo.interactivestory.model.Page;
import com.tabo.interactivestory.model.Story;

import java.util.Stack;


public class StoryActivity extends AppCompatActivity {

    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story story;



    private String name;
    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;
    private Stack<Integer> pageStack = new Stack<Integer>();

    /**
     * When reusing Activities or Views, we need to make sure that users can navigate backwards
     * through our app in an expected manner. Often this means maintaining a custom back stack,
     * where we control what is displayed when they hit the Back button.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.storyImageView);
        storyTextView = findViewById(R.id.storyTextView);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);



        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.key_name));

        if(name == null || name.isEmpty()){
            name = "Friend";
        }
        Log.d(TAG,name);

        story = new Story();
        loadPage(0);

    }

    private void loadPage(int pageNumber) {

        pageStack.push(pageNumber);

        final Page page = story.getPage(pageNumber);

        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);

        String pageText = getString(page.getTextId());

        // Add the name if the placeholder is included.
        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);


        if (page.isFinalPage()) {
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(getString(R.string.play_again_button_text));
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } else {

            loadButtons(page);
        }
    }

    private void loadButtons(final Page page) {
        choice1Button.setText(page.getChoice1().getTextId());
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });
        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
    }

    @Override
    public void onBackPressed() {

        pageStack.pop(); // for page stack when removing from the stack

        if(pageStack.isEmpty()){
            super.onBackPressed();
        }else{
            loadPage(pageStack.pop());
        }
    }
}
