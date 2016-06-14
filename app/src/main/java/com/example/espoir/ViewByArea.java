package com.example.espoir;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;




public class ViewByArea extends Activity implements Constants
{
	int flag = 0;
	
	// Progress Dialog
	private ProgressDialog pDialog;
	String address;
		
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	
	ArrayList<HashMap<String, String>> customerList = 
			new ArrayList<HashMap<String,String>>();
	
	// url 
	private static String url_address = "http://www.shellprompt.in/Android_connect/view_by_area.php";
		
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NAME = "name";
	private static final String TAG_TABLENO = "tableid";
	
	// products JSONArray
	private JSONArray products = null;
	
	private TableLayout tableViewLayout;
	
	private TableRow setLayoutParam(TableRow r)
	{
		TableRow.LayoutParams lp = new TableRow.LayoutParams(
				TableRow.LayoutParams.WRAP_CONTENT);
		r.setLayoutParams(lp);
		return r;
	}
	
	private void populateTable()
	{
		tableViewLayout = (TableLayout)findViewById(R.id.viewtable);
		int index = 1;
		for (HashMap<String, String> customer : customerList) 
		{
			TableRow r = new TableRow(this);
			r = setLayoutParam(r);
			
			
			TextView tv1 = new TextView(this);
			//tv1.setBackgroundResource(R.drawable.layoutstyle);
			tv1.setGravity(Gravity.CENTER);
			tv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT));
			tv1.setWidth(165);
			tv1.setPadding(5, 0, 0, 0);
			tv1.setText(customer.get(COMPID).toString());
			tv1.setTextSize(20);
			r.addView(tv1);
			
			TextView tv2 = new TextView(this);
			//tv2.setBackgroundResource(R.drawable.layoutstyle);
			tv2.setGravity(Gravity.CENTER);
			tv2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT));
			tv2.setPadding(5, 0, 0, 0);
			tv2.setText(customer.get(FILEDDATE).toString());
			tv2.setTextSize(20);
			r.addView(tv2);
			tableViewLayout.addView(r);
			
			TextView tv3 = new TextView(this);
			//tv1.setBackgroundResource(R.drawable.layoutstyle);
			tv3.setGravity(Gravity.CENTER);
			tv3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT));
			tv3.setWidth(165);
			tv3.setPadding(5, 0, 0, 0);
			tv3.setText(customer.get(TITLE).toString());
			tv3.setTextSize(20);
			r.addView(tv3);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_complaints_by_area);
		address = getIntent().getExtras().getString("jsonObject");
		
	
		// Loading products in Background Thread
		new LoadAllProducts().execute();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	class LoadAllProducts extends AsyncTask<String, String, String> 
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ViewByArea.this);
			pDialog.setMessage("Loading Details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) 
		{
			// Building Parameters
			List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
			paramValues.add(new BasicNameValuePair("address", address));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_address, "POST", paramValues);
						
			// Check your log cat for JSON reponse
			Log.d("All Complaints: ", json.toString());
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray("products");

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						String compid = c.getString(COMPID);
						String title = c.getString(TITLE);
						String filedDate = c.getString(FILEDDATE);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(COMPID, compid);
						map.put(TITLE, title);
						map.put(FILEDDATE, filedDate);

						// adding HashList to ArrayList
						customerList.add(map);

					}
				} else {
					// no products found
					// Launch Add New product Activity
					flag = 1;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) 
		{
			Intent i;
			if(flag==1)
			{
				pDialog.dismiss();
				Toast.makeText(getApplicationContext(), "No complaints, Lodge now !", Toast.LENGTH_LONG).show();
				i = new Intent(getApplicationContext(), ThirdActivity.class);
				startActivity(i);
				
			}
			else
			{
				// dismiss the dialog after getting all products
				pDialog.dismiss();
				// updating UI from Background Thread
				populateTable();
			}	
		}
	}
}
