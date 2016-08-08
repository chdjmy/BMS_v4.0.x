package com.example.http;

public interface OnUploadProcessListener {
	/**
	 * �ϴ���Ӧ
	 * @param responseCode
	 * @param message
	 */
	public abstract void onUploadDone(int responseCode, String message);
	/**
	 * �ϴ���
	 * @param uploadSize
	 */
	public abstract void onUploadProcess(int uploadSize);
	/**
	 * ׼���ϴ�
	 * @param fileSize
	 */
	public abstract void initUpload(int fileSize);
}