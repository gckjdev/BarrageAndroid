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
        //FIXME: should generate userId
        //userBuilder.getEmail()
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
}
