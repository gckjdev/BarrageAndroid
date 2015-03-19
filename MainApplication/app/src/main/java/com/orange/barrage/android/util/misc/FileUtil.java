package com.orange.barrage.android.util.misc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import roboguice.util.Ln;

public class FileUtil {
	private static final String TAG = null;
	private static int len = 0;
	private static byte[] buffer = new byte[1024];

	
	public static String getSDCardCachePath(Context context,String appName){
		return FileUtil.getSDPath()+"/"+appName+"/cache/";
	}


	public static void saveFileAsString(Context context, String filename, String content) {
		saveFileAsByte(context, filename, content.getBytes());
	}
	
	public static void saveFileAsByte(Context context, String filename, byte[] content) {
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(content);
		} catch (Exception e) {
			logException(filename, e);
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				logException(filename, e);
			}
		}
	}

	public static String readFileAsString(Context context, String filename) {
		byte[] data = readFileAsByte(context, filename);
		return new String(data);
	}

	public static byte[] readFileAsByte(Context context, String filename) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = null;
		try {
			fis = context.openFileInput(filename);
			while ((len = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			logException(filename, e);
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				logException(filename, e);
			}
		}

		return baos.toByteArray();
	}

	public static void logException(String filename, Exception e) {
		Ln.e(e, "File operation failed, file name: " + filename);
	}
	
	
	public static long getFileSize(String path)
	{
		// TODO Auto-generated method stub
		File file = new File(path);
		if (file.exists())
		{
			return file.length();
		} else
		{
			return 0;
		}
	}
	
	 public static boolean moveFile(String srcFile, String destPath) {
	        // File (or directory) to be moved
	        File file = new File(srcFile);
	        // Destination directory
	        File dir = new File(destPath);
	        // Move file to new directory
	       boolean moveFlag = file.renameTo(new File(dir, file.getName()));
	        //boolean moveFlag = file.renameTo(new File(destPath));
	        return moveFlag;
	}
	 
	
	public static void saveBitmapInFile(String filePath,String fileName,Bitmap bitmap)
	{
		File cacheFolder = new File(filePath);
		if (!cacheFolder.exists())
		{
			cacheFolder.mkdirs();
			creatNoMediaFile(filePath);
		}
		FileOutputStream fouts = null;
		try
		{
			File cacheFile = new File(filePath, fileName);
			cacheFile.createNewFile();
			fouts = new FileOutputStream(cacheFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fouts);
			fouts.flush();
			fouts.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	} 
	
	
	public static boolean saveFileInSDCard(String fileName,String filePath,InputStream inputStream){
		Ln.d("<saveFileInSDCard> and filepath = " + filePath);
		if (!checkFileIsExits(filePath))
		{
			creatDir(filePath);
		}
		String tragetFilePath = filePath+fileName;
		return writeFile(tragetFilePath, inputStream);
	}
	
	
	
	public static boolean writeFile(String targetFile, InputStream inputStream)
	{
		boolean flag = true;

		try
		{
			File file = new File(targetFile);
			OutputStream myOutput = new FileOutputStream(file);
			BufferedInputStream myInput = new BufferedInputStream(inputStream);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1)
			{
				myOutput.write(buffer, 0, length);
				Ln.d("file length = " + length);
			}
			myOutput.flush();
			myInput.close();
			myOutput.close();
		} catch (Exception e)
		{
			Ln.e(e, "<writeFile> but catch exception :" + e.toString());
		}

		return flag;
	}
	
	
	
	public static FileInputStream readFile(String filePath){
			
		if (!checkFileIsExits(filePath))
		{
			Ln.e("<readFile> but file not found filePath = "+filePath);
			return null;
		}
		File file = new File(filePath);	
		FileInputStream fileInputStream = null;
		try
		{
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			Ln.e(e, "<readFile> but file not found filePath = "+filePath);
		}
		return fileInputStream;
	}
	
	
	public static boolean deleteFile(String filePath)
	{
		boolean flag = false;
		File file = new File(filePath);
		if (file.isFile() && file.exists())
		{
			file.delete();
			flag = true;
		}
		return flag;
	}
	
	
	public static String getSDPath(){
        boolean sdCardExist = MemoryUtil.externalMemoryAvailable();
        if   (sdCardExist)                              
        return	android.os.Environment.getExternalStorageDirectory().getAbsolutePath(); 
        return null;
    } 
	
	
	public static void creatNoMediaFile(String path){
		String fileName = path+".nomedia";
		File file = new File(fileName);
		try
		{
			file.createNewFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	

 
public static boolean checkFileIsExits(String filePath)
{
	File file = new File(filePath);
	if (file.exists())
	{
		return true;
	}
	return false;
}

    
   /* public static String getSDPath(){
        boolean sdCardExist = MemoryUtil.externalMemoryAvailable();
        if   (sdCardExist)                              
        return	android.os.Environment.getExternalStorageDirectory().getAbsolutePath(); 
        return null;
    } */
    
    
    public static void creatDir(String filePath){
    	File fileSaveDir = new File(filePath);
		if (!fileSaveDir.exists())
			fileSaveDir.mkdirs();
    }

	
	public static boolean copyAssetsFile(Context context, String fileName,
			String dataPath)
	{

		InputStream inputStream = null;
		OutputStream myOutput = null;
		BufferedInputStream myInput = null;
		boolean result = false;
		String targetFile = dataPath+fileName;
		try
		{
			inputStream = context.getAssets().open(fileName);
			myOutput = new FileOutputStream(targetFile);
			myInput = new BufferedInputStream(inputStream);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) != -1)
			{
				myOutput.write(buffer, 0, length);
			}
			myOutput.flush();
			result = true;	
		} catch (Exception e)
		{
			Ln.e(e, "<copyAssetsFile> assets file = " +fileName + ", to dest file "
					+ targetFile + ", but catch exception " + e.toString());
			result = false;
	} finally
	{
		try
		{
			if (myOutput != null)
			{
				myOutput.close();
			}
			if (myInput != null)
			{
				myInput.close();
			}
			if (inputStream != null)
			{
				inputStream.close();
			}
		} catch (IOException e)
		{
		}
	}
		return result;
	}



    /**
     * Save image to the SD card
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap,String path,String photoName){
        if (SystemUtil.checkSDCardAvailable()) {
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
