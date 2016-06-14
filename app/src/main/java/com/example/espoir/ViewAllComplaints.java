package com.example.espoir;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class ViewAllComplaints extends Activity implements Constants
{
	int flag = 0;
	String titlename = null;

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> customerList =
			new ArrayList<HashMap<String,String>>();

	// url to get all products list
	private static String url_all_products = "http://www.shellprompt.in/Android_connect/View_all_complaints.php";

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

			TextView tv = new TextView(this);
			//tv.setBackgroundResource(R.drawable.layoutstyle);
			tv.setGravity(Gravity.CENTER);
			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT));
			tv.setPadding(5, 0, 0, 0);
			tv.setText(customer.get(COMPID).toString());
			tv.setTextSize(20);
			r.addView(tv);
//			tv.setClickable(true);
//
//			tv.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Intent i = new Intent(ViewAllComplaints.this,ThirdActivity.class);
//					startActivity(i);
//				}
//			});

			TextView tv1 = new TextView(this);
			//tv1.setBackgroundResource(R.drawable.layoutstyle);
			tv1.setGravity(Gravity.CENTER);
			tv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT));
			tv1.setWidth(165);
			tv1.setPadding(5, 0, 0, 0);
			tv1.setText(customer.get(TITLE).toString());
			tv.setClickable(true);
			tv1.setTextSize(20);
			r.addView(tv1);
			tv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(ViewAllComplaints.this, MainActivity.class);
					startActivity(i);
				}
			});


			TextView tv2 = new TextView(this);
			//tv2.setBackgroundResource(R.drawable.layoutstyle);
			tv2.setGravity(Gravity.CENTER);
			tv2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT));
			tv2.setPadding(5, 0, 0, 0);
			tv2.setText(customer.get(FILEDDATE).toString());
			tv2.setTextSize(20);
			r.addView(tv2);
//			tv.setClickable(true);
//
//			tv.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Intent i = new Intent(ViewAllComplaints.this, ThirdActivity.class);
//					startActivity(i);
//				}
//			});

			TextView tv3=new TextView(this);
			tv3.setGravity(Gravity.CENTER);
			tv3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT));
			tv3.setText(customer.get(METOO).toString());
			tv3.setTextSize(20);
			r.addView(tv3);
			tv.setClickable(true);
//			tv.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Intent i = new Intent(ViewAllComplaints.this, ThirdActivity.class);
//					startActivity(i);
//				}
//			});

			tableViewLayout.addView(r);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_all_complaints);


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
			pDialog = new ProgressDialog(ViewAllComplaints.this);
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
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "POST", paramValues);

			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(COMPLAINTS);

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						String compid = c.getString(COMPID);
						String title = c.getString(TITLE);
						String filedDate = c.getString(FILEDDATE);
						String metoo=c.getString(METOO);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(COMPID, compid);
						map.put(TITLE, title);
						map.put(FILEDDATE, filedDate);
						map.put(METOO,metoo);

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
				Toast.makeText(getApplicationContext(), "No Booking, Book now !", Toast.LENGTH_LONG).show();
				i = new Intent(getApplicationContext(), FourthActivity.class);
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
