package com.example.http;

public interface RequestResultCallback {
	/**
	 * �ɹ��ص�����
	 * @param paramObject ���صĽ��
	 * */
	public abstract void onSuccess(Object paramObject);
	/**
	 * ʧ�ܻص�����
	 * @param paramException �����쳣��Ϣ
	 * */
	public abstract void onFail(String paramException);
}
