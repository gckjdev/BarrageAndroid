package com.orange.barrage.android.feed.mission;

import android.graphics.Bitmap;
import android.util.Log;

import com.orange.barrage.android.util.imagecdn.CreateImageInfoInterface;
import com.orange.barrage.android.util.imagecdn.JpegImageInfo;
import com.orange.barrage.android.util.imagecdn.QiuCdnManager;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by pipi on 15/1/6.
 */
public class FeedMission {

    private static FeedMission ourInstance = new FeedMission();

    public static FeedMission getInstance() {
        return ourInstance;
    }

    private FeedMission() {
    }

    CreateImageInfoInterface imageInfo = new JpegImageInfo();
    public static float UPLOAD_IMAGE_QUALITY = 1.0f;

    public void createFeed(final Bitmap image,
                           final String text,
                           final List<UserProtos.PBUser> toUsers,
                           final FeedMissionCallbackInterface callback){

        // refer to http://developer.qiniu.com/docs/v6/sdk/android-sdk.html
        // prepare data for upload
        String token = QiuCdnManager.getInstance().getToken();
        String key = imageInfo.createKey();
        byte[] data = imageInfo.getImageBytes(image, UPLOAD_IMAGE_QUALITY);
        if (data == null || data.length == 0){
            Log.e("FeedMission", "<createFeed> but image data null");
            return;
        }

        UploadManager uploadManager = new UploadManager();

        // init upload options
        UploadOptions options = new UploadOptions(
                null, imageInfo.getMimeType(), true, new UpProgressHandler() {

            @Override
            public void progress(String s, double v) {
                Log.d("QINIU", String.format("upload progress %s, %.2f", s, v));
            }
        },
                null
//                new UpCancellationSignal(){
//                    public boolean isCancelled(){
//                        return isCancelled;
//                    }
//                }
        );

        // upload image
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        Log.d("QINIU", "upload complete, info="+info.toString()+", response="+response.toString());

                        if (info.statusCode == 200){
                            try {
                                String cdnKey = response.getString("key");

                                submitFeedToServer(cdnKey, text, toUsers, callback);

                            } catch (JSONException e) {
                                Log.e("QINIU", "catch exception = "+e.toString(), e);
                            }
                        }

                        // TODO invoke callback
                    }
                }, options);

    }

    private void submitFeedToServer(String cdnKey, String text, List<UserProtos.PBUser> toUsers, FeedMissionCallbackInterface callback) {

    }

    public void replyFeed(BarrageProtos.PBFeedAction feedAction,
                          FeedMissionCallbackInterface callbackInterface){

    }

    public void getTimelineFeed(FeedMissionCallbackInterface callbackInterface){

    }

}
