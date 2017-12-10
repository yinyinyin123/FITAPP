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

import com.example.project.editinf.listen_back;
import com.example.project.infopage.pfthread;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class stadium extends Activity implements ViewFactory,OnTouchListener{
	private ImageSwitcher mImageSwitcher;
    private int[] images;
    private int flag = 1;
    private int currentPosition=0;
    private float downX;
    private boolean free = false;
    private String name="";
	private String user = "";
	private String path;
   	private String staname;
   	private String staoptime;
   	private String staadddr;
   	private String statele;
   	private String stapri;
   	private String staother;
   	Handler handler1;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.admin);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		user = data.getString("user");
        final ImageButton editinf = (ImageButton)findViewById(R.id.editinf);
        
        editinf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(stadium.this,editinf.class);
				Bundle data = new Bundle();
				data.putString("user",user);
				data.putString("name", name);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});
        final Button importnews = (Button)findViewById(R.id.importnews) ;
        importnews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(stadium.this,writematch.class);
				Bundle data = new Bundle();
				data.putString("user",user);
				data.putString("name", name);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});
        final Button deviceb = (Button)findViewById(R.id.watchdevice);
        
        deviceb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(stadium.this,editinf.class);
				if(!free){
					Intent intent = new Intent(stadium.this,infodevice.class);
					//Intent intent = new Intent(stadium.this,fsdevice.class);
					Bundle data = new Bundle();
					data.putString("user",user);
					data.putString("name", name);
					intent.putExtras(data);
					startActivity(intent);
					finish();
				}
				else{
					Intent intent = new Intent(stadium.this,fsdevice.class);
					//Intent intent = new Intent(stadium.this,fsdevice.class);
					Bundle data = new Bundle();
					data.putString("user",user);
					data.putString("name", name);
					intent.putExtras(data);
					startActivity(intent);
					finish();
				}
			}
		});
        
//        final Button importnews = (Button)findViewById(R.id.importnews);
//        importnews.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(stadium.this,editinf.class);
//				Bundle data = new Bundle();
//				data.putString("user",user);
//				data.putString("name", name);
//				intent.putExtras(data);
//				startActivity(intent);
//			}
//		});
        String kind = getIntent().getStringExtra("kind");
		images = new int[]{R.drawable.sta1,R.drawable.stta2,R.drawable.sta3};
		mImageSwitcher = (ImageSwitcher)findViewById(R.id.imageViewadmin);
		mImageSwitcher.setFactory(this);
		mImageSwitcher.setOnTouchListener(this);
		//currentPosition = getIntent().getIntExtra("positon",0);
		mImageSwitcher.setImageResource(images[currentPosition]);
		final Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new listen_back());
		Thread infoThread = new Thread(new pfthread());
		infoThread.start();
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
					staname = bundle.getString("staname");
					staoptime = bundle.getString("staoptime");
					staadddr = bundle.getString("staadddr");
					statele = bundle.getString("statele");
					staother = bundle.getString("staother");
					stapri = bundle.getString("stapri");
					if(stapri.equals("免费")){
						free = true;
					}
				  	final TextView staddname=(TextView)findViewById(R.id.staddname);
					final TextView staddadress=(TextView)findViewById(R.id.staddadress);
					final TextView staddother=(TextView)findViewById(R.id.staddother);
					final TextView staddprice=(TextView)findViewById(R.id.staddprice);
					final TextView staddtele=(TextView)findViewById(R.id.staddtele);
					final TextView staddtime=(TextView)findViewById(R.id.staddtime);
					System.out.println(path+"9999999");
					staddname.setText(staname);
					staddother.setText(staother);
					staddadress.setText(staadddr);
					staddprice.setText(stapri);
					staddtele.setText(statele);
					staddtime.setText(staoptime);
			}
		};
	}
	
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(stadium.this,MainActivity.class);//test其实是login
			data.putString("user",user);
			data.putString("name", name);
			intent.putExtras(data);
			startActivity(intent);
			finish();
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
	public void sendjson3(){
		int number = 0;
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		user = data.getString("user");
		String url = "http://192.168.191.1:8080/web/stadiumServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object stadiums = user+"stadium";
			json1.put("user", stadiums);
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
			staname = json.getString("stadiumname");
		    staoptime= json.getString("opentime");
		    staadddr = json.getString("stadiumaddr");
		    statele = json.getString("tele");
		    stapri = json.getString("price");
		    staother = json.getString("other");
		    path = json.getString("stadiumpic");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	class pfthread implements Runnable{
		public void run(){
			sendjson3();
			Message message=Message.obtain();
			Bundle bundle = new Bundle();
			bundle.putString("staname",staname);
			bundle.putString("staoptime", staoptime);
			bundle.putString("staadddr", staadddr);
			bundle.putString("statele", statele);
			bundle.putString("staother", staother);
			bundle.putString("path",path);
			bundle.putString("stapri", stapri);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}


}
