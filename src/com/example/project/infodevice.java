
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

public class infodevice extends Activity{
	String user="";
    String name="";
    String time="";
    private ArrayList<String> n_devicename = new ArrayList<String>();
    private ArrayList<String> n_devicesum = new ArrayList<String>();
    private ArrayList<String> nn_devicename = new ArrayList<String>();
    private ArrayList<String> nn_devicesum = new ArrayList<String>();
    private String device = "";
    private String sum = "";
    private String nday="";
    private String today="";
    boolean is_end = false;
	Handler handler1;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.infodevice);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		user = data.getString("user");
		date();
		//init();
		//draw_device(is_end);
		final View v = (View)findViewById(R.id.right);
        v.setBackgroundColor(Color.WHITE);
        final View vv = (View)findViewById(R.id.left);
        vv.setBackgroundColor(Color.RED);
        final Button listen_back = (Button)findViewById(R.id.back);
        listen_back.setOnClickListener(new listen_bac());
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
		final Button add_device = (Button)findViewById(R.id.add_device);
		add_device.setOnClickListener(new listen_adddevice());
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
    		param2.height = 80;
    		param2.width = 1000;
    		param2.leftMargin = 100;
    		param2.topMargin = 30 + 110*i;
    		if(!is_end){
    			tt.setText(n_devicename.get(i));
    		}
    		else{
    			tt.setText(nn_devicename.get(i));
    		}
    		tt.setTextSize(15);
    		r.addView(tt,param2);
    		TextView t = new TextView(this);
    		android.widget.RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param1.height = 80;
    		param1.width = 200;
    		param1.leftMargin = 350;
    		param1.topMargin = 30 + 110*i;
    		if(!is_end){
    			t.setText("总数: "+n_devicesum.get(i));
    		}
    		else{
    			t.setText("总数: "+nn_devicesum.get(i));
    		}
    		t.setTextSize(15);
    		r.addView(t,param1);
//    		View vi = new View(this);
//    		android.widget.RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//    		param3.height = 80;
//    		param3.width = 200;
//    		param3.leftMargin = 350;
//    		param3.topMargin = 30 + 140*i;
//    		vi.setBackground(new ColorDrawable(Color.RED));
//    		r.addView(vi,param3);	
    		final ImageButton bu = new ImageButton(this);
    		android.widget.RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param4.height = 40;
    		param4.width = 40;
    		param4.leftMargin = 600;
    		param4.topMargin = 35 + 110*i;
    		bu.setBackgroundResource(R.drawable.next);
    		r.addView(bu,param4);
    		bu.setOnClickListener(new OnClickListener() {
			
    			@Override
    			public void onClick(View v) {
				// TODO Auto-generated method stub
    				System.out.println(bu.getBottom());
    				int j = (bu.getBottom() - 60) / 110;
    				Bundle data = new Bundle();
    	    		Intent intent = new Intent(infodevice.this,userdevice.class);//test其实是login
    				data.putString("user",user);
    				data.putString("name", name);
    				if(!is_end){
    				   data.putString("devicename",n_devicename.get(j));
    				   data.putString("time",today );
    				}
    				else{
    					data.putString("devicename", nn_devicename.get(j));
    					data.putString("time", nday);
    				}
    				intent.putExtras(data);
    				startActivity(intent);
    				finish();
    			}
    		});
    	}
    }
	class listen_bac implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(infodevice.this,stadium.class);//test其实是login
			data.putString("user",user);
			data.putString("name", name);
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
    public void showdialog_sum(){
    	final EditText edit = new EditText(this);
		AlertDialog.Builder inputdialog=new AlertDialog.Builder(this);
		inputdialog.setTitle("设备总量").setView(edit);
		inputdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(!is_end){
					n_devicesum.add(edit.getText().toString());
				}
				else{
					nn_devicesum.add(edit.getText().toString());
				}
				sum = edit.getText().toString();
				//showdialog_sum();
				draw_device(is_end);
				Thread loginThread1 = new Thread(new insertthread());
				loginThread1.start(); 
				
			}
		}).show();
    }
    public void showdialog_device(){
		final EditText edit = new EditText(this);
		AlertDialog.Builder inputdialog=new AlertDialog.Builder(this);
		inputdialog.setTitle("设备名").setView(edit);
		inputdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(!is_end){
					n_devicename.add(edit.getText().toString());
				}
				else{
					nn_devicename.add(edit.getText().toString());
				}
				device = edit.getText().toString();
				showdialog_sum();
//				Thread loginThread1 = new Thread(new insertthread());
//				loginThread1.start(); 
				
			}
		}).show();
	}
	
	class listen_adddevice implements OnClickListener{
		@Override
		public void onClick(View v){
			showdialog_device();
		}
	}
	public void sendjson1(){
		int number = 0;
//		Intent intent = getIntent();
//		Bundle data = intent.getExtras();
//		user = data.getString("user");
		String url = "http://192.168.191.1:8080/web/insdevServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			String t;
			if(is_end){
				t = nday;
			}
			else{
				t = today;
			}
			Object time = t;
			Object username = user;
			Object devicename = device ;
			Object devicesum = sum;
			json1.put("username", username);
			json1.put("devicename", devicename);
			json1.put("devicesum", devicesum);
			json1.put("time", time);
			StringEntity se = new StringEntity(json1.toString(),"UTF-8");
			//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class insertthread implements Runnable{
		public void run(){
			sendjson1();
		}
	}
	public void sendjson(){
		int number = 0;
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		user = data.getString("user");
		String url = "http://192.168.191.1:8080/web/deviceServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = user;
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



}

