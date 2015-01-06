package com.orange.barrage.android.util.misc;

import java.util.Locale;

public class LocaleUtil {
	
	public static String getCurrentLanguage() {
		return Locale.getDefault().getDisplayLanguage();
	}
	
	public static String getCurrentCountry() {
		return Locale.getDefault().getDisplayCountry();
	}
}
