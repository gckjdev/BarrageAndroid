package com.orange.barrage.android.util.misc;

import android.net.Uri.Builder;
import android.util.Log;

import roboguice.util.Ln;

public class UriUtil {
	public static Builder appendQueryParameter(Builder builder, String key, String value) {
		if (key == null) {
			Ln.e("Can not add query parameter with null key!");
		}
		if (value == null) {
			return builder.appendQueryParameter(key, "");
		}
		return builder.appendQueryParameter(key, value);
	}
}
