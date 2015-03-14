package com.orange.barrage.android.feed.mission;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by youjiannuo on 2015/3/7.
 */
public class PhotoAndCamera {


    private static final int PHOTOHRAPH = 1;// 拍照
    private static final int PHOTOZOOM = 2; // 缩放
    private static final int PHOTORESOULT = 3;// 结果
    private  static final int NONE = 0;
    private static final String IMAGE_UNSPECIFIED = "image/*";

    private Activity mActivity;



    public PhotoAndCamera(Activity context){
        this.mActivity = context;

    }


    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
            Environment.getExternalStorageDirectory(), "temp.jpg")));
        mActivity.startActivityForResult(intent, PHOTOHRAPH);
    }

    public void choosePhoto(){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);
        mActivity.startActivityForResult(intent, PHOTOZOOM);

    }


    /*
	 * 将这些方法放在Activity下面的onActivityResult里
	 */
    public void  getPicture(int requestCode, int resultCode, Intent data ,onGetPhotoCallback l){

        boolean isSuccess = false;
        Bitmap photo = null;
//        if (resultCode == NONE) {
//
//            return;
//        }
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    +"/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        // 读取相册缩放图片
        if (data != null && requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (data != null && requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                 photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
                // 100)压缩文件
                isSuccess = true;
//                return photo;
            }
        }

        if(l != null){
            if (isSuccess) l.onSuccess(photo);
            else l.onErro();
        }

//        return null;
    }

    //截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        mActivity.startActivityForResult(intent, requestCode);
    }


    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");

        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        mActivity.startActivityForResult(intent, PHOTORESOULT);

//        File picture = new File(Environment.getExternalStorageDirectory()
//                +"/temp.jpg");
//
//        File picture = new File(Environment.getExternalStorageDirectory()
//                +"/temp.jpg");
//
//
//        new Crop(inputUri).output(outputUri).asSquare().start(mActivity);
    }



    public interface onGetPhotoCallback{

        public void onSuccess(Bitmap bitmap);

        public void onErro();


    }



}
