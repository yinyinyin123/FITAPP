package com.example.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import com.example.project.myhomepage.thread;
import com.example.project.test.LoginThread;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
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

public class match extends Activity{
	private String user="";
    private String name="";
    boolean is_end = false;
    private ArrayList<String> matchID_ing = new ArrayList<String>();
    private ArrayList<String> matchID_ed = new ArrayList<String>();
    private ArrayList<String> matchname_ing = new ArrayList<String>();
    private ArrayList<String> matchname_ed = new ArrayList<String>();
    private ArrayList<String> matchtype_ing = new ArrayList<String>();
    private ArrayList<String> matchtype_ed = new ArrayList<String>();
	Handler handler1;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.match);
        final View v = (View)findViewById(R.id.right);
        v.setBackgroundColor(Color.WHITE);
        final View vv = (View)findViewById(R.id.left);
        vv.setBackgroundColor(Color.RED);
        final Button listen_back = (Button)findViewById(R.id.back);
        listen_back.setOnClickListener(new listen_back());
        Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		user = data.getString("user");
//        init();
//     	draw_match(is_end);
		Thread loginThread = new Thread(new thread());
		loginThread.start(); 
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
					matchname_ing = bundle.getStringArrayList("matchname_ing");
					matchname_ed = bundle.getStringArrayList("matchname_ed");
					matchID_ing = bundle.getStringArrayList("matchID_ing");
					matchID_ed = bundle.getStringArrayList("matchID_ed");
					matchtype_ing = bundle.getStringArrayList("matchtype_ing");
					matchtype_ed = bundle.getStringArrayList("matchtype_ed");
//					Thread drawThread = new Thread(new thread_draw());
//					drawThread.start(); 
					draw_match(is_end);
			}
		};
		final Button signed = (Button)findViewById(R.id.signed);
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
					draw_match(is_end);
				}
			}
		});
		final Button signing = (Button)findViewById(R.id.signing);
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
					draw_match(is_end);
				}
			}
		});
    }
    public void init(){
        for(int i = 1 ; i < 15 ; i++){
        	matchID_ing.add(""+i+"");
        	matchID_ed.add(""+i+"");
        	matchname_ing.add("这里是赛事"+i+"");
        	matchname_ed.add("这里是结束的赛事"+i+"");
        }
        
    }
    public void draw_match(boolean sign){
    	int listlength = 0;
    	RelativeLayout r = (RelativeLayout)findViewById(R.id.rr);
    	r.removeAllViews();
    	if(!is_end){
    		listlength = matchname_ing.size();
    	}else{
    		listlength = matchname_ed.size();
    	}
    	for(int i = 0 ; i < listlength; i++ ){
    		View vv = new View(this);
			android.widget.RelativeLayout.LayoutParams param0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			param0.height = 340;
		    param0.width = 1200;
			param0.leftMargin = 1;
			param0.topMargin = 1 + 350*i;
			if(!is_end){
				if(matchtype_ing.get(i).equals("篮球")){
					vv.setBackgroundResource(R.drawable.matchba);
				}
				else if(matchtype_ing.get(i).equals("足球")){
					vv.setBackgroundResource(R.drawable.matchfo);
				}
				else if(matchtype_ing.get(i).equals("网球")){
					vv.setBackgroundResource(R.drawable.matchten);
				}
				else if(matchtype_ing.get(i).equals("田径")){
					vv.setBackgroundResource(R.drawable.matchrun);
				}
				else if(matchtype_ing.get(i).equals("羽毛球")){
					vv.setBackgroundResource(R.drawable.matchbad);
				}
			}
			else{
				if(matchtype_ed.get(i).equals("篮球")){
					vv.setBackgroundResource(R.drawable.matchba);
				}
				else if(matchtype_ed.get(i).equals("足球")){
					vv.setBackgroundResource(R.drawable.matchfo);
				}
				else if(matchtype_ed.get(i).equals("网球")){
					vv.setBackgroundResource(R.drawable.matchten);
				}
				else if(matchtype_ed.get(i).equals("田径")){
					vv.setBackgroundResource(R.drawable.matchrun);
				}
				else if(matchtype_ed.get(i).equals("羽毛球")){
					vv.setBackgroundResource(R.drawable.matchbad);
				}
			}
			r.addView(vv,param0);
			final TextView text = new TextView(this);
			text.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("11111112414141");
					text.setText("14141");
				}
			});
			android.widget.RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			if(!is_end){
				text.setText(matchname_ing.get(i));
			}
			else{
				text.setText(matchname_ed.get(i));
			}
			text.setTextColor(Color.RED);
			text.setTextSize(13);
			param1.height = 60;
			param1.width = 500;
			param1.topMargin = 80 + 350*i;
			param1.leftMargin = 120;
			r.addView(text,param1);
		    final Button bu = new Button(this);
			android.widget.RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			param4.setMargins(950,30+i*130,70,1100-i*130);
			bu.setText("进入赛事");
			bu.setTextColor(Color.RED);
			//bu.setBackgroundResource(R.drawable.login_shape);
			bu.setTextSize(11);
			param4.height = 60;
			param4.width = 200;
			param4.leftMargin = 550;
			param4.topMargin = 210 + 350*i;		
			r.addView(bu,param4);
			bu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println(bu.getBottom());
					int position = bu.getBottom();
					int index = (position - 270) / 350;
					Intent intent = new Intent(match.this,matchifo.class);
					Bundle data = new Bundle();
    				data.putString("user", user);
    				data.putString("name", name);
    				if(is_end){
    					data.putString("matchID", matchID_ed.get(index));
    					System.out.println("结束了");
    				}else{
    					data.putString("matchID", matchID_ing.get(index));
    					System.out.println("还可以报名");
    				}
    				intent.putExtras(data);
    				startActivity(intent);
    				finish();
					
				}
			});
    	}
    }
	class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(match.this,MainActivity.class);//test其实是login
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
		String url = "http://192.168.191.1:8080/web/matchServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object username = user;
			json1.put("username", username);
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
			JSONArray temp_ing = json.getJSONArray("matchname_ing");
			JSONArray temp_ed = json.getJSONArray("matchname_ed");
			JSONArray tempid_ing = json.getJSONArray("matchid_ing");
			JSONArray tempid_ed = json.getJSONArray("matchid_ed");
			JSONArray temptype_ing = json.getJSONArray("matchtype_ing");
			JSONArray temptype_ed = json.getJSONArray("matchtype_ed");
			for(int i = 0 ; i < temp_ing.length() ; i++){
				matchname_ing.add(temp_ing.getString(i));
			}
			for(int i = 0 ; i < temp_ed.length() ; i++){
				matchname_ed.add(temp_ed.getString(i));
			}
			for(int i = 0 ; i < tempid_ing.length() ; i++){
				matchID_ing.add(tempid_ing.getString(i));
				System.out.println(tempid_ing.getString(i));
			}
			for(int i = 0 ; i < tempid_ed.length() ; i++){
				matchID_ed.add(tempid_ed.getString(i));
				System.out.println(tempid_ed.getString(i));
			}
			for(int i = 0 ; i < temptype_ing.length(); i++){
				matchtype_ing.add(temptype_ing.getString(i));
			}
			for(int i = 0 ; i < temptype_ed.length(); i++){
				matchtype_ed.add(temptype_ed.getString(i));
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
			bundle.putStringArrayList("matchID_ing",matchID_ing);
			bundle.putStringArrayList("matchID_ed", matchID_ed);
			bundle.putStringArrayList("matchname_ing", matchname_ing);
			bundle.putStringArrayList("matchname_ed", matchname_ed);
			bundle.putStringArrayList("matchtype_ing",matchtype_ing);
			bundle.putStringArrayList("matchtype_ed",matchtype_ed);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}
//	class thread_draw extends Thread{
//		public void run(){
//            runOnUiThread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try{
//						Thread.sleep(500);
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//					draw_match(is_end);
//				}
//			});
//		}
//	}


}
