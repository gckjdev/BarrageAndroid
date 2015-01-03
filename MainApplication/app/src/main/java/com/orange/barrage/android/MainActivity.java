package com.orange.barrage.android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.orange.barrage.android.data.dummy.PictureTopicDummyDataGen;
import com.orange.barrage.android.ui.TopicPage;
import com.orange.barrage.android.ui.topic.PictureTopicAdapter;
import com.orange.barrage.android.util.ContextManager;

public class MainActivity extends ActionBarActivity {

    private String TAG = "MainActivity";

    private ImageButton mShowTopicPageButton ;
    private ImageButton mShowPersonalPageButton;

    private LinearLayout mMainLayout;

    private TopicPage mTopicPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e("TAG", "uncaught exception thrown", ex);
            }
        });
        //FIXME: dummy data
        PictureTopicDummyDataGen.init();

        setContentView(R.layout.activity_main);

        ContextManager.init(this);

        initComponents();
        initEvnets();
    }


    public void initComponents(){
        mShowTopicPageButton = (ImageButton) findViewById(R.id.btn_show_topic_page);
        mShowPersonalPageButton = (ImageButton) findViewById(R.id.btn_show_peronal_page);
        mMainLayout = (LinearLayout) findViewById(R.id.MainLayout);



    }

    private void hideAllViews(ViewGroup parent){
        int count = parent.getChildCount();
        for(int i=0;i<count;i++){
            View view = parent.getChildAt(i);
            view.setVisibility(View.INVISIBLE);
        }
    }
    private void initEvnets(){
        mShowTopicPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mTopicPage==null) {
                    mTopicPage = new TopicPage(ContextManager.getContext());
                    mTopicPage.setAdapter(new PictureTopicAdapter());
                    mMainLayout.addView(mTopicPage);
                    mTopicPage.loadData();
                }
                hideAllViews(mMainLayout);
                mTopicPage.setVisibility(View.VISIBLE);
            }
        });

        mShowPersonalPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAllViews(mMainLayout);
                Toast.makeText(ContextManager.getContext(), "Show personal page", Toast.LENGTH_SHORT);
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}