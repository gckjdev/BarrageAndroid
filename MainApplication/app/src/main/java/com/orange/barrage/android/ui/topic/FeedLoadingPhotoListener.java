package com.orange.barrage.android.ui.topic;

import com.squareup.picasso.Callback;

import java.util.Vector;

/**
 * Created by youjiannuo on 2015/4/1.
 * 监听图片是否加载完成
 */
public class FeedLoadingPhotoListener {


    private boolean isStart = false;

    Vector<Callback> mCallbacks = new Vector<>();

    private OnLoadPhotoListener mOnLoadPhotoListener;
    private OnLoadPhotoItemListener mOnLoadPhotoItemListener;

    public Callback buildCallback(){
        return buildCallback(null);
    }

    public Callback buildCallback(final Object obj){

        Callback callback = new Callback() {
            Object mObj = obj;
            @Override
            public void onSuccess() {
                remove(this);
                onCallbak( 0 , mObj);
            }

            @Override
            public void onError() {
                remove(this);
                onCallbak( 1 , mObj);
            }
        };

        mCallbacks.add(callback);

        return callback;
    }

    private void onCallbak(int postion , Object obj){
        if(mOnLoadPhotoItemListener == null) return;
        if(postion == 0){
            mOnLoadPhotoItemListener.onSuccess(obj);
        }else {
            mOnLoadPhotoItemListener.onErro(obj);
        }

    }

    public void remove(Callback callback){
        mCallbacks.remove(callback);

        if(isStart && mOnLoadPhotoListener != null && mCallbacks.size() == 0){
            mOnLoadPhotoListener.onFinish();
        }
    }

    public void clear(){
        mCallbacks.clear();
    }

    public void setOnLoadPhotoListener(OnLoadPhotoListener l){
        mOnLoadPhotoListener = l;
    }

    public void setOnLoadPhotoItemListener(OnLoadPhotoItemListener l){
        mOnLoadPhotoItemListener = l;
    }

    //开始监听的时候，需要调到这个方法，反正不会监听得到的
    public void startListener(){
        isStart = true;
    }

    public interface OnLoadPhotoListener{

        public void onFinish();

    }

    public interface OnLoadPhotoItemListener{
        public void onSuccess(Object obj);

        public void onErro(Object obj);
    }

}
