package com.example.espoir;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.espoir.camera.CameraUtility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class FourthActivity extends Activity implements CameraConstants
{
	CameraUtility cameraObject;
	boolean flag;
	int counter;
	
	// LogCat tag
	private static final String TAG = FourthActivity.class.getSimpleName();
	
 
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static Uri fileUri; // file url to store image/video

 
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	Spinner Department1;
	EditText title3;
	EditText addressView1;
	EditText locView2;
	EditText locView6;
	EditText locView3;
	EditText desc1;


	private static String url_lodge = "http://www.shellprompt.in/Android_connect/lodge.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
    Button btnShowAddress,btnonsubmit;
    TextView addressView;
    TextView locView;
    TextView locView5;
    Spinner spnr;
    

	public ImageView imgPreview;
	public Button btnCapturePicture;

    AppLocationService appLocationService;
    
    public CamUtil camUtil = new CamUtil(this);
    String[] Department = {
  	      "BWSSB",
  	      "KPTCL",
  	      "BSNL",
  	      "BBMP"
  	  };

	
//----------------------------------------------------------------------------------------------------------------
    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
 
        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }
 
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
 
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
 
    
 
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                
            	// successfully captured the image
                // launching upload activity
            	cameraObject.launchUploadActivity(true,fileUri);
            	
            	
            } else if (resultCode == RESULT_CANCELED) {
                
            	// user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        
        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                
            	// video successfully recorded
                // launching upload activity
            	cameraObject.launchUploadActivity(false,fileUri);
            
            } else if (resultCode == RESULT_CANCELED) {
                
            	// user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }    
    
//----------------------------------------------------------------------------------------------------------------    
    
    
    /*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) 
		{
			if (resultCode == RESULT_OK) 
			{
				// successfully captured the image
				// display it in image view
				previewCapturedImage();
			} else if (resultCode == RESULT_CANCELED) 
			{
				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else 
			{
				// failed to capture image
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}*/

	/*
	 * Display image from a path to ImageView
	 */
	/*private void previewCapturedImage() {
		try {

			imgPreview.setVisibility(View.VISIBLE);

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			imgPreview.setImageBitmap(bitmap);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}*/
    

	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth);
        
        cameraObject = new CameraUtility(this);
  
 		Department1 = (Spinner) findViewById(R.id.spinner);
 		title3 = (EditText) findViewById(R.id.title2);
 		addressView1 = (EditText) findViewById(R.id.addressView);
 		locView2 = (EditText) findViewById(R.id.locView);
 		locView6 = (EditText) findViewById(R.id.locView5);
 		locView3 = (EditText) findViewById(R.id.locView1);
 		desc1 = (EditText) findViewById(R.id.description);

        addressView = (TextView) findViewById(R.id.addressView);
        locView = (TextView) findViewById(R.id.locView);
        locView5= (TextView) findViewById(R.id.locView5);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
		btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);

		btnCapturePicture.setOnClickListener(new View.OnClickListener() {
			 
	            @Override
	            public void onClick(View v) {
	                // capture picture
	                captureImage();
	            }
	        });
		
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
	    
		
		
		
		
	    btnonsubmit.setOnClickListener(new OnClickListener() 
	    {

 			@Override
 			public void onClick(View view) 
 			{
 				
 				new Lodge().execute();
 			}
 		});
        
 		
		/*
		 * Capture image button click event
		 */
		btnCapturePicture.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// capture picture
				camUtil.captureImage();
			}
		});

		// Checking camera availability
		if (!camUtil.isDeviceSupportCamera(this)) 
		{
			Toast.makeText(getApplicationContext(),
					"Sorry! Your device doesn't support camera",
					Toast.LENGTH_LONG).show();
			// will close the app if the device does't have camera
			finish();
		}

        
        appLocationService = new AppLocationService(
                FourthActivity.this);

        btnShowAddress = (Button) findViewById(R.id.getAddress);
        btnShowAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location location = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);

                //you can hard-code the lat & long if you have issues with getting it
                //remove the below if-condition and use the following couple of lines
                //double latitude = 37.422005;
                //double longitude = -122.084095

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String lat = "Lat:"+latitude;
                    String Long = "Long:"+longitude;
                    locView.setText(lat);
                    locView5.setText(Long);
                    LocationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());
                } else {
                    showSettingsAlert();
                }

            }
        });



    }
    /*public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    
    private static File getOutputMediaFile(int type) {
    	 
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
 
        return mediaFile;
    }*/
    
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
        fileUri = cameraObject.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
 
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
 
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    class Lodge extends AsyncTask<String, String, String> {

    	/**
    	 * Before starting background thread Show Progress Dialog
    	 * */
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog = new ProgressDialog(FourthActivity.this);
    		pDialog.setMessage("Lodging..");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(true);
    		pDialog.show();
    	}

    	
    	protected String doInBackground(String... args) {
    		String dept_name = Department1.getSelectedItem().toString();
    		String title = title3.getText().toString();
    		String address = addressView1.getText().toString();
    		String latitude = locView2.getText().toString();
    		String longitude = locView6.getText().toString();
    		String landmark = locView3.getText().toString();
    		String description = desc1.getText().toString();

    		// Building Parameters
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
    		params.add(new BasicNameValuePair("dept_name", dept_name));
    		params.add(new BasicNameValuePair("title", title));
    		params.add(new BasicNameValuePair("address", address));
    		params.add(new BasicNameValuePair("latitude", latitude));
    		params.add(new BasicNameValuePair("longitude", longitude));
    		params.add(new BasicNameValuePair("landmark", landmark));
    		params.add(new BasicNameValuePair("description", description));

    		// getting JSON Object
    		// Note that create product url accepts POST method
    		JSONObject json = jsonParser.makeHttpRequest(url_lodge,
    				"POST", params);
    		
    		// check log cat fro response
    		Log.d("Create Response", json.toString());

    		// check for success tag
    		try {
    			int success = json.getInt(TAG_SUCCESS);

    			if (success == 1) 
    			{
    				
    				flag = true;
    				/*Intent i3 = new Intent(getApplicationContext(), ThirdActivity.class);
    				
    				startActivity(i3);
    				Toast.makeText(getApplicationContext(), "Complaint has been lodged.", Toast.LENGTH_SHORT).show();
    				
    				// closing this screen
    				finish();*/
    			} else {
    				flag = false;
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
    		pDialog.dismiss();
    		if(flag==true)
    		{
    			Intent i3 = new Intent(getApplicationContext(), ThirdActivity.class);
				
				startActivity(i3);
				Toast.makeText(getApplicationContext(), "Complaint has been lodged.", Toast.LENGTH_SHORT).show();
    		}
    		else
    		{
    			String msg = "Submittion Failed ! Check your internet connection!";
    			Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    			// dismiss the dialog once done
    		}	
    		
    	}

    }



    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                FourthActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        FourthActivity.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    public class GeocoderHandler extends Handler 
    {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String address=new String();
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            for(String sl: locationAddress.split("Address:\n"))
            {
            	if(sl.contains("Latitude"))
            	{
            		
            	}
            	else
            	{
            		address = address+sl;
            		if(address.contains("null"))
            		{
            			address=address.replace("null\n", "");
            		}
            	}
            }
            addressView.setText(address);
        }
    }
}