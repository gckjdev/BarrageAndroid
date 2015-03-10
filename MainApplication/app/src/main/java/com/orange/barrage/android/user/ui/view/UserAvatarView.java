package com.orange.barrage.android.user.ui.view;

import android.app.ActionBar;
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
import com.orange.barrage.android.util.misc.image.RoundedTransformation;
import com.orange.protocol.message.UserProtos;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * Created by pipi on 15/3/5.
 */
public class UserAvatarView extends ImageButton {

    private Context mContext;
    UserProtos.PBUser user;
    int borderWidth = 4;

    public UserAvatarView(Context context , AttributeSet attrs) {
        super(context , attrs);
        mContext = context;
    }

    public void loadUser(UserProtos.PBUser user){

        // set user data
        this.user = user;

        // load avatar image
        setAvartUrl(user.getAvatar());
    }

    public void setAvartUrl(String url){

        int width = 100;
        int height = 100;

        Picasso.with(mContext)
                .load(url)
                .transform(new RoundedTransformation(width/2, borderWidth))
                .resize(width, height)
                .placeholder(R.drawable.tab_home)       // TODO change to right default
                .error(R.drawable.tab_friend)           // TODO change to right default
                .into(this , null);
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
//        ImageDealTools.DrawRoundStroke(canvas , getWidth() , getHeight() ,Color.WHITE);
    }


}
