package com.example.espoir;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class SeventhActivity extends Activity
{
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText area2;
	
	private static String url_address = "http://www.shellprompt.in/Android_connect/view_by_area.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
    Button btnonsubmit;
    

	
	    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seventh);
        

 		area2= (EditText) findViewById(R.id.area2);
		btnonsubmit = (Button)findViewById(R.id.submit1);
		
	    btnonsubmit.setOnClickListener(new OnClickListener() {

 			@Override
 			public void onClick(View view) 
 			{
 				Log.d("SeventhActivity", "Inside OnClickListener");
 				new Load().execute();
 			}
 		});
	    }
        
 		
	class Load extends AsyncTask<String, String, String> {

    	/**
    	 * Before starting background thread Show Progress Dialog
    	 * */
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog = new ProgressDialog(SeventhActivity.this);
    		pDialog.setMessage("Loading..");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(true);
    		pDialog.show();
    	}

    	
    	protected String doInBackground(String... args) {
    		String address = area2.getText().toString();

    		// Building Parameters
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
    		params.add(new BasicNameValuePair("address", address));

    		// getting JSON Object
    		// Note that create product url accepts POST method
    		JSONObject json = jsonParser.makeHttpRequest(url_address,
    				"POST", params);
    		
    		// check log cat fro response
    		Log.d("Create Response", json.toString());

    		// check for success tag
    		try {
    			int success = json.getInt(TAG_SUCCESS);

    			if (success == 1) 
    			{
    				
    				Intent i3 = new Intent(getApplicationContext(), ViewByArea.class);
    				i3.putExtra("jsonObject",address);
    				
    				startActivity(i3);
    				
    				// closing this screen
    				finish();
    			} else {
    				// failed
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

}