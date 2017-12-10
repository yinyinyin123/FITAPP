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
import android.support.v7.app.AppCompatActivity;
import android.os.Message;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import com.example.project.myhomepage.listen_back;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class register extends Activity {
	private String name="";
	private String user = "";
	private String pass = "";
	private String restudent =""; 
	private String returnvalue = "";
	Handler handler1;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		final Button button_register = (Button)findViewById(R.id.zhuce);
		final Button button_back = (Button)findViewById(R.id.back);
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.obj!=null){
					if(returnvalue.equals("user has existed")){
						Toast.makeText(getApplication(), returnvalue,Toast.LENGTH_SHORT).show();
					}
				}
			}
		};
		button_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(register.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		final  RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup1);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radio = group.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton)findViewById(radio);
				if(rb.getText().equals("成为会员")){
					restudent = "y";
				}else{
					restudent = "n";
				}
			}
		}); 
		button_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText username = (EditText)findViewById(R.id.username);
				final EditText mpass = (EditText)findViewById(R.id.password);
				final EditText name1 = (EditText)findViewById(R.id.nicheng);
				user = username.getText().toString();
				pass = mpass.getText().toString();
				name = name1.getText().toString();
				Thread registerthread = new Thread(new thread());
				registerthread.start();	
				
				System.out.println(restudent);
//				if(restudent.equals("y")){
//					Intent intent = new Intent(register.this,myhomepage.class);//test其实是login
//					data.putString("user",user);
//					data.putString("name", name);
//					intent.putExtras(data);
//					startActivity(intent);
//					finish();
//				}
//				else
//				{
//					Intent intent = new Intent(register.this,stadium.class);//test其实是login
//					data.putString("user",user);
//					data.putString("name", name);
//					intent.putExtras(data);
//					startActivity(intent);
//					finish();
//				}
			}
		});
	}
	public void sendjson(){
		String url = "http://192.168.191.1:8080/web/registerServlet";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = user;
			json1.put("username", username);
			Object pwd = pass;
			json1.put("password", pwd);
			Object stu = restudent;
			json1.put("student", stu);
			Object mname = name;
			json1.put("name", mname);
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
			Bundle data = new Bundle();
			returnvalue = json.getString("exist");
			if(json.getString("exist").equals("ok")){
				if(restudent.equals("y")){
					Intent intent = new Intent(register.this,myhomepage.class);//test其实是login
					data.putString("user",user);
					data.putString("name", name);
					intent.putExtras(data);
					startActivity(intent);
					finish();
				}
				else
				{
					Intent intent = new Intent(register.this,stadium.class);//test其实是login
					data.putString("user",user);
					data.putString("name", name);
					intent.putExtras(data);
					startActivity(intent);
					finish();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	class thread implements Runnable{
		@Override
		public void run(){
			sendjson();
			Message m = handler1.obtainMessage();
			m.obj = returnvalue;
			handler1.sendMessage(m);
		}
	}
	
}
