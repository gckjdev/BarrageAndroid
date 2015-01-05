package com.orange.barrage.android.util.network;

import com.orange.protocol.message.MessageProtos;

import org.json.JSONObject;

/**
 * Created by pipi on 15/1/5.
 */
public interface BarrageNetworkCallback {
    void handleSuccess(MessageProtos.PBDataResponse response);
    void handleFailure(MessageProtos.PBDataResponse response, int errorCode);
}

