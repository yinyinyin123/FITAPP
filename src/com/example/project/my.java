package com.example.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.example.project.test.LoginThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class my extends Activity{
	String user="";
	String mon="";
	String tues="";
	String wednes="";
	String thur="";
	String fri="";
	String sau="";
	String sun="";
    String exist = "";
	Handler handler1;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my);
		String name;
		final TextView text = (TextView)findViewById(R.id.textView1);
		final TextView text_Monday = (TextView)findViewById(R.id.Monday);
		final TextView text_Tuesday =(TextView)findViewById(R.id.Tuesday);
		final TextView text_Wednesday = (TextView)findViewById(R.id.Wednesday);
		final TextView text_Thursday = (TextView)findViewById(R.id.Thursday);
		final TextView text_Friday = (TextView)findViewById(R.id.Friday);
		final TextView text_Saturday = (TextView)findViewById(R.id.Saturday);
		final TextView text_Sunday = (TextView)findViewById(R.id.Sunday);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("user");
		user = name;
		text.setText("尊敬的"+user);
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
					if(bundle.getString("exist").equals("true")){
						text_Monday.setText(bundle.getString("Mon"));
						text_Tuesday.setText(bundle.getString("Tue"));
						text_Wednesday.setText(bundle.getString("wednes"));
						text_Thursday.setText(bundle.getString("Thur"));
						text_Friday.setText(bundle.getString("Fri"));
						text_Saturday.setText(bundle.getString("Sau"));
						text_Sunday.setText(bundle.getString("Sun"));				
					}else{
						text_Monday.setText("");
					    text_Tuesday.setText("");
					    text_Wednesday.setText("");
					    text_Thursday.setText("");
					    text_Friday.setText("");
					    text_Saturday.setText("");
					    text_Sunday.setText("");
					}
					
			}
		};
		Thread loginThread = new Thread(new tableThread());
		loginThread.start();
		final Button button_homepage = (Button)findViewById(R.id.homepage_my);
		button_homepage.setOnClickListener(new listen_homepage());
    }
    class listen_homepage implements OnClickListener{
    	@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(my.this,MainActivity.class);//test其实是login
			data.putString("user",user);
			intent.putExtras(data);
			startActivity(intent);
    	}
    }
    public void sendjson(){
		String url = "http://192.168.191.1:8080/web/TableServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = user;
			json1.put("username", username);
			StringEntity se = new StringEntity(json1.toString());
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
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
			mon = json.getString("Monday");
			tues = json.getString("Tuseday");
			wednes = json.getString("Wednesday");
			thur = json.getString("Thurday");
			fri = json.getString("Friday");
			sau = json.getString("Sartuday");
			sun = json.getString("Sunday");
			exist = json.getString("exist");	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
    class tableThread implements Runnable{
		public void run(){
			sendjson();
			Message message=Message.obtain();
			Bundle bundle = new Bundle();
			bundle.putString("Mon", mon);
			bundle.putString("Tue", tues);
			bundle.putString("wednes", wednes);
			bundle.putString("Thur",thur );
			bundle.putString("Fri", fri);
			bundle.putString("Sau", sau);
			bundle.putString("Sun", sun);
			bundle.putString("exist", exist);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}
}
