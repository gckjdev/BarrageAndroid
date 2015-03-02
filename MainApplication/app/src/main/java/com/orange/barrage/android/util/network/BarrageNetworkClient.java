package com.orange.barrage.android.util.network;

import android.util.Log;

import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.config.BarrageConfigManager;
import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.protocol.message.ErrorProtos;
import com.orange.protocol.message.MessageProtos;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.inject.ContextSingleton;
import roboguice.util.Ln;

/**
 * Created by pipi on 15/1/5.
 */
@Singleton
public class BarrageNetworkClient {

    @Inject
    UserManager mUserManager;

    @Inject
    BarrageConfigManager mConfigManager;

    public void dataRequest(int type,
                            MessageProtos.PBDataRequest.Builder requestBuilder,
                            boolean isPostError,
                            final BarrageNetworkCallback callback){

        BarrageNetworkInterface retrofitInterface = new RestAdapter.Builder()
                .setEndpoint(mConfigManager.TRAFFIC_SERVER_URL)
                .setConverter(new ProtoConverter())
                .build()
                .create(BarrageNetworkInterface.class);

        String userId = mUserManager.getUserId();
        if (userId != null) {
            requestBuilder.setUserId(userId);
        }

        requestBuilder.setType(type);
        requestBuilder.setRequestId(DateUtil.getNowTime());

        MessageProtos.PBDataRequest req = requestBuilder.build();
        retrofitInterface.dataRequest(req, new Callback<MessageProtos.PBDataResponse>() {

            @Override
            public void success(MessageProtos.PBDataResponse pbDataResponse, Response response) {
                if (pbDataResponse == null){
                    Ln.d("http success but data response null");
                    callback.handleFailure(null, ErrorProtos.PBError.ERROR_DATA_RESPONSE_NULL_VALUE);
                }
                else if (pbDataResponse.getResultCode() != 0){
                    Ln.d("http success, but data response fail = "+pbDataResponse.toString());
                    callback.handleFailure(pbDataResponse, pbDataResponse.getResultCode());
                }
                else {
                    Ln.d("http success " + pbDataResponse.toString());
                    callback.handleSuccess(pbDataResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Ln.e(retrofitError, "failure "+retrofitError.toString());
                int code = 0;
                if (retrofitError.getResponse() != null){
                    code = retrofitError.getResponse().getStatus();
                }

                callback.handleFailure(null, code);
            }
        });

    }

    public static void test(){

        BarrageNetworkInterface retrofitInterface = new RestAdapter.Builder()
                .setEndpoint("http://192.168.36.200:8100/")
                .setConverter(new ProtoConverter())
                .build().create(BarrageNetworkInterface.class);

        MessageProtos.PBDataRequest.Builder builder = MessageProtos.PBDataRequest.newBuilder();
        builder.setUserId("1234");
        builder.setType(1);
        builder.setRequestId(13333);

        MessageProtos.PBDataRequest req = builder.build();
        retrofitInterface.dataRequest(req, new Callback<MessageProtos.PBDataResponse>() {

            @Override
            public void success(MessageProtos.PBDataResponse pbDataResponse, Response response) {
                Ln.d("http success "+pbDataResponse.toString());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Ln.d("http failure "+retrofitError.toString());
            }
        });
    }

}
