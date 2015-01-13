package com.orange.barrage.android.util.misc;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

import roboguice.util.Ln;

public class HttpUtils {

	public static JSONObject httpGet(Uri uri) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri.toString());
		ResponseHandler<String> strRespHandler = new BasicResponseHandler();

		String response = null;
		JSONObject jsonResponse = null;

		try {
			response = httpclient.execute(httpGet, strRespHandler);
			Ln.d("Get http response: " + response);
			
			jsonResponse = new JSONObject(response);
			Ln.d("Get Json response: " + jsonResponse);
		} catch (Exception e) {
			logException(e);
		}

		return jsonResponse;
	}

	private static void logException(Exception e) {
		Ln.e(UtilConstants.LOG_TAG, "Get exception when handling http request/response!", e);
	}
}
