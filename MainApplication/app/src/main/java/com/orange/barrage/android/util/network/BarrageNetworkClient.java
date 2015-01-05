package com.orange.barrage.android.util.network;

import android.util.Log;

import com.orange.protocol.message.MessageProtos;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by pipi on 15/1/5.
 */
public class BarrageNetworkClient {

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
