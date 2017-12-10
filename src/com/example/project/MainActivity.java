package com.example.project;

//import com.example.helloandroid.MainActivity;
//import com.example.helloandroid.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.project.test.LoginThread;
//第一次迭代产物
public class MainActivity extends Activity implements ViewFactory,OnTouchListener{
    private ImageSwitcher mImageSwitcher;
    private int[] images;
    private int currentPosition=0;
    private float downX;
    private boolean login = true;
    private String username = "";
    private String name = "";
    private String responseMsg = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		images = new int[]{R.drawable.bai1,R.drawable.bjt,R.drawable.bai3};
		mImageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
		mImageSwitcher.setFactory(this);
		mImageSwitcher.setOnTouchListener(this);
		mImageSwitcher.setImageResource(images[currentPosition]);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		System.out.print(data);
		if(data == null){
			
		}
		else{
			username = data.getString("user");
			if(username == null || username.equals("")){
				login = true;
			}
			else{
				login = false;
				name = data.getString("name");
			}
		}
		final ImageButton my = (ImageButton)findViewById(R.id.myhome);
		my.setOnClickListener(new listen_my());
		final Button fit = (Button)findViewById(R.id.outdoor);
		fit.setOnClickListener(new listen_fit());
		final Button match = (Button)findViewById(R.id.match);
		match.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,match.class);
				Bundle data = new Bundle();
				data.putString("user",username);
				data.putString("name", name);
				intent.putExtras(data);
				startActivity(intent);	
				finish();
			}
		});
		final Button swim = (Button)findViewById(R.id.swim);
		swim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				Bundle data = new Bundle();
				data.putString("user",username);
				data.putString("name", name);
				data.putString("stadium", "swim");
				intent.putExtras(data);
				startActivity(intent);	
				finish();
			}
		});
		final Button bask = (Button)findViewById(R.id.basketball);
		bask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				Bundle data = new Bundle();
				data.putString("user",username);
				data.putString("name", name);
				data.putString("stadium", "basketball");
				intent.putExtras(data);
				startActivity(intent);	
				finish();
			}
		});
		final Button badmin = (Button)findViewById(R.id.badminton);
		badmin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				Bundle data = new Bundle();
				data.putString("user",username);
				data.putString("name", name);
				data.putString("stadium", "badmin");
				intent.putExtras(data);
				startActivity(intent);	
				finish();
			}
		});
		final Button tennis = (Button)findViewById(R.id.tennis);
		tennis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				Bundle data = new Bundle();
				data.putString("user",username);
				data.putString("name", name);
				data.putString("stadium","tennis");
				intent.putExtras(data);
				startActivity(intent);	
				finish();
			}
		});
		final Button fitt = (Button)findViewById(R.id.fit);
		fitt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,infopage.class);
				Bundle data = new Bundle();
				data.putString("user",username);
				data.putString("name", name);
				data.putString("stadium", "fit");
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});
		final Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("yinzhibo");
			}
		});
	}
	class listen_fit implements OnClickListener{
		@Override
		public void onClick(View V){
			Intent intent = new Intent(MainActivity.this,outdoor.class);
			Bundle data = new Bundle();
			data.putString("user",username);
			data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
			finish();
			
		}
	}
	class listen_my implements OnClickListener{
		@Override
		public void onClick(View v){
			System.out.println("2313141412");
			if(login == true){
				Intent intent = new Intent(MainActivity.this,test.class);//test其实是login
				startActivity(intent);
				finish();
			}else{
				Thread loginThread = new Thread(new LoginThread());
				loginThread.start();
			}
		}
	}
	public boolean loginserver(){
		boolean loginValidate = false;
		String username1 = username;
		String url = "http://192.168.191.1:8080/web/kindServlet";
		url = url + "?" + "username=" + username1;
		HttpClient client = new DefaultHttpClient();
    	HttpGet request  = new HttpGet(url);
    	try{   		
    		HttpResponse response = client.execute(request);
    		if(response.getStatusLine().getStatusCode()==200){
    			loginValidate = true;
    			responseMsg = EntityUtils.toString(response.getEntity());
    			Bundle data = new Bundle();
    			if(responseMsg.equals("successy")){
    				Intent intent = new Intent(MainActivity.this,myhomepage.class);//test其实是login
    				data.putString("user",username);
    				data.putString("name", name);
    				intent.putExtras(data);
    				startActivity(intent);
    				finish();
    			}else if(responseMsg.equals("successn")){
    				Intent intent = new Intent(MainActivity.this,stadium.class);
    				data.putString("user", username);
    				data.putString("name", name);
    				intent.putExtras(data);
    				startActivity(intent);
    				finish();
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return loginValidate;
	}
	class LoginThread implements Runnable{
		public void run(){
			boolean loginValidate = loginserver();
		}
	}
    @Override
    public View makeView(){
    	final ImageView i = new ImageView(this);
    	i.setBackgroundColor(0xff000000);
    	i.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	i.setLayoutParams(new ImageSwitcher.LayoutParams(android.widget.TableLayout.LayoutParams.FILL_PARENT,android.widget.TableLayout.LayoutParams.FILL_PARENT));
    	return i;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @Override
    public boolean onTouch(View v,MotionEvent event){
    	switch(event.getAction()){
    	  case MotionEvent.ACTION_DOWN:{
    		  downX = event.getX();
    		  break;
    	  }
    	  case MotionEvent.ACTION_UP:{
    		  float lastX = event.getX();
    		  if(lastX > downX){
    			  if(currentPosition > 0){
    				  mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
    				  mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
    				  currentPosition--;
    				  mImageSwitcher.setImageResource(images[currentPosition]);
    			  }
    			  else{
    				  currentPosition = images.length-1;
    				  mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
    				  mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
    				  mImageSwitcher.setImageResource(images[currentPosition]);
    				  //Toast.makeText(getApplication(), "the first picture", Toast.LENGTH_SHORT).show();
    			  }
    		  }
    		  if(lastX < downX){
    			  if(currentPosition < images.length-1){
    				  mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_in));
    				  mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_out));
    				  currentPosition++;
    				  mImageSwitcher.setImageResource(images[currentPosition]);
    			  }
    			  else{
    				  //Toast.makeText(getApplication(), "the last picture",Toast.LENGTH_SHORT).show();
    				  currentPosition = 0;
    				  mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_in));
    				  mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_out));
    				  mImageSwitcher.setImageResource(images[currentPosition]);
    			  }
    		  }
    	  }
    	  break;
    	}
    	return true;
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
