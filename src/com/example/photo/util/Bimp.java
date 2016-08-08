package com.example.photo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bimp {
	public static int max = 0;//static变量必须初始化
	//选择的图片的临时列表
	public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   

	/**
	 * revision 修正图片大小，把图片压缩到1000 像素以下
	 * @param path 照片途径
	 * @return 返回Bitmap对象
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path) throws IOException {
		//FileUtils里Bitmap被compress到FileOutputStream里后被写到File
		//这里是相反操作，把File转到FileInputStream，再到buffer里
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		//设为true时只获得图片边界信息，不解码图片
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;//获得图片
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	//修改时显示照片
	public static void displayPhoto(String picAddr){
		if(!StringUtils.isEmpty(picAddr)){
			String[] photos = picAddr.split(";");
			for(int i=0;i<photos.length;i++){
				ImageItem takePhoto = new ImageItem();
				takePhoto.setImagePath(photos[i]);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
		}
	}
	//生成照片路径
	public static String savePhotoAddr(){
		StringBuffer photoAddr = new StringBuffer();
		for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
			photoAddr.append(
					Bimp.tempSelectBitmap.get(i).getImagePath())
					.append(";");
		}
		return photoAddr.toString();
	}
	//清空图片列表
	public static void clearPhoto(){
		for (int i = 0; i < PublicWay.activityList.size(); i++) {
			if (null != PublicWay.activityList.get(i)) {
				PublicWay.activityList.get(i).finish();
			}
		}
		Bimp.tempSelectBitmap.clear();
		Bimp.max = 0;
	}
	
}
