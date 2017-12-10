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
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.example.project.myhomepage.MyDialog;
import com.example.project.myhomepage.listen_back;
import com.example.project.register.thread;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class writematch  extends Activity{
	final int dialog=1;
	String user = "";
	String nname = "";
	int[] hhh={R.string.lan,R.string.yu,R.string.tian,R.string.wang,R.string.zu};
	private String matchname="";
	private String matchhost="";
	private String matchaddr="";
	private String matchjoin="";
	private String time1="";
	private String time2="";
	private String type="";
	private String matchinfo="";
	private String matchid="";
	Handler handler1;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.watchmatch);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		nname = data.getString("name");
		user = data.getString("user");
		final Button button_send = (Button)findViewById(R.id.send);
		final Button button_back = (Button)findViewById(R.id.back);
		final Button button_change1 = (Button)findViewById(R.id.changetime1);
		final Button button_change2 = (Button)findViewById(R.id.changetime2);
		final Spinner sp=(Spinner)findViewById(R.id.matchtype);
		final BaseAdapter ba= new BaseAdapter(){
			@Override
			public int getCount() {return 5;}
			@Override
			public Object getItem(int arg0) {return null;}
			@Override
			public long getItemId(int arg0) {return 0;}
			@Override
			public View getView(int arg0,View arg1,ViewGroup arg2) {
				LinearLayout ll= new LinearLayout(writematch.this);
				ll.setOrientation(LinearLayout.HORIZONTAL);
				TextView  tv= new TextView(writematch.this);
				tv.setText(getResources().getText(hhh[arg0]));
				tv.setTextSize(15);
				 ll.addView(tv);
				 return ll;		
			}
		};
		sp.setAdapter(ba);
		sp.setOnItemSelectedListener(
			new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0,View arg1,int arg2,long arg3)
				{
					LinearLayout ll=(LinearLayout)arg1;
					TextView  tvn=(TextView)ll.getChildAt(0);
					type=tvn.getText().toString();
							 
				}
				public void onNothingSeleced(AdapterView<?> arg0){ }
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
				
			});

		button_send.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final EditText name = (EditText)findViewById(R.id.matchname);
				final EditText host = (EditText)findViewById(R.id.host);
				final EditText addr = (EditText)findViewById(R.id.matchadd);
				final EditText join = (EditText)findViewById(R.id.baoming);
				final EditText info = (EditText)findViewById(R.id.matchinfo);
				matchname = name.getText().toString();
				matchhost = host.getText().toString();
				matchaddr = addr.getText().toString();
				matchjoin = join.getText().toString();
				matchinfo = info.getText().toString();
				for(int i = 1 ; i <= 5 ; i++){
					matchid = matchid + "" + (int)(Math.random()*9);
				}
				Thread writethread = new Thread(new thread());
				writethread.start();	
				Intent intent = new Intent(writematch.this,stadium.class);
				Bundle data = new Bundle();
				data.putString("user",user);
				data.putString("name",nname);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});
        button_back.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("fff");
				Intent intent = new Intent(writematch.this,stadium.class);
				Bundle data = new Bundle();
				data.putString("user",user);
				data.putString("name", nname);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});
        button_change1.setOnClickListener(new OnClickListener(){		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c= Calendar.getInstance();
				new DatePickerDialog(writematch.this,
						new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
								TextView nn=(TextView)findViewById(R.id.matchtime);
								nn.setText("比赛时间："+year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
								if(monthOfYear <= 8 && dayOfMonth < 10){
									 
									time1=year+"-0"+(monthOfYear+1)+"-0"+(dayOfMonth);
								}
								else if(monthOfYear <= 8 && dayOfMonth >= 10){
									 
									time1=year+"-0"+(monthOfYear+1)+"-"+(dayOfMonth);
								}
								else if(monthOfYear > 8 && dayOfMonth < 10){
									 
									time1=year+"-"+(monthOfYear+1)+"-0"+(dayOfMonth);
								}
								else{
									time1=year+"-"+(monthOfYear+1)+"-"+(dayOfMonth);
									 
								}
							}
						}
				,c.get(Calendar.YEAR)
				,c.get(Calendar.MONTH)
				,c.get(Calendar.DAY_OF_MONTH)).show();
				 
			}
        });
        button_change2.setOnClickListener(new OnClickListener(){		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c= Calendar.getInstance();
				new DatePickerDialog(writematch.this,
						new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
								TextView mm=(TextView)findViewById(R.id.jointime);
								 
								if(monthOfYear <= 8 && dayOfMonth < 10){
									 
									time2=year+"-0"+(monthOfYear+1)+"-0"+(dayOfMonth);
								}
								else if(monthOfYear <= 8 && dayOfMonth >= 10){
									 
									time2=year+"-0"+(monthOfYear+1)+"-"+(dayOfMonth);
								}
								else if(monthOfYear > 8 && dayOfMonth < 10){
									 
									time2=year+"-"+(monthOfYear+1)+"-0"+(dayOfMonth);
								}
								else{
									time2=year+"-"+(monthOfYear+1)+"-"+(dayOfMonth);
									 
								}
								mm.setText("报名截止时间："+year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
							}
						}
				,c.get(Calendar.YEAR)
				,c.get(Calendar.MONTH)
				,c.get(Calendar.DAY_OF_MONTH)).show();
				 
			}
        });
	}
	public void sendjson(){
		String url = "http://192.168.191.1:8080/web/writeServlet";
		HttpPost post = new HttpPost(url);
		
		try{
			JSONObject json1 = new JSONObject();
			Object name = matchname;
			json1.put("matchname", name);
			Object host = matchhost;
			json1.put("matchhost", host);
			Object addr = matchaddr;
			json1.put("matchaddr", addr);
			Object join = matchjoin;
			json1.put("matchjoin", join);
			Object mtime = time1;
			json1.put("time1", mtime);
			Object jtime =time2;
			json1.put("time2", jtime);
			Object jtype =type;
			json1.put("type", type);
			Object jinfo =matchinfo;
			json1.put("matchinfo", jinfo);
			Object matchID = matchid;
			json1.put("matchid", matchID);
			StringEntity se = new StringEntity(json1.toString(),"UTF-8");
			//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	class thread implements Runnable{
		@Override
		public void run(){
			sendjson();
		}
	}
	 
}
