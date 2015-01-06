package com.orange.barrage.android.util.imagecdn;

import android.graphics.Bitmap;

/**
 * Created by pipi on 15/1/6.
 */
public interface CreateImageInfoInterface {

    public byte[] getImageBytes(Bitmap bitmap, float quality);
    public String getMimeType();
    public String getPathExtension();
    public String createKey();

}
