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

public class userdevice extends Activity{
	String user="";
    String name="";
    String time="";
    private String devicename;
    private ArrayList<String> users = new ArrayList<String>();
	Handler handler1;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userdevice);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		user = data.getString("user");
		devicename = data.getString("devicename");
		time = data.getString("time");
		final Button listen_back = (Button)findViewById(R.id.back);
		listen_back.setOnClickListener(new listen_back());
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
					users = bundle.getStringArrayList("users");
					draw_user();
			}
		};
		Thread loginThread = new Thread(new thread());
		loginThread.start(); 
    }
    private void draw_user(){
    	int listlength = 0;
    	RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
    	r.removeAllViews();
    	listlength = users.size();
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
    		param.height = 60 ;
    		param.width = 60 ;
    		param.leftMargin = 20;
    		param.topMargin = 20 + 110*i;  		
    		v.setBackgroundResource(R.drawable.hot);
    		r.addView(v,param);
    		TextView tt = new TextView(this);
    		android.widget.RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		param2.height = 60;
    		param2.width = 400;
    		param2.leftMargin = 100;
    		param2.topMargin = 30 + 110 * i;
            tt.setText("用户  " + users.get(i)+" 已预约");
    		tt.setTextSize(15);
    		r.addView(tt,param2);	
    	}
    }
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(userdevice.this,infodevice.class);//test其实是login
			data.putString("user",user);
			data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
			finish();
    	}
	}
	public void sendjson(){
		int number = 0;
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		user = data.getString("user");
		String url = "http://192.168.191.1:8080/web/userdeviceServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = user;
			Object ti = time;
			Object dn = devicename;
			json1.put("username", username);
			json1.put("time", ti);
			json1.put("devicename", dn);
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
			JSONArray temp = json.getJSONArray("users");
			for(int i = 0 ; i < temp.length() ; i++){
				users.add(temp.getString(i));
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
			bundle.putStringArrayList("users",users);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}
}
