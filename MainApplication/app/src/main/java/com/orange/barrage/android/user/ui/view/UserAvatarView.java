package com.orange.barrage.android.user.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.misc.ImageUtil;
import com.orange.barrage.android.util.misc.image.RoundedTransformation;
import com.orange.protocol.message.UserProtos;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * Created by pipi on 15/3/5.
 */
public class UserAvatarView extends ImageView {

    private Context mContext;
    UserProtos.PBUser user;

    public UserAvatarView(Context context){
        super(context);
        mContext = context;
    }

    public UserAvatarView(Context context , AttributeSet attrs) {
        this(context , attrs , 0);

    }

    public UserAvatarView(Context context , AttributeSet attrs ,  int defStyle){
        super(context , attrs , defStyle);
        mContext = context;
    }



    public void loadUser(UserProtos.PBUser user){

//        setBorderWidth(10);
//        setBorderColor(Color.RED);
//        setSelectorStrokeWidth(10);
//        setSelectorStrokeColor(Color.BLUE);

        // set user data
        this.user = user;
        // load avatar image
        setAvartUrl(user.getAvatar());

    }
    public void setAvartUrl(String url){
        setAvartUrl(url , null);
    }

    public void setAvartUrl(String url , Callback callback){

        // TODO get height and width from layout configuration
//        int width = 100;
//        int height = 100;
        int borderWidth = 8;
        if(url == null || url.trim().length() == 0) {
//            setImageResource(R.drawable.y_morentouxiang);

            Bitmap bitmap = ImageUtil.getRoundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.y_morentouxiang));

            setImageBitmap(bitmap);
            setVisibility(View.VISIBLE);
            return;
        }

        //MessageCenter.postErrorMessage("ni hao wo bei diao yong");
      /*  Ln.e("头像加载照片"+url);//加载照片*/
        Picasso.with(mContext)
                .load(url)
                .resize(300,300)
                .transform(new RoundedTransformation(borderWidth))
                .placeholder(R.drawable.y_morentouxiang)       // TODO change to right default
                .error(R.drawable.y_morentouxiang)           // TODO change to right default
                .into(this , callback);


        //set visible
        setVisibility(View.VISIBLE);

    }




//    @Deprecated
//    Callback mCallback = new Callback() {
//        @Override
//        public void onSuccess() {
//            Bitmap bitmap = ((BitmapDrawable)getDrawable()).getBitmap();
//            bitmap = ImageDealTools.getRoundedCornerBitmap(bitmap);
//            setImageBitmap(bitmap);
//        }
//
//        @Override
//        public void onError() {
//
//        }
//    };

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

//        ImageDealTools.DrawRoundStroke(canvas , getWidth() , getHeight() ,Color.WHITE);

////        ImageDealTools.DrawRoundStroke(canvas , getWidth() , getHeight() ,Color.WHITE);

//    }


}
