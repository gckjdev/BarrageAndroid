package com.orange.barrage.android.util.network;

import com.orange.protocol.message.MessageProtos;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by pipi on 15/1/5.
 */
public interface BarrageNetworkInterface {
    @POST("/api/?&m=req&format=pb")
    void dataRequest(@Body MessageProtos.PBDataRequest user, Callback<MessageProtos.PBDataResponse> cb);
}
