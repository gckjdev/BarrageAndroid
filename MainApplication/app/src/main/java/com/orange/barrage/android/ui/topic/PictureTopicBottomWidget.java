package com.orange.barrage.android.ui.topic;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Rollin on 2014/11/30.
 */
@Deprecated
public class PictureTopicBottomWidget extends LinearLayout{

    private PictureTopicContainer mContainer;

    public PictureTopicBottomWidget(Context context, PictureTopicContainer container){
        super(context);
        mContainer = container;

        TextView view = new TextView(context);
        view.setText("Bottom Container");
        this.addView(view);

        Button button = new Button(context);
        button.setText("Play");
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getMainWidget().play();
            }
        });
        this.addView(button);
    }

    private PictureTopicMainWidget getMainWidget() {
        return mContainer.getMainWidget();
    }
}
