package com.example.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Net {
	private Net(Context context){
		this.context = context;
	}
	
	public boolean netState(){
		
		boolean netState = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null) {
			netState = cm.getActiveNetworkInfo().isAvailable(); 
			   NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			  // Log.i(StringResource.ACCOUNT, "networkInfo.getTypeName()-------->"+ networkInfo.getTypeName());
			   System.out.println("networkInfo:"+networkInfo.getTypeName());
			   if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
				   if(networkInfo.isConnected()){
					   netState = true;
				   }else{
					   netState = false;
				   }
			   }
//			   if(networkInfo.getTypeName().toLowerCase().equals("pppoe")){
//				   if(networkInfo.isConnected()){
//					   netState = true;   
//				   }else{
//					   netState = false;
//				   }
//			   }
			}
		return netState;
	}
	
	
	public static Net getSingle(Context context){
		if(null == net){
			net = new Net(context);
		}
		return net;
	}
	private Context context;
	private static Net net;
}
