package com.example.project;



import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.os.Handler;
import android.support.v4.graphics.drawable.*;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Message;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class test extends Activity{
	private String responseMsg = "";
	Handler handler1;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		final Button login_button = (Button)findViewById(R.id.login);
		login_button.setOnClickListener(new login_listen());
		OvalShape ovalShape = new OvalShape();
		ShapeDrawable drawable0 = new ShapeDrawable(ovalShape);
		drawable0.getPaint().setColor(Color.RED);
		drawable0.getPaint().setStyle(Paint.Style.FILL);
		drawable0.getPaint().setAntiAlias(true);
		drawable0.getPaint().setStrokeWidth(5);
		RoundedBitmapDrawable drawable3 = RoundedBitmapDrawableFactory.create(getResources(),BitmapFactory.decodeResource(getResources(),R.drawable.yuga));
		drawable3.setCornerRadius(300);
		drawable3.setAntiAlias(true);
		Drawable[] layers = new Drawable[2];
		layers[0] = drawable0;
		layers[1] = drawable3;
		LayerDrawable layerDrawable = new LayerDrawable(layers);
		layerDrawable.setLayerInset(0, 5, 5, 5, 5);
		layerDrawable.setLayerInset(1, 10, 10,10, 10);
		final View view = findViewById(R.id.view);
		view.setBackground(layerDrawable);
		final Button button_register = (Button)findViewById(R.id.zhuche);
		button_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(test.this,register.class);//test其实是login
				startActivity(intent);
				finish();
			}
		});
		final Button button_back = (Button)findViewById(R.id.back);
		button_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    		Intent intent = new Intent(test.this,MainActivity.class);//test其实是login
				startActivity(intent);
				finish();
			}
		});
		final Button login_bu = (Button)findViewById(R.id.zhuche);
		login_bu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    		Intent intent = new Intent(test.this,register.class);//test其实是login
				startActivity(intent);
				finish();
			}
		});
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.obj!=null){
					if(!responseMsg.substring(0,7).equals("success")){
						Toast.makeText(getApplication(), responseMsg,Toast.LENGTH_SHORT).show();
					}
				}
			}
		};
	}
	class login_listen implements OnClickListener{
		@Override
		public void onClick(View v){
			Thread loginThread = new Thread(new LoginThread());
			loginThread.start();
//			Thread loginThread1 = new Thread(new login());
//			loginThread1.start();
		}
	}
	public boolean loginserver(){
		boolean loginValidate = false;
		final EditText user = (EditText)findViewById(R.id.username);
		final EditText pass = (EditText)findViewById(R.id.password);
		String username = user.getText().toString();
		String password = pass.getText().toString();
		System.out.println(username+" "+password);
		String url = "http://192.168.191.1:8080/web/FirstServlet";
		url = url + "?" + "username=" + username + "&password=" + password;
		HttpClient client = new DefaultHttpClient();
    	HttpGet request  = new HttpGet(url);
    	try{   		
    		HttpResponse response = client.execute(request);
    		if(response.getStatusLine().getStatusCode()==200){
    			loginValidate = true;
    			responseMsg = EntityUtils.toString(response.getEntity());	
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return loginValidate;
	}
	class LoginThread implements Runnable{
		public void run(){
			boolean loginValidate = loginserver();
			Message m = handler1.obtainMessage();
			m.obj = responseMsg;
			handler1.sendMessage(m);
			Bundle data = new Bundle();
			System.out.println(responseMsg.substring(0,8));
			if(responseMsg.substring(0,8).equals("successy")){
				Intent intent = new Intent(test.this,myhomepage.class);//test其实是login
				int te = responseMsg.indexOf(" ");
				String name = responseMsg.substring(te+1);
				data.putString("user",responseMsg.substring(8,te));
				data.putString("name", name);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}else if(responseMsg.substring(0,8).equals("successn")){
				Intent intent = new Intent(test.this,stadium.class);
				int te = responseMsg.indexOf(" ");
				String name = responseMsg.substring(te+1);
				data.putString("user", responseMsg.substring(8,te));
				data.putString("name", name);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		}
	}
	public void sendjson(){
		String url = "http://192.168.191.1:8080/web/FirstServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		final EditText user = (EditText)findViewById(R.id.username);
		final EditText pass = (EditText)findViewById(R.id.password);
		String muser = user.getText().toString();
		String mpass = pass.getText().toString();
		try{
			JSONObject json1 = new JSONObject();
			Object username = muser;
			json1.put("username", username);
			Object pwd = mpass;
			json1.put("password", pwd);
			//System.out.print(json1.toString());
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
			String name = json.getString("login");
			System.out.println(name);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class login implements Runnable{
		public void run(){
			sendjson();
		}
	}

}
