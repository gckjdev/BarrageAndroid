package com.orange.barrage.android.user.mission;

import android.util.Log;

import com.orange.barrage.android.util.network.BarrageNetworkCallback;
import com.orange.barrage.android.util.network.BarrageNetworkClient;
import com.orange.protocol.message.MessageProtos;
import com.orange.protocol.message.UserProtos;

/**
 * Created by pipi on 15/1/5.
 */
public class UserMission {

    private static UserMission ourInstance = new UserMission();

    public static UserMission getInstance() {
        return ourInstance;
    }

    private UserMission() {
    }

    private void regiseterUser(int type,
                              UserProtos.PBUser.Builder userBuilder,
                              String inviteCode,
                              UserMissionCallback callback) {


        userBuilder.setRegDate((int)(System.currentTimeMillis()/1000));
        userBuilder.setUserId("");

        MessageProtos.PBRegisterUserRequest.Builder regBuilder = MessageProtos.PBRegisterUserRequest.newBuilder();
        regBuilder.setType(type);
        regBuilder.setUser(userBuilder.build());
        if (inviteCode != null && !inviteCode.isEmpty()){
            regBuilder.setInviteCode(inviteCode);
        }

        MessageProtos.PBDataRequest.Builder dataRequestBuilder = MessageProtos.PBDataRequest.newBuilder();
        dataRequestBuilder.setRegisterUserRequest(regBuilder.build());

        BarrageNetworkClient.getInstance().dataRequest(MessageProtos.PBMessageType.MESSAGE_REGISTER_USER_VALUE,
                dataRequestBuilder,
                true,
                new BarrageNetworkCallback() {
                    @Override
                    public void handleSuccess(MessageProtos.PBDataResponse response) {

                        Log.d(UserMission.class.getName(), "regiseterUser success");

                        UserProtos.PBUser user = response.getRegisterUserResponse().getUser();
                        if (user != null){
                            // TODO success, store locally
                        }
                        else{
                            // failure
                        }
                    }

                    @Override
                    public void handleFailure(MessageProtos.PBDataResponse response, int errorCode) {
                        // TODO failure handling
                        Log.d(UserMission.class.getName(), "regiseterUser failure");
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
