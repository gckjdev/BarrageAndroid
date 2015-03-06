package com.orange.barrage.android.user.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.imagecdn.ImageDealTools;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * Created by pipi on 15/3/5.
 */
public class UserAvatarView extends ImageButton {

    private Context mContext;

    public UserAvatarView(Context context , AttributeSet attrs) {
        super(context , attrs);
        mContext = context;
    }

    public void setImageBitmap(String url){

        Picasso.with(mContext).load(url).placeholder(R.drawable.tab_home).error(R.drawable.tab_friend).into(this , mCallback);
    }

    Callback mCallback = new Callback() {
        @Override
        public void onSuccess() {
            Bitmap bitmap = ((BitmapDrawable)getDrawable()).getBitmap();

            bitmap = ImageDealTools.getRoundedCornerBitmap(bitmap);

            setImageBitmap(bitmap);

        }

        @Override
        public void onError() {

        }
    };


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ImageDealTools.DrawRoundStroke(canvas , getWidth() , getHeight() ,Color.WHITE);
    }


    /**
     * 点击头像
     * @param v
     */
    public void onClick(View v){

    }


}
