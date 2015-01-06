package com.orange.barrage.android.util.imagecdn;

import android.graphics.Bitmap;

import com.orange.barrage.android.util.misc.DateUtil;
import com.orange.barrage.android.util.misc.ImageUtil;
import com.orange.barrage.android.util.misc.StringUtil;

import java.util.Date;

/**
 * Created by pipi on 15/1/6.
 */
public class JpegImageInfo implements CreateImageInfoInterface {

    @Override
    public byte[] getImageBytes(Bitmap bitmap, float quality) {
        return ImageUtil.imageToJpeg(bitmap, quality);
    }

    @Override
    public String getMimeType() {
        return "image/jpeg";
    }

    @Override
    public String getPathExtension() {
        return "jpeg";
    }

    @Override
    public String createKey(){
        String pathExt = getPathExtension();
        String uuid = StringUtil.createUUID();
        String date = DateUtil.dateToStringByFormat(new Date(), "yyyyMMdd", true);
        return String.format("data/img/%s/%s.%s", date, uuid, pathExt);
    }
}
