package com.example.espoir;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manoj Kumar
 */

public class MainActivity extends Activity {

    boolean flag;
    int counter=0;
    String complaint_id;

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    TextView text, title;
    Button metoo, submit;

    private static String url_lodge = "http://www.shellprompt.in/Android_connect/update_product.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "complaint_id";
    private static final String TAG_METOO = "me_too";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metoo = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.textView);
        submit = (Button) findViewById(R.id.button2);

        Intent i = getIntent();
        complaint_id = i.getStringExtra(TAG_PID);


        metoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                text.setText("0"+counter);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Update().execute();
            }
        });

    }

    class Update extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Lodging..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            String me_too = text.getText().toString();


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PID, complaint_id));
            params.add(new BasicNameValuePair(TAG_METOO, me_too));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_lodge,
                    "POST", params);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag


            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }
}
