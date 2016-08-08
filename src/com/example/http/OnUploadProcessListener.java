package com.example.http;

public interface OnUploadProcessListener {
	/**
	 * 上传响应
	 * @param responseCode
	 * @param message
	 */
	public abstract void onUploadDone(int responseCode, String message);
	/**
	 * 上传中
	 * @param uploadSize
	 */
	public abstract void onUploadProcess(int uploadSize);
	/**
	 * 准备上传
	 * @param fileSize
	 */
	public abstract void initUpload(int fileSize);
}