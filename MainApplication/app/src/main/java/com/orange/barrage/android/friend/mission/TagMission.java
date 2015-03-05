package com.orange.barrage.android.friend.mission;

import com.orange.barrage.android.friend.mission.callback.AddTagCallback;
import com.orange.barrage.android.friend.mission.callback.GetTagListCallback;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.util.model.CommonMission;
import com.orange.barrage.android.util.network.BarrageNetworkCallback;
import com.orange.protocol.message.ErrorProtos;
import com.orange.protocol.message.MessageProtos;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.inject.ContextSingleton;
import roboguice.util.Ln;

/**
 * Created by pipi on 15/3/3.
 */

@Singleton
public class TagMission extends CommonMission {

    @Inject
    TagManager mTagManager;

    public void addTag(final UserProtos.PBUserTag tag, final AddTagCallback callback){

        if (tag == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE, null);
            return;
        }

        if (mTagManager.isTagNameExist(tag.getName())){
            callback.handleMessage(ErrorProtos.PBError.ERROR_USER_TAG_NAME_DUPLICATE_VALUE, null);
            return;
        }

        addOrUpdateTag(tag, callback);

    }

    // 添加用户到某个tag
    public void addUsersToTag(final UserProtos.PBUserTag origTag, final List<String> addUserIds, final AddTagCallback callback){
        if (origTag == null || addUserIds == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE, null);
            return;
        }

        UserProtos.PBUserTag.Builder tagBuilder = UserProtos.PBUserTag.newBuilder(origTag);
        tagBuilder.addAllUserIds(addUserIds);

        UserProtos.PBUserTag tag = tagBuilder.build();
        addOrUpdateTag(tag, callback);
    }


    // 从标签删除某个用户
    public void removeUserFromTag(final UserProtos.PBUserTag origTag, final String removeUserId, final AddTagCallback callback){
        if (origTag == null || removeUserId == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE, null);
            return;
        }

        UserProtos.PBUserTag.Builder tagBuilder = UserProtos.PBUserTag.newBuilder(origTag);
        tagBuilder.getUserIdsList().remove(removeUserId);

        UserProtos.PBUserTag tag = tagBuilder.build();
        addOrUpdateTag(tag, callback);
    }

    // 在已有标签情况下，把新的userList更新到tag
    public void updateUserTag(final UserProtos.PBUserTag origTag, final List<String> currentUserIds, final AddTagCallback callback){
        if (origTag == null || currentUserIds == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE, null);
            return;
        }

        UserProtos.PBUserTag.Builder tagBuilder = UserProtos.PBUserTag.newBuilder(origTag);
        tagBuilder.clearUserIds();
        tagBuilder.addAllUserIds(currentUserIds);

        UserProtos.PBUserTag tag = tagBuilder.build();
        addOrUpdateTag(tag, callback);
    }

    private void addOrUpdateTag(final UserProtos.PBUserTag tag, final AddTagCallback callback) {

        MessageProtos.PBAddUserTagRequest.Builder addBuilder = MessageProtos.PBAddUserTagRequest.newBuilder();
        addBuilder.setTag(tag);

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setAddUserTagRequest(addBuilder);

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_ADD_USER_TAG_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        Ln.d("addTag success");

                        UserProtos.PBUserTagList tagList = response.getAddUserTagResponse().getTags();
                        if (tagList != null) {
                            // success, store locally
                            mTagManager.storeTags(tagList);
                            callback.handleMessage(0, tag);
                        } else {
                            callback.handleMessage(ErrorProtos.PBError.ERROR_USER_TAG_LIST_NULL_VALUE, tag);
                        }

                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("addTag failure");
                        callback.handleMessage(errorCode, null);
                    }
                });
    }


    // 删除标签
    public void deleteTag(final UserProtos.PBUserTag tag, final AddTagCallback callback){
        if (tag == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE, null);
            return;
        }

        MessageProtos.PBDeleteUserTagRequest.Builder deleteBuilder = MessageProtos.PBDeleteUserTagRequest.newBuilder();
        deleteBuilder.setTag(tag);

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setDeleteUserTagRequest(deleteBuilder);

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_DELETE_USER_TAG_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        Ln.d("deleteTag success");

                        UserProtos.PBUserTagList tagList = response.getDeleteUserTagResponse().getTags();
                        if (tagList != null) {
                            // success, store locally
                            mTagManager.storeTags(tagList);
                            callback.handleMessage(0, tag);
                        } else {
                            callback.handleMessage(ErrorProtos.PBError.ERROR_USER_TAG_LIST_NULL_VALUE, tag);
                        }

                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("deleteTag failure");
                        callback.handleMessage(errorCode, null);
                    }
                });
    }

    // 获取用户所有标签
    public void getAllTags(final GetTagListCallback callback){
        MessageProtos.PBGetUserTagListRequest.Builder builder = MessageProtos.PBGetUserTagListRequest.newBuilder();

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setGetUserTagListRequest(builder.build());

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_GET_USER_TAG_LIST_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        Ln.d("getAllTags success");

                        UserProtos.PBUserTagList tagList = response.getGetUserTagListResponse().getTags();
                        if (tagList != null) {
                            // success, store locally
                            mTagManager.storeTags(tagList);
                            callback.handleMessage(0, tagList);
                        } else {
                            callback.handleMessage(ErrorProtos.PBError.ERROR_USER_TAG_LIST_NULL_VALUE, tagList);
                        }

                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("getAllTags failure");
                        callback.handleMessage(errorCode, null);
                    }
                });
    }





}
