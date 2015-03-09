package com.orange.barrage.android.util.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.orange.barrage.android.util.System.SystemTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by youjiannuo on 2015/3/7.
 */
public class FileTools {


    /**
     * Save image to the SD card
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap,String path,String photoName){
        if (SystemTools.checkSDCardAvailable()) {
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdirs();
            }

            File photoFile = new File(path , photoName + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
//						fileOutputStream.close();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally{
                try {
                    if(fileOutputStream != null)
                        fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * delete file
     * @param path
     */
    public static void deleteFile(String path){

        if(SystemTools.checkSDCardAvailable()){

            File file = new File(path);

            if(file.exists()){

                file.delete();

            }

        }

    }

    /**
     * Get images from SD card by path and the name of image
     * @param photoName
     * @return
     */
    public static Bitmap getPhotoFromSDCard(String path,String photoName){
        Bitmap photoBitmap = BitmapFactory.decodeFile(path + "/" + photoName + ".png");
        if (photoBitmap == null) {
            return null;
        }else {
            return photoBitmap;
        }
    }



}
