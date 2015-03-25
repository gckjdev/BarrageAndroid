package com.orange.barrage.android.feed.mission;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Filter;
import android.widget.SimpleAdapter;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.orange.barrage.android.util.misc.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

import roboguice.util.Ln;

/**
 * Created by youjiannuo on 2015/3/7.
 */
public class PhotoAndCamera {


    private static final int PHOTOHRAPH = 1;// 拍照
    private static final int PHOTOZOOM = 2; // 缩放
    private static final int PHOTORESOULT = 3;// 结果
    private static final int NONE = 0;
    private static final String IMAGE_UNSPECIFIED = "image/*";

    private Activity mActivity;


    public PhotoAndCamera(Activity context) {
        this.mActivity = context;
    }

    private ImageChooserManager mImageChooserManager;

    public void takePicture(final onGetPhotoCallback callback) {
        mImageChooserManager = new ImageChooserManager(mActivity, ChooserType.REQUEST_CAPTURE_PICTURE);
        mImageChooserManager.setImageChooserListener(createImageChooserListener(callback));
        try {
            mImageChooserManager.choose();
        } catch (Exception e) {
            //FIXME: cannot take picture
            Ln.e(e, "error in take photo");
        }

//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
//                Environment.getExternalStorageDirectory(), "temp.jpg")));
//        mActivity.startActivityForResult(intent, PHOTOHRAPH);
    }

    public void choosePhoto(final onGetPhotoCallback callback) {
        mImageChooserManager = new ImageChooserManager(mActivity, ChooserType.REQUEST_PICK_PICTURE);
        mImageChooserManager.setImageChooserListener(createImageChooserListener(callback));
        try {
            mImageChooserManager.choose();
        } catch (Exception e) {
            //FIXME: cannot choose picture
            Ln.e(e, "error in choose picture");
        }
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                IMAGE_UNSPECIFIED);
//        mActivity.startActivityForResult(intent, PHOTOZOOM);

    }

    protected ImageChooserListener createImageChooserListener(final onGetPhotoCallback callback) {
        return new ImageChooserListener() {

                @Override
                public void onImageChosen(final ChosenImage chosenImage) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String filePath = chosenImage.getFilePathOriginal();
                            Bitmap image = FileUtil.getPhoto(filePath);
                            callback.onSuccess(image);
                        }
                    });
                }

                @Override
                public void onError(final String reason) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(reason);
                        }
                    });
                }
            };
    }

    /*
     * 将这些方法放在Activity下面的onActivityResult里
	 */
    public void getPicture(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                (requestCode == ChooserType.REQUEST_PICK_PICTURE ||
                        requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            mImageChooserManager.submit(requestCode, data);
        }

//        boolean isSuccess = false;
//        Bitmap photo = null;
//        // 拍照
//        if (requestCode == PHOTOHRAPH) {
//            // 设置文件保存路径这里放在跟目录下
//            File picture = new File(Environment.getExternalStorageDirectory()
//                    + "/temp.jpg");
//            startPhotoZoom(Uri.fromFile(picture));
//        }
//
//        // 读取相册缩放图片
//        if (data != null && requestCode == PHOTOZOOM) {
//            startPhotoZoom(data.getData());
//        }
//        // 处理结果
//        if (data != null && requestCode == PHOTORESOULT) {
//            Bundle extras = data.getExtras();
//            if (extras != null) {
//                photo = extras.getParcelable("data");
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
//                // 100)压缩文件
//                isSuccess = true;
//            }
//        }
//
//        if (l != null) {
//            if (isSuccess) l.onSuccess(photo);
//            else if(requestCode != PHOTOHRAPH || requestCode != PHOTORESOULT) l.onErro();
//        }
    }

    //截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
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


    public interface onGetPhotoCallback {

        public void onSuccess(Bitmap bitmap);

        public void onError(String reason);
    }
}
