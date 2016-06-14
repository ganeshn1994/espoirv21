package com.example.espoir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class FirstActivity extends ActionBarActivity 
{
	
	private ProgressDialog pDialog;
    boolean successFlag;
	JSONParser jsonParser = new JSONParser();
	EditText username;
	EditText password2;
	Intent newIntent;
	public static String userid;
	


	// url to check
	private static String url_check = "http://www.shellprompt.in/Android_connect/check.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	Button b1,b2,b3;
	Intent i2,go;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        
        username = (EditText)findViewById(R.id.username1);
        password2 = (EditText)findViewById(R.id.password1);
        
        b1 = (Button)findViewById(R.id.logIn);
        b2 = (Button)findViewById(R.id.reg);
		b3 = (Button) findViewById(R.id.google);
        
        i2 = new Intent(this, SecondActivity.class);
		go=new Intent(this,Google.class);
       
        
        b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivity(i2);
				
			}
        });
        
        b1.setOnClickListener(new OnClickListener() {

     			@Override
     			public void onClick(View view) {
     				// creating login details in background thread
     				new Login().execute();
     			}
     		});

		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(go);
			}
		});
            
            
        }
       
    /**
	 * Background Async Task to Register
	 * */
	class Login extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FirstActivity.this);
			pDialog.setMessage("Authenticating....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating
		 * */
		protected String doInBackground(String... args) {
			String user_name = username.getText().toString();
			String password = password2.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_name",user_name));
			params.add(new BasicNameValuePair("password",password));

			// getting JSON Object
			// Note that create check url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_check,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) 
				{
					successFlag = true;
					//write the code for fetching userid and storing it in a gobal variable userid

					
				} else {
					successFlag = false;
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
			if(successFlag==false)
			{
				Toast.makeText(getApplicationContext(), "Login Failed ! TRY AGAIN", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
				newIntent = new Intent(getApplicationContext(), ThirdActivity.class);
				startActivity(newIntent);
			}	
				
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
