package com.orange.barrage.android.feed.mission;

import android.graphics.Bitmap;
import android.util.Log;

import com.orange.barrage.android.feed.model.FeedManager;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.imagecdn.CreateImageInfoInterface;
import com.orange.barrage.android.util.imagecdn.JpegImageInfo;
import com.orange.barrage.android.util.imagecdn.QiNiuCdnManager;
import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.barrage.android.util.network.BarrageNetworkCallback;
import com.orange.barrage.android.util.network.BarrageNetworkClient;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.ConstantsProtos;
import com.orange.protocol.message.MessageProtos;
import com.orange.protocol.message.UserProtos;
import com.qiniu.android.http.ResponseInfo;
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
        String token = QiNiuCdnManager.getInstance().getToken();
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

    private void submitFeedToServer(String cdnKey, String text, List<UserProtos.PBUser> toUsers, final FeedMissionCallbackInterface callback) {



        UserProtos.PBUser user = UserManager.getInstance().getUser();
        String imageURL = QiNiuCdnManager.getInstance().getUrl(cdnKey);

        BarrageProtos.PBFeed.Builder feedBuilder = BarrageProtos.PBFeed.newBuilder();
        feedBuilder.setFeedId("");
        feedBuilder.setType(ConstantsProtos.PBFeedType.FEED_IMAGE_TEXT_VALUE);
        feedBuilder.setDate(DateUtil.getNowTime());
        feedBuilder.setText(text);
        feedBuilder.setCdnKey(cdnKey);
        feedBuilder.setImage(imageURL);
        feedBuilder.addAllToUsers(toUsers);
        feedBuilder.setCreateUser(user);

        // TODO add device info

        BarrageProtos.PBFeed feed = feedBuilder.build();

        MessageProtos.PBCreateFeedRequest.Builder feedReqBuilder = MessageProtos.PBCreateFeedRequest.newBuilder();
        feedReqBuilder.setFeed(feed);

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setCreateFeedRequest(feedReqBuilder.build());

        BarrageNetworkClient.getInstance().dataRequest(MessageProtos.PBMessageType.MESSAGE_CREATE_FEED_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {
                        String feedId = response.getCreateFeedResponse().getFeedId();
                        Log.d("FeedMission", "create feed successfully, feedId="+feedId);
                        callback.handleSuccess(feedId, null);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Log.d("FeedMission", "create feed failure, errorCode="+errorCode);
                        callback.handleFailure(errorCode);
                    }
                });

    }

    public void replyFeed(final BarrageProtos.PBFeedAction feedAction,
                          final FeedMissionCallbackInterface callback){

        MessageProtos.PBReplyFeedRequest.Builder replyBuilder = MessageProtos.PBReplyFeedRequest.newBuilder();
        replyBuilder.setAction(feedAction);

        MessageProtos.PBDataRequest.Builder reqBuilder = MessageProtos.PBDataRequest.newBuilder();
        reqBuilder.setReplyFeedRequest(replyBuilder.build());

        BarrageNetworkClient.getInstance().dataRequest(MessageProtos.PBMessageType.MESSAGE_REPLY_FEED_VALUE,
                reqBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {
                        String actionId = response.getReplyFeedResponse().getAction().getActionId();
                        Log.d("FeedMission", "reply feed successfully, actionId ="+actionId);
                        callback.handleSuccess(actionId, null);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Log.d("FeedMission", "reply feed failure, errorCode="+errorCode);
                        callback.handleFailure(errorCode);
                    }
                });

    }

    public void getTimelineFeed(final FeedMissionCallbackInterface callback){

        String offsetFeedId = "";
        int limit = 10;

        MessageProtos.PBGetUserTimelineFeedRequest.Builder timelineBuilder = MessageProtos.PBGetUserTimelineFeedRequest.newBuilder();
        timelineBuilder.setOffsetFeedId(offsetFeedId);
        timelineBuilder.setLimit(limit);

        MessageProtos.PBDataRequest.Builder reqBuilder = MessageProtos.PBDataRequest.newBuilder();
        reqBuilder.setGetUserTimelineFeedRequest(timelineBuilder.build());

        BarrageNetworkClient.getInstance().dataRequest(MessageProtos.PBMessageType.MESSAGE_GET_USER_TIMELINE_FEED_VALUE,
                reqBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {
                        List<BarrageProtos.PBFeed> list = response.getGetUserTimelineFeedResponse().getFeedsList();
                        Log.d("FeedMission", "get timeline feed successfully, count ="+list.size());
                        FeedManager.getInstance().storeUserTimeline(list);
                        callback.handleSuccess(null, list);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Log.d("FeedMission", "get feed timeline, errorCode="+errorCode);
                        callback.handleFailure(errorCode);
                    }
                });
    }

}
