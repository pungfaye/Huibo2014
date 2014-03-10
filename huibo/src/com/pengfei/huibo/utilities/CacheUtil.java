package com.pengfei.huibo.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;


/**
 * @author pengfei
 * 
 */
public class CacheUtil {

	public static final String TINYWEIBO_ROOT_PATH = android.os.Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator + "tinyweibo" + File.separator;

	public static final String PROFILE_CACHE_PATH = TINYWEIBO_ROOT_PATH + "profileimage" + File.separator;

	public static final String IMAGE_CACHE_PATH = TINYWEIBO_ROOT_PATH + "weiboimage" + File.separator;

	public static final String PICTURE_CACHE_PATH = TINYWEIBO_ROOT_PATH + "takenpicture" + File.separator;


	static {
		File file = new File(TINYWEIBO_ROOT_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(PROFILE_CACHE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(IMAGE_CACHE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(PICTURE_CACHE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	
	public static Bitmap restoreBitmap(String parentpath, String imageurl) {
		String path = parentpath + EncryptDecryptUtil.encrypt(imageurl);
		File file = new File(path);
		if (file.exists()) {
			try {
				return BitmapFactory.decodeStream(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				return null;
			}
		}
		return null;
	}

	public static void saveImageToPath(Bitmap bitmap, String imagepath) {
		File file = new File(imagepath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));// quality = 100
		} catch (Exception e) {
		}
	}

	public static void clearCache() {
		deleteDirectory(IMAGE_CACHE_PATH);
		deleteDirectory(PICTURE_CACHE_PATH);
		deleteDirectory(PROFILE_CACHE_PATH);
	}

	private static void deleteDirectory(String cachePath) {
		File file = new File(cachePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}

	public static double calculateSpaceUsed() {
		return getSize(new File(TINYWEIBO_ROOT_PATH));
	}

	public static double getSize(File file) {
		if (file.exists()) {
			if (!file.isFile()) {
				File[] fl = file.listFiles();
				double ss = 0;
				for (File f : fl) {
					ss += getSize(f);
				}
				return ss;
			} else {
				double ss = (double) file.length() / 1024 / 1024;
				return ss;
			}
		} else {
			return 0.0;
		}
	}

}
