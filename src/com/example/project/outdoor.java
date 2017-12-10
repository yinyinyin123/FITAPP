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
import com.example.project.my.tableThread;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class outdoor extends Activity{
	private AutoCompleteTextView autoComtextView = null;
	private ArrayList<String> temp = new ArrayList<String>();
	private String[] count1;
	private int number;
	private String user="";
	private String name="";
	private ArrayList<String> id = new ArrayList<String>();
	private ImageButton button = null;
	Handler handler1;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.outdoor);
		Intent intent1 = getIntent();
		Bundle data1 = intent1.getExtras();
		if(data1 == null){
			
		}
		else{
			user = data1.getString("user");
			name = data1.getString("name");
			//System.out.println(user+"13213");
		}
		Thread loginThread = new Thread(new thread());
		loginThread.start(); 
		autoComtextView = (AutoCompleteTextView)findViewById(R.id.autoComplete);
		button = (ImageButton)findViewById(R.id.search);
		final Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new listen_back());
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,count1);
		//autoComtextView.setAdapter(adapter);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("ÄãÒªËÑË÷µÄÊÇ", autoComtextView.getText().toString());
				String t = autoComtextView.getText().toString();
				String stadium = "";
				for(int i = 0 ; i < count1.length ; i++){
					if(count1[i].equals(t)){
						stadium = id.get(i);
						break;
					}
				}
				Intent intent = new Intent(outdoor.this,infopage.class);
				Bundle data = new Bundle();
				data.putString("name",name);
				data.putString("user",user);
				data.putString("stadium", stadium);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});
		handler1 = new Handler(){
			@Override
			public void handleMessage(Message msg){
					Bundle bundle = msg.getData();
					temp = bundle.getStringArrayList("array");
					id = bundle.getStringArrayList("id");
					number = bundle.getInt("number");
					count1 = new String[number];
                    for(int i = 0 ; i < temp.size(); i++){
                    	count1[i] = temp.get(i);
                    	System.out.println(count1[i]);
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(outdoor.this, android.R.layout.simple_list_item_1,count1);
            		autoComtextView.setAdapter(adapter);      
			}
		};
	}
	class listen_back implements OnClickListener{
		@Override
		public void onClick(View V){
			Intent intent1 = getIntent();
			Bundle data1 = intent1.getExtras();
			System.out.print(data1);
			if(data1 == null){
				
			}
			else{
				user = data1.getString("user");
				name = data1.getString("name");
				//System.out.println(user+"13213");
			}
			Intent intent = new Intent(outdoor.this,MainActivity.class);
			Bundle data = new Bundle();
			data.putString("name",name);
			data.putString("user",user);
			intent.putExtras(data);
			startActivity(intent);
			finish();
		}
	}
	public void sendjson(){
		String url = "http://192.168.191.1:8080/web/fitServlet";
	    //String str= "";
		HttpPost post = new HttpPost(url);
		try{
//			JSONObject json1 = new JSONObject();
//			Object username = user;
//			json1.put("username", username);
//			StringEntity se = new StringEntity(json1.toString());
//			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//			post.setEntity(se);
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
		    number = json.getInt("number");
			for(int i = 0 ; i < number ; i++){
				System.out.println(json.getString(""+i+""));
				temp.add(json.getString(""+i+""));
				id.add(json.getString("id"+i+""));
			    System.out.println(json.getString(""+i+""));
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
			bundle.putStringArrayList("array",temp);
			bundle.putStringArrayList("id", id);
			bundle.putInt("number", number);
			message.setData(bundle);
			handler1.sendMessage(message);
		}
	}
}
