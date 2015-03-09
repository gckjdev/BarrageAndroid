package com.orange.barrage.android.feed.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.RequestCodes;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * Created by Rollin on 2015/1/31.
 */
public class FeedCreateActivity extends RoboFragmentActivity {


//    @InjectView(R.id.picture_image_view)
//    private ImageView mFeedImage;
//
//    @InjectView(R.id.cancel_button)
//    private Button mCancelButton;
//
//    @InjectView(R.id.next_button)
//    private Button mNextButton;
//
//    @InjectView(R.id.subtitle_text)
//    private TextView mSubtitleText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed_create);
//        initComponents();
//
//        initEvents();
//    }
//
//    private void initComponents() {
//        //Bitmap photo = getIntent().getParcelableExtra("photo");
//        String path = getIntent().getStringExtra("path");
//        Bitmap photo = BitmapFactory.decodeFile(path);
//        mFeedImage.setImageBitmap(photo);
//    }
//
//    private void initEvents() {
//        mNextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FeedCreateActivity.this, FeedCreateSelectFriendActivity.class);
//
//                //FIXME: random some default or ask user to provider comment.
//                String value = mSubtitleText.getText()!=null ? mSubtitleText.getText().toString():"";
//                intent.putExtra("subtitle",value);
//                intent.putExtra("path", getIntent().getStringExtra("path"));
//                //intent.putExtra("bitmap", getIntent().getParcelableExtra("photo"));
//                startActivityForResult(intent, RequestCodes.FEED_CREATE_SELECT_FRIENDS);
//            }
//        });
//
//        mCancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode== RequestCodes.FEED_CREATE_SELECT_FRIENDS && data != null){
//            setResult(Activity.RESULT_FIRST_USER, data);
//            finish();
//        }
//    }
}
