package com.orange.barrage.android.user.mission;

import android.util.Log;

import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.barrage.android.util.network.BarrageNetworkCallback;
import com.orange.barrage.android.util.network.BarrageNetworkClient;
import com.orange.protocol.message.ErrorProtos;
import com.orange.protocol.message.MessageProtos;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.inject.ContextSingleton;
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

                        Ln.d(UserMission.class.getName(), "regiseterUser success");

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

                        Ln.d(UserMission.class.getName(), "loginUser success");

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
                        Ln.d(UserMission.class.getName(), "verifyInviteCode success");
                        callback.handleMessage(0, null);
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        Ln.d("verifyInviteCode failure");
                        callback.handleMessage(errorCode, null);
                    }
                });
    }

}
