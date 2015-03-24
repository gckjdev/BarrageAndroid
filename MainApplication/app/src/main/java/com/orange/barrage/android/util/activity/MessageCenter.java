package com.orange.barrage.android.util.activity;

import android.widget.Toast;

import com.orange.barrage.android.util.ContextManager;

import javax.inject.Inject;

import roboguice.inject.ContextSingleton;

/**
 * Created by pipi on 15/3/6.
 */

public class MessageCenter {

    public static void postInfoMessage(String messageText){
        Toast.makeText(ContextManager.getContext(), messageText, Toast.LENGTH_SHORT).show();
    }

    public static void postErrorMessage(String messageText){
        Toast.makeText(ContextManager.getContext(), messageText, Toast.LENGTH_SHORT).show();
    }

    public static void postSuccessMessage(String messageText){
        Toast.makeText(ContextManager.getContext(), messageText, Toast.LENGTH_SHORT).show();
    }

    public static void postTestMessage(String messageText){
        Toast.makeText(ContextManager.getContext(), messageText, Toast.LENGTH_SHORT).show();
    }

}
