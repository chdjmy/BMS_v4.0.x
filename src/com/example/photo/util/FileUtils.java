package com.example.photo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {
	//SD卡中的存储路径，不会随程序一起背卸载
	//getExternelFilesDirs()是应用文件夹data/data/package，随应用一起卸载
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/Photo_BMS/";
	static{
		if (!isFileExist("")) {
			try {
				File tempf = createSDDir("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 把照片保存到SD卡，返回路径
	 * @param bm Bitmap对象
	 * @param picName 照片名称
	 * @return 照片保存后路径，其实是SDPATH+picName+.JPEG
	 */
	public static String saveBitmap(Bitmap bm,String picName) {
		String filePath = null;
		try {
			
			filePath = SDPATH+picName+ ".JPEG";
			
			File f = new File(SDPATH, picName + ".JPEG"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); 
			else if (file.isDirectory())
				deleteDir(); 
		}
		dir.delete();
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
