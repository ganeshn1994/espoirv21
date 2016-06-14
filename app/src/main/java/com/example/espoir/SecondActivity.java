package com.example.espoir;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.espoir.FirstActivity;
import com.example.espoir.SecondActivity;
import com.example.espoir.R;

import android.support.v7.app.ActionBarActivity;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class SecondActivity extends ActionBarActivity {
	
	Button b3,b4;
	Intent i4;
	
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText fullName1;
	EditText userName1;
	EditText phNum1;
	EditText email1;
	EditText password3;
	EditText password5;


	// url to reg
	private static String url_reg = "http://www.shellprompt.in/Android_connect/reg.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        
        b3 = (Button)findViewById(R.id.submit);
        b4 = (Button)findViewById(R.id.logIn1);
        

        i4 = new Intent(this, FirstActivity.class);
        
        // Edit Text
 		fullName1 = (EditText) findViewById(R.id.fullName1);
 		email1 = (EditText) findViewById(R.id.email1);
 		phNum1 = (EditText) findViewById(R.id.phNum1);
 		userName1 = (EditText) findViewById(R.id.userName1);
 		password3 = (EditText) findViewById(R.id.password3);
 		password5 = (EditText) findViewById(R.id.password5);
       
        
        b4.setOnClickListener(new OnClickListener() {
			
    			@Override
    			public void onClick(View v)
    			{
    				startActivity(i4);
    				
    			}
            });
        
     		// button click event
     		b3.setOnClickListener(new View.OnClickListener() {

     			@Override
     			public void onClick(View view) {
     				// creating reg details in background thread
     				new Register().execute();
     			}
     		});
            
            
        }
       
    /**
	 * Background Async Task to Register
	 * */
	class Register extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SecondActivity.this);
			pDialog.setMessage("Registering..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating
		 * */
		protected String doInBackground(String... args) {
			String full_name = fullName1.getText().toString();
			String email_id = email1.getText().toString();
			String phone_number = phNum1.getText().toString();
			String user_name = userName1.getText().toString();
			String password = password3.getText().toString();
			String confirm_password = password5.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("full_name", full_name));
			params.add(new BasicNameValuePair("email_id", email_id));
			params.add(new BasicNameValuePair("phone_number",phone_number));
			params.add(new BasicNameValuePair("user_name",user_name));
			params.add(new BasicNameValuePair("password",password));
			params.add(new BasicNameValuePair("confirm_password",confirm_password));

			// getting JSON Object
			// Note that create reg url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_reg,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully reg
					Intent i3 = new Intent(getApplicationContext(), FirstActivity.class);
					startActivity(i3);
					
					// closing this screen
					finish();	
				} else {
					// failed to reg
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
   
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
