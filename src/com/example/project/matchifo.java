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

import com.example.project.match.thread;
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

public class matchifo extends Activity{
	String user="";
    String name="";
    private String matchID ="";
	Handler handler1;
	private String matchname = "";
	private String matchifo = "";
	private String matchjoin_endtime = "";
	private String matchposi = "";
	private String matchhost = "";
	private String matchtime = "";
	private String joinway;
	private String type;
    @Override 
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.matchifo);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		name = data.getString("name");
		user = data.getString("user");
		matchID = data.getString("matchID");
		final Button listen_back = (Button)findViewById(R.id.back);
	    listen_back.setOnClickListener(new listen_back());
	    Thread loginThread = new Thread(new thread());
		loginThread.start();
		handler1 = new Handler(){

			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
					matchname = bundle.getString("matchname");
					matchifo = bundle.getString("matchifo");
					matchjoin_endtime = bundle.getString("matchjoin_endtime");
					matchposi = bundle.getString("matchposi");
				    matchhost = bundle.getString("matchhost");
				    matchtime = bundle.getString("matchtime");
				    joinway = bundle.getString("joinway");
				    type = bundle.getString("type");
				    draw_text();
			}
		};
    }
    public void draw_text(){
    	final View B = (View)findViewById(R.id.matchpic);
    	if(type.equals("篮球")){
			B.setBackgroundResource(R.drawable.matchba);
		}
		else if(type.equals("足球")){
			B.setBackgroundResource(R.drawable.matchfo);
		}
		else if(type.equals("网球")){
			B.setBackgroundResource(R.drawable.matchten);
		}
		else if(type.equals("田径")){
			B.setBackgroundResource(R.drawable.matchrun);
		}
		else if(type.equals("羽毛球")){
			B.setBackgroundResource(R.drawable.matchbad);
		}
    	final TextView A = (TextView)findViewById(R.id.matchhost);
    	A.setText("主办方:  "+matchhost);
    	final TextView C = (TextView)findViewById(R.id.matchjoin_endtime);
    	C.setText("报名截止时间:  "+matchjoin_endtime);
    	final TextView D = (TextView)findViewById(R.id.matchtime);
    	D.setText("比赛时间:  "+matchtime);
    	final TextView E = (TextView)findViewById(R.id.matchifo);
    	E.setText("详细信息: "+matchifo);
    	final TextView F = (TextView)findViewById(R.id.matchposi);
    	F.setText("比赛地点:  "+matchposi);
    	final TextView G = (TextView)findViewById(R.id.joinway);
    	G.setText("报名方式:  "+joinway);
    }
    class listen_back implements OnClickListener{
		@Override
    	public void onClick(View v){
    		Bundle data = new Bundle();
    		Intent intent = new Intent(matchifo.this,match.class);//test其实是login
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
		String url = "http://192.168.191.1:8080/web/matchifoServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
			JSONObject json1 = new JSONObject();
			Object matchid = matchID;
			json1.put("matchID", matchid);
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
		    matchname = json.getString("matchname");
		    matchifo = json.getString("matchifo");
		    matchjoin_endtime = json.getString("join_endtime");
		    matchposi = json.getString("matchposi");
		    matchhost = json.getString("matchhost");
		    matchtime = json.getString("matchtime");
		    joinway = json.getString("joinway");
		    type = json.getString("type");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	class thread implements Runnable{
		public void run(){
			sendjson();
			Message message=Message.obtain();
			Bundle bundle = new Bundle();
            bundle.putString("matchname", matchname);
            bundle.putString("matchifo", matchifo);
            bundle.putString("matchjoin_endtime", matchjoin_endtime);
            bundle.putString("matchposi", matchposi);
            bundle.putString("matchhost", matchhost);
            bundle.putString("matchtime", matchtime);
            bundle.putString("joinway", joinway);
            bundle.putString("type", type);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}
    


}