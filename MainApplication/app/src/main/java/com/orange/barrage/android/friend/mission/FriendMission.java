package com.orange.barrage.android.friend.mission;

import com.orange.barrage.android.friend.mission.callback.FriendMissionDefaultCallback;
import com.orange.barrage.android.friend.mission.callback.GetFriendListCallback;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.util.model.CommonMission;
import com.orange.barrage.android.util.network.BarrageNetworkCallback;
import com.orange.protocol.message.ErrorProtos;
import com.orange.protocol.message.MessageProtos;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;

import roboguice.util.Ln;

/**
 * Created by pipi on 15/3/5.
 */
public class FriendMission extends CommonMission {

    @Inject
    FriendManager mFriendManager;

    // 获取好友列表
    public void getFriendList(final int type, final GetFriendListCallback callback){

        MessageProtos.PBGetUserFriendListRequest.Builder builder = MessageProtos.PBGetUserFriendListRequest.newBuilder();
        builder.setType(type);

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setGetUserFriendListRequest(builder.build());

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_GET_USER_FRIEND_LIST_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {


                        UserProtos.PBUserFriendList friendList = response.getGetUserFriendListResponse().getFriends();
                        if (friendList != null) {
                            Ln.d("getFriendList success, %d friends, %d request, %d request count",
                                    friendList.getFriendsCount(), friendList.getRequestFriendsCount(), friendList.getRequestNewCount());

                            // success, store locally
                            switch (type){
                                case MessageProtos.PBGetUserFriendListType.TYPE_ALL_VALUE:
                                    mFriendManager.storeFriendList(friendList);
                                    break;
                                case MessageProtos.PBGetUserFriendListType.TYPE_FRIEND_LIST_VALUE:
                                    mFriendManager.storeOnlyFriendList(friendList.getFriendsList());
                                    break;
                                case MessageProtos.PBGetUserFriendListType.TYPE_REQUEST_FRIEND_LIST_VALUE:
                                    mFriendManager.storeRequestFriendList(friendList.getRequestFriendsList(), friendList.getRequestFriendsCount());
                                    break;
                                default:
                                    Ln.w("getFriendList, invalid type %d", type);
                                    break;
                            }

                            callback.handleMessage(0, friendList);
                        } else {
                            callback.handleMessage(ErrorProtos.PBError.ERROR_RESPONSE_SERVICE_DATA_NULL_VALUE, null);
                        }

                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("getFriendList failure");
                        callback.handleMessage(errorCode, null);
                    }
                });

    }

    // 同意好友
    public void addFriend(final MessageProtos.PBAddUserFriendRequest addRequest, final FriendMissionDefaultCallback callback){

        if (addRequest == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE);
            return;
        }

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setAddUserFriendRequest(addRequest);

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_ADD_USER_FRIEND_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        Ln.d("addFriend success");

                        // TODO success, store locally
                        callback.handleMessage(0);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("addFriend failure");
                        callback.handleMessage(errorCode);
                    }
                });
    }

    // 删除好友
    public void deleteFriend(final UserProtos.PBUser pbFriend, final FriendMissionDefaultCallback callback){
        // TODO
    }

    // 处理好友请求（同意、拒绝等）
    public void processUserFriendRequest(final MessageProtos.PBProcessUserFriendRequest request, final FriendMissionDefaultCallback callback){

        if (request == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE);
            return;
        }

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setProcessUserFriendRequest(request);

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_PROCESS_USER_FRIEND_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        Ln.d("processUserFriendRequest success");

                        // TODO success, store locally
                        callback.handleMessage(0);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("processUserFriendRequest failure");
                        callback.handleMessage(errorCode);
                    }
                });
    }

    // 同步用户好友列表
    public void syncFriend(final GetFriendListCallback callback){

        boolean needSync = false;

        boolean IS_WIFI = true;     // TODO need a network util
        if (IS_WIFI){
            needSync = true;
        }
        else{
            List<UserProtos.PBUser> friendList = mFriendManager.getFriendList();
            if (friendList == null || friendList.size() == 0){
                Ln.d("no friend list yet, need sync");
                needSync = true;
            }
        }

        if (needSync){
            getFriendList(MessageProtos.PBGetUserFriendListType.TYPE_ALL_VALUE, callback);
        }
        else{
            UserProtos.PBUserFriendList allList = mFriendManager.getAllList();
            callback.handleMessage(0, allList);
        }
    }

    // 获得用户的好友请求列表
    public void getFriendRequestList(final GetFriendListCallback callback){
        getFriendList(MessageProtos.PBGetUserFriendListType.TYPE_REQUEST_FRIEND_LIST_VALUE, callback);
    }

}
