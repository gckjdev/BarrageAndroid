package com.orange.barrage.android.user.mission;

import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.barrage.android.util.network.BarrageNetworkCallback;
import com.orange.barrage.android.util.network.BarrageNetworkClient;
import com.orange.protocol.message.ErrorProtos;
import com.orange.protocol.message.MessageProtos;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.util.Ln;

/**
 * Created by pipi on 15/1/5.
 */
@Singleton
public class UserMission {

    @Inject
    BarrageNetworkClient mBarrageNetworkClient;

    @Inject
    UserManager mUserManager;

    private void regiseterUser(int type,
                              UserProtos.PBUser.Builder userBuilder,
                              String inviteCode,
                              final UserMissionCallback callback) {


        userBuilder.setRegDate(DateUtil.getNowTime());

        // set empty user ID for registration
        userBuilder.setUserId("");

        MessageProtos.PBRegisterUserRequest.Builder regBuilder = MessageProtos.PBRegisterUserRequest.newBuilder();
        regBuilder.setType(type);
        regBuilder.setUser(userBuilder.build());
        if (inviteCode != null && !inviteCode.isEmpty()){
            regBuilder.setInviteCode(inviteCode);
        }

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setRegisterUserRequest(regBuilder.build());

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_REGISTER_USER_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        Ln.d("regiseterUser success");

                        UserProtos.PBUser user = response.getRegisterUserResponse().getUser();
                        if (user != null) {
                            // success, store locally
                            mUserManager.storeUser(user);
                            callback.handleMessage(0, user);
                        } else {
                            // TODO no data???
                        }

                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("regiseterUser failure");
                        callback.handleMessage(errorCode, null);
                    }
                });
    }


    public void regiseterUserByEmail(String email,
                                     String password,
                                     String inviteCode,
                                     UserMissionCallback callback){

        UserProtos.PBUser.Builder builder = UserProtos.PBUser.newBuilder();
        builder.setEmail(email);
        builder.setPassword(password);

        regiseterUser(UserProtos.PBRegisterType.REG_EMAIL_VALUE,
                builder,
                inviteCode,
                callback);
    }

    public void regiseterUserByMobile(String mobile,
                                      String password,
                                      String inviteCode,
                                      UserMissionCallback callback){

        UserProtos.PBUser.Builder builder = UserProtos.PBUser.newBuilder();
        builder.setMobile(mobile);
        builder.setPassword(password);

        regiseterUser(UserProtos.PBRegisterType.REG_MOBILE_VALUE,
                builder,
                inviteCode,
                callback);
    }

    private void loginUser(final MessageProtos.PBLoginUserRequest.Builder loginUserBuilder,
                           final UserMissionCallback callback) {

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setLoginUserRequest(loginUserBuilder);

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_LOGIN_USER_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        Ln.d("loginUser success");

                        UserProtos.PBUser user = response.getLoginUserResponse().getUser();
                        if (user != null) {
                            // success, store locally
                            mUserManager.storeUser(user);
                            callback.handleMessage(0, user);
                        } else {
                            // TODO no data???
                        }

                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("loginUser failure");
                        callback.handleMessage(errorCode, null);
                    }
                });
    }

    public void loginUserByEmail(String email,
                                 String password,
                                 UserMissionCallback callback){

        MessageProtos.PBLoginUserRequest.Builder builder = MessageProtos.PBLoginUserRequest.newBuilder();
        builder.setType(UserProtos.PBLoginType.LOGIN_EMAIL_VALUE);
        builder.setEmail(email);
        builder.setPassword(password);

        loginUser(builder, callback);
    }

    public void loginUserByMobile(String mobile,
                                 String password,
                                 UserMissionCallback callback){

        MessageProtos.PBLoginUserRequest.Builder builder = MessageProtos.PBLoginUserRequest.newBuilder();
        builder.setType(UserProtos.PBLoginType.LOGIN_MOBILE_VALUE);
        builder.setMobile(mobile);
        builder.setPassword(password);

        loginUser(builder, callback);
    }

    public void verifyInviteCode(final String code, final UserMissionCallback callback){

        MessageProtos.PBVerifyInviteCodeRequest.Builder builder = MessageProtos.PBVerifyInviteCodeRequest.newBuilder();
        builder.setInviteCode(code);

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setVerifyInviteCodeRequest(builder);

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_VERIFY_INVITE_CODE_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {
                        Ln.d("verifyInviteCode success");
                        callback.handleMessage(0, null);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("verifyInviteCode failure");
                        callback.handleMessage(errorCode, null);
                    }
                });
    }

    public void searchUser(final String keyword, final int offset, final int limit, final SearchUserCallback callback){

        MessageProtos.PBSearchUserRequest.Builder builder = MessageProtos.PBSearchUserRequest.newBuilder();
        builder.setKeyword(keyword);
        builder.setLimit(limit);
        builder.setOffset(offset);

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setSearchUserRequest(builder);

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_SEARCH_USER_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        List<UserProtos.PBUser> userList = response.getSearchUserResponse().getUsersList();
                        Ln.d("searchUser success, keyword="+keyword+", offset="+offset+", limit="+limit);
                        callback.handleMessage(0, userList);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.w("searchUser failure, keyword="+keyword+", offset="+offset+", limit="+limit);
                        callback.handleMessage(errorCode, null);
                    }
                });

    }

    public void updateUser(final UserProtos.PBUser user, final UserMissionCallback callback){

        if (user == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE, null);
            return;
        }

        MessageProtos.PBUpdateUserInfoRequest.Builder builder = MessageProtos.PBUpdateUserInfoRequest.newBuilder();
        builder.setUser(user);

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setUpdateUserInfoRequest(builder);

        mBarrageNetworkClient.dataRequest(MessageProtos.PBMessageType.MESSAGE_UPDATE_USER_INFO_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        UserProtos.PBUser retUser = response.getUpdateUserInfoResponse().getUser();
                        if (retUser != null){
                            mUserManager.storeUser(retUser);
                        }
                        Ln.d("updateUser success, return user="+retUser.toString());
                        callback.handleMessage(0, retUser);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.w("updateUser failure");
                        callback.handleMessage(errorCode, null);
                    }
                });

    }

    public void updateUserNick(final String nick, final UserMissionCallback callback){

        if (nick == null){
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE, null);
            return;
        }

        UserProtos.PBUser.Builder userBuilder = UserProtos.PBUser.newBuilder();
        userBuilder.setUserId(mUserManager.getUserId());
        userBuilder.setNick(nick);
        updateUser(userBuilder.build(), callback);
    }

    public void updateUserSignature(final String signature,final UserMissionCallback callback)
    {
        if (signature==null)
        {
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE,null);
            return ;
        }
        UserProtos.PBUser.Builder userBuilder=UserProtos.PBUser.newBuilder();
        userBuilder.setUserId(mUserManager.getUserId());
        userBuilder.setSignature(signature);
        updateUser(userBuilder.build(),callback);
    }

    //update the email
    public void updateUserEmail(final String email,final UserMissionCallback callback)
    {
        if (email==null)
        {
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE,null);
            return ;
        }
        UserProtos.PBUser.Builder userBuilder=UserProtos.PBUser.newBuilder();
        userBuilder.setUserId(mUserManager.getUserId());
        userBuilder.setEmail(email);
        updateUser(userBuilder.build(),callback);
    }

    //update the user password
    public void updateUserPassword(final String password,final UserMissionCallback callback)
    {
        if (password==null)
        {
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE,null);
            return ;
        }
        UserProtos.PBUser.Builder userBuilder=UserProtos.PBUser.newBuilder();
        userBuilder.setUserId(mUserManager.getUserId());
        userBuilder.setPassword(password);
        updateUser(userBuilder.build(),callback);
    }

    //update the Gender
    public void updateUserGender(final Boolean gender,final UserMissionCallback callback)
    {
        if (gender==null)
        {
            callback.handleMessage(ErrorProtos.PBError.ERROR_INCORRECT_INPUT_DATA_VALUE,null);
            return ;
        }
        UserProtos.PBUser.Builder userBuilder=UserProtos.PBUser.newBuilder();
        userBuilder.setUserId(mUserManager.getUserId());
        userBuilder.setGender(gender);
        updateUser(userBuilder.build(),callback);
    }
}
