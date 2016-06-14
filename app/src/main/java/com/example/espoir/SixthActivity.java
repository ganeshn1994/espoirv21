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
import android.widget.Spinner;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class SixthActivity extends Activity
{
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	Spinner Department1;

	private static String url_dept = "http://www.shellprompt.in/Android_connect/view_by_department.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
    Button btnonsubmit;
    Spinner spnr;
    
    String[] Department = {
  	      "BWSSB",
  	      "KPTCL",
  	      "BSNL",
  	      "BBMP"
  	  };

	
	    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixth);
  
 		Department1 = (Spinner) findViewById(R.id.spinner);
        
		
		btnonsubmit = (Button)findViewById(R.id.submit1);
		
		spnr = (Spinner)findViewById(R.id.spinner);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	        this, android.R.layout.simple_spinner_item, Department);
	    spnr.setAdapter(adapter);
	    spnr.setOnItemSelectedListener(
	              new AdapterView.OnItemSelectedListener() {
	                  @Override
	                  public void onItemSelected(AdapterView<?> arg0, View arg1,
	                          int arg2, long arg3) {
	                    int position = spnr.getSelectedItemPosition();
	                    Toast.makeText(getApplicationContext(),"You have selected "+Department[+position],Toast.LENGTH_LONG).show();
	                      // TODO Auto-generated method stub
	                  }
	                  @Override
	                  public void onNothingSelected(AdapterView<?> arg0) {
	                      // TODO Auto-generated method stub
	                  }
	              }
	          );
	    
		
		
		
		
	    btnonsubmit.setOnClickListener(new OnClickListener() {

 			@Override
 			public void onClick(View view) 
 			{
 				Log.d("SixthActivity", "Inside OnClickListener");
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
    		pDialog = new ProgressDialog(SixthActivity.this);
    		pDialog.setMessage("Loading..");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(true);
    		pDialog.show();
    	}

    	
    	protected String doInBackground(String... args) {
    		String dept_name = Department1.getSelectedItem().toString();

    		// Building Parameters
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
    		params.add(new BasicNameValuePair("dept_name", dept_name));

    		// getting JSON Object
    		// Note that create product url accepts POST method
    		JSONObject json = jsonParser.makeHttpRequest(url_dept,
    				"POST", params);
    		
    		// check log cat fro response
    		Log.d("Create Response", json.toString());

    		// check for success tag
    		try {
    			int success = json.getInt(TAG_SUCCESS);

    			if (success == 1) 
    			{
    				
    				Intent i3 = new Intent(getApplicationContext(), ViewByDepartment.class);
    				i3.putExtra("jsonObject",dept_name);
    				
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