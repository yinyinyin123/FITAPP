package com.example.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.project.match.thread;
import com.example.project.myhomepage.MyDialog;
import com.example.project.myhomepage.deletethread;
import com.example.project.myhomepage.insertthread;
import com.example.project.test.LoginThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class yuyue extends Activity{
	String user="";
    String name="";
    String time="";
    private ArrayList<String> n_devicename = new ArrayList<String>();
    private ArrayList<String> n_devicesum = new ArrayList<String>();
    private ArrayList<String> nn_devicename = new ArrayList<String>();
    private ArrayList<String> nn_devicesum = new ArrayList<String>();
    private String device = "";
    private String stadium="";
    private String success="";
    private String sum = "";
    private String nday="";
    private String today="";
    private int index = 0;
    boolean is_end = false;
	Handler handler1,handler2;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.yuyue);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		user = data.getString("user");
		stadium = data.getString("stadium");
		date();
		//init();
		//draw_device(is_end);
		final View v = (View)findViewById(R.id.right);
        v.setBackgroundColor(Color.WHITE);
        final View vv = (View)findViewById(R.id.left);
        vv.setBackgroundColor(Color.RED);
        final Button listen_back = (Button)findViewById(R.id.back);
        listen_back.setOnClickListener(new listen_back());
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
					n_devicesum = bundle.getStringArrayList("n_devicesum");
					nn_devicesum = bundle.getStringArrayList("nn_devicesum");
					n_devicename = bundle.getStringArrayList("n_devicename");
					nn_devicename = bundle.getStringArrayList("nn_devicename"); 
					draw_device(is_end);
			}
		};
		handler2 = new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.obj!=null){
					Toast.makeText(getApplication(), success,Toast.LENGTH_SHORT).show();
				}
			}
		};
        final Button signed = (Button)findViewById(R.id.signed);
        signed.setText(nday.substring(5));
        signed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final View left = (View)findViewById(R.id.left);
				left.setBackgroundColor(Color.WHITE);
				final View right = (View)findViewById(R.id.right);
				right.setBackgroundColor(Color.RED);
				if(!is_end){
					is_end = true;
					draw_device(is_end);
				}
			}
		});
        final Button signing = (Button)findViewById(R.id.signing);
        signing.setText(today.substring(5));
		signing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final View left = (View)findViewById(R.id.left);
				left.setBackgroundColor(Color.RED);
				final View right = (View)findViewById(R.id.right);
				right.setBackgroundColor(Color.WHITE);
				if(is_end){
					is_end = false;
					draw_device(is_end);
				}
			}
		});
		Thread loginThread = new Thread(new thread());
		loginThread.start(); 
    }
    private void init(){
    	for(int i = 0 ; i < 20 ; i++ ){
    		nn_devicename.add(i+"00");
    		nn_devicesum.add(i+"00");
    		n_devicename.add(i+"11");
    		n_devicesum.add(i+"11");
    	}
    }
    private void draw_device(boolean sign){
    	int listlength = 0;
    	RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
    	r.removeAllViews();
    	if(!is_end){
    		listlength = n_devicename.size();
    	}else{
    		listlength = nn_devicename.size();
    	}
    	for(int i = 0 ; i < listlength ; i++){
			//新加的
    		View vv = new View(this);
    		android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param0.height = 100;
		    param0.width = 1300;
			param0.leftMargin = 1;
			param0.topMargin = 1 + 110*i;
                 
    		vv.setBackgroundResource(R.drawable.shape);
    		r.addView(vv,param0);
    		View v = new View(this);
    		android.widget.RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param.height = 60;
    		param.width = 60;
    		param.leftMargin = 20;
    		param.topMargin = 20 + 110*i;  		
    		v.setBackgroundResource(R.drawable.hot);
    		r.addView(v,param);
    		TextView tt = new TextView(this);
    		android.widget.RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param2.height = 60;
    		param2.width = 200;
    		param2.leftMargin = 150;
    		param2.topMargin = 30 + 110*i;
    		if(!is_end){
    			tt.setText(n_devicename.get(i));
    		}
    		else{
    			tt.setText(nn_devicename.get(i));
    		}
    		tt.setTextSize(15);
    		r.addView(tt,param2);
    		final TextView bu = new TextView(this);
    		android.widget.RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param4.height = 60;
    		param4.width = 150;
    		param4.leftMargin = 500;
    		param4.topMargin = 35 + 110*i;
    		bu.setText("预约");
    	    //bu.setBackgroundResource(R.drawable.shape);
    	    bu.setTextColor(Color.RED);
    	    bu.setTextSize(11);
    		r.addView(bu,param4);
    		bu.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
				// TODO Auto-generated method stub
    				System.out.println(bu.getBottom());
    				index = (bu.getBottom() - 95) / 110;
    				Thread change = new Thread(new chan());
    				change.start();
    				Thread insuser = new Thread(new istuser());
    				insuser .start();
    			}
    		});
    	}
    }
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(yuyue.this,infopage.class);//test其实是login
			data.putString("user",user);
			data.putString("name", name);
			data.putString("stadium", stadium);
			intent.putExtras(data);
			startActivity(intent);
			finish();
    	}
	}
    private void date(){
    	int year,month,day;
        Calendar now = Calendar.getInstance();
   	 	year = now.get(Calendar.YEAR);
   	 	month = now.get(Calendar.MONTH)+1;
   	 	day = now.get(Calendar.DAY_OF_MONTH);
   	 	today = make_date(year,month,day);
   	 	if(month == 2)
   	 	{
   	 		if(day<28)
   	 		{
   	 			day=day+1;
   	 		}
   	 		else
   	 		{
   	 			month=month+1;
   	 			day=1;
   	 		}
   	 	}
   	 	else if(month==12)
   	 	{
   	 	    if(day<31)
	 		{
	 			day=day+1;
	 		}
	 		else
	 		{
	 			year++;
	 			month=1;
	 			day=1;
	 		}
   	 	}
   	 	else if(month==1&&month==3&&month==5&&month==7&&month==8&&month==10)
   	 	{
   	 	    if(day<31)
	 		{
	 			day=day+1;
	 		}
	 		else
	 		{
	 			month=month+1;
	 			day=1;
	 		}
   	 	}
   	 	else
   	 	{
	   	 	if(day<30)
	 		{
	 			day=day+1;
	 		}
	 		else
	 		{
	 			month=month+1;
	 			day=1;
	 		}
   	 	}
   	 	nday = make_date(year,month,day);
   	 	System.out.println(today +"    "+nday);
    }
    public String make_date(int year , int month , int day){
    	String temp = "";
    	temp = temp + year;
    	if(month <= 9){
    	   temp = temp + "-0" + month;
    	}
    	else{
    		temp = temp + "-" + month;
    	}
    	if(day <= 9){
    		temp = temp + "-0" + day;
    	}
    	else{
    		temp = temp + "-" + day;
    	}
    	return temp;
    }


	

	public void sendjson(){
		int number = 0;
		String url = "http://192.168.191.1:8080/web/deviceServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = stadium;
			Object today_t = today;
			Object next_t = nday;
			json1.put("username", username);
			json1.put("today", today_t);
			json1.put("nday", next_t);
			StringEntity se = new StringEntity(json1.toString(),"UTF-8");
			//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String s;
			StringBuffer result = new StringBuffer("");
			while((s=reader.readLine()) != null){
				result.append(s);
			}
			reader.close();
			JSONObject json = new JSONObject(result.toString());
			JSONArray temp_ndn = json.getJSONArray("n_devicename");
			JSONArray temp_nndn = json.getJSONArray("nn_devicename");
			JSONArray temp_nds = json.getJSONArray("n_devicesum");
			JSONArray temp_nnds = json.getJSONArray("nn_devicesum");
			for(int i = 0 ; i < temp_ndn.length() ; i++){
				n_devicename.add(temp_ndn.getString(i));
			}
			for(int i = 0 ; i < temp_nndn.length() ; i++){
				nn_devicename.add(temp_nndn.getString(i));
			}
			for(int i = 0 ; i < temp_nds.length() ; i++){
				n_devicesum.add(temp_nds.getString(i));
			}
			for(int i = 0 ; i < temp_nnds.length() ; i++){
			    nn_devicesum.add(temp_nnds.getString(i));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class thread implements Runnable{
		public void run(){
			sendjson();
			Message message=Message.obtain();
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("n_devicename",n_devicename);
			bundle.putStringArrayList("nn_devicename", nn_devicename);
			bundle.putStringArrayList("n_devicesum", n_devicesum);
			bundle.putStringArrayList("nn_devicesum", nn_devicesum);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}
	
	
	public void sendjson1(){
		int number = 0;
		String url = "http://192.168.191.1:8080/web/changeServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = stadium;
			Object day;
			if(!is_end){
				day = today;
			}
			else{
				day = nday;
			}
			Object devicename;
			if(!is_end){
				devicename = n_devicename.get(index);
			}
			else{
				devicename = nn_devicename.get(index);
			}
			json1.put("username", username);
			json1.put("time", day);
			json1.put("devicename",devicename);
			StringEntity se = new StringEntity(json1.toString(),"UTF-8");
			//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String s;
			StringBuffer result = new StringBuffer("");
			while((s=reader.readLine()) != null){
				result.append(s);
			}
			reader.close();
			JSONObject json = new JSONObject(result.toString());
			
            success = json.getString("success");
            System.out.println("@@@@"+success);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class chan implements Runnable{
		public void run(){
			sendjson1();
			Message m = handler2.obtainMessage();
			m.obj = success;
			handler2.sendMessage(m);
		}
	}
	public void sendjson2(){
		int number = 0;
		String url = "http://192.168.191.1:8080/web/insuserServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = stadium;
			Object day;
			Object muser = user;
			if(!is_end){
				day = today;
			}
			else{
				day = nday;
			}
			Object devicename;
			if(!is_end){
				devicename = n_devicename.get(index);
			}
			else{
				devicename = nn_devicename.get(index);
			}
			json1.put("user", username);
			json1.put("time", day);
			json1.put("devicename",devicename);
			json1.put("person", muser);
			StringEntity se = new StringEntity(json1.toString(),"UTF-8");
			//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String s;
			StringBuffer result = new StringBuffer("");
			while((s=reader.readLine()) != null){
				result.append(s);
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class istuser implements Runnable{
		public void run(){
			sendjson2();
		}
	}



}
