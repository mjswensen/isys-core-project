package edu.byu.isys403.data.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	ViewFlipper vf = null;
	HttpClient client = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vf = (ViewFlipper)findViewById(R.id.viewFlipper);
        client = new DefaultHttpClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void loginSubmit(View view) {
    	try {
	    	HttpPost request = new HttpPost("http://10.0.2.2:8080/MyStuffSprint/edu.byu.isys413.data.actions.Login.action");
	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    	EditText email = (EditText) findViewById(R.id.editTextEmail);
	    	EditText password = (EditText) findViewById(R.id.editTextPassword);
	    	nameValuePairs.add(new BasicNameValuePair("format", "json"));
	    	nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
	        nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));
	        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        HttpResponse response = client.execute(request);
	        StatusLine statusLine = response.getStatusLine();
	        int statusCode = statusLine.getStatusCode();
	        if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				JSONObject json = new JSONObject(builder.toString());
				if(json.getString("status").equals("success")) {
					vf.showNext();
				} else {
					TextView loginStatus = (TextView) findViewById(R.id.textViewLoginStatus);
					loginStatus.setText("Bummer. The email/password you provided didn't work.");
				}
	        } else {
	        	throw new Exception("Got a response other than 200.");
	        }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
