package com.orange.barrage.android.util.network;

import android.util.Log;

import com.orange.protocol.message.ErrorProtos;
import com.orange.protocol.message.MessageProtos;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by pipi on 15/1/5.
 */
public class BarrageNetworkClient {

    private static BarrageNetworkClient ourInstance = new BarrageNetworkClient();

    public static BarrageNetworkClient getInstance() {
        return ourInstance;
    }

    private BarrageNetworkClient() {
    }


    public void dataRequest(int type,
                            MessageProtos.PBDataRequest.Builder requestBuilder,
                            boolean isPostError,
                            final BarrageNetworkCallback callback){

        BarrageNetworkInterface retrofitInterface = new RestAdapter.Builder()
                .setEndpoint("http://192.168.36.200:8100/") // TODO move server URL to UMENG online config
                .setConverter(new ProtoConverter())
                .build()
                .create(BarrageNetworkInterface.class);

        // TODO get and set userId in request builder
        requestBuilder.setUserId("this is dummy UserID in data request. hahaha, you didn't change me YET");

        requestBuilder.setType(type);
        requestBuilder.setRequestId((int)(System.currentTimeMillis()/1000));

        MessageProtos.PBDataRequest req = requestBuilder.build();
        retrofitInterface.dataRequest(req, new Callback<MessageProtos.PBDataResponse>() {

            @Override
            public void success(MessageProtos.PBDataResponse pbDataResponse, Response response) {
                if (pbDataResponse == null){
                    Log.d("[HTTP]", "success but data response null");
                    callback.handleFailure(null, ErrorProtos.PBError.ERROR_DATA_RESPONSE_NULL_VALUE);
                }
                else if (pbDataResponse.getResultCode() != 0){
                    Log.d("[HTTP]", "success, but data response fail = "+pbDataResponse.toString());
                    callback.handleFailure(pbDataResponse, pbDataResponse.getResultCode());
                }
                else {
                    Log.d("[HTTP]", "success "+pbDataResponse.toString());
                    callback.handleSuccess(pbDataResponse);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("[HTTP]", "failure "+retrofitError.toString());
                callback.handleFailure(null, retrofitError.getResponse().getStatus());
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
                Log.d("Retrofit", "http success "+pbDataResponse.toString());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("Retrofit", "http failure "+retrofitError.toString());
            }
        });
    }

}
