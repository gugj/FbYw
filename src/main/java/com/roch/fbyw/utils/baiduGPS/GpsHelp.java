package com.roch.fbyw.utils.baiduGPS;

import android.content.Context;
import android.webkit.WebView;

import com.google.gson.Gson;

public class GpsHelp {

	public Context context;
	public Locat locat= null;
	public Gson gson=new Gson();

	public GpsHelp(){}

	public GpsHelp(Context c,WebView web){
		this.context=c;
		locat=new Locat(context,web);
		locat.start();
	}

	//停止 定位
	public void stop(){
		locat.stop();
	}

	public void start(){
		locat.start();
	}

	//返回所以信息
	public String getInfo(){
		//locat.start();
		if(locat.locations.getAddr()!="" && locat.locations.getAddr()!=null){
			//locat.stop();
		}
		return gson.toJson(locat.locations);
	}

	//返回地址
	public String getAdds(){
		//locat.start();
		if(locat.locations.getAddr()!="" && locat.locations.getAddr()!=null){
			//locat.stop();
			return locat.locations.getAddr();
		}else{
			return "false";
		}
	}

	//返回坐标
	public String getCoordinate(){
		//locat.start();
		if(locat.locations.getAddr()!="" && locat.locations.getAddr()!=null){
			//locat.stop();
			return locat.locations.getLontitude()+","+locat.locations.getLatitude();
		}else{
			return "false";
		}
	}

	public void Toast(String txt){
		locat.Toast(txt);
	}

}
