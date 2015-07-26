package com.example.gaming.interactivestory.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaming.interactivestory.Model.Page;
import com.example.gaming.interactivestory.Model.Story;
import com.example.gaming.interactivestory.R;


public class StoryActivity extends ActionBarActivity {

    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mCurrentpage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));

        if(mName == null)
        {
            mName = "Friend";
        }
        Log.d(TAG, mName);

        mImageView = (ImageView)findViewById(R.id.storyImageView);
        mTextView = (TextView)findViewById(R.id.storyTextView);
        mChoice1 = (Button)findViewById(R.id.choiceButton1);
        mChoice2 = (Button)findViewById(R.id.choiceButton2);

        loadPage(0);
    }

    private void loadPage(int choice)
    {
        mCurrentpage = mStory.getPage(choice);

        Drawable drawable = getResources().getDrawable(mCurrentpage.getImageId());
        mImageView.setImageDrawable(drawable);

        String pageText = mCurrentpage.getText();
        //adds name if placeholder included. Otherwise not added.
        pageText = String.format(pageText, mName);
        mTextView.setText(mCurrentpage.getText());

        if(mCurrentpage.isFinal())
        {
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("PLAY AGAIN");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else {

            mChoice1.setText(mCurrentpage.getChoice1().getText());
            mChoice2.setText(mCurrentpage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentpage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentpage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
    }

}
