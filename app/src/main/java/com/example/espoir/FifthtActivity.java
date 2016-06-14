package com.example.espoir;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class FifthtActivity extends ActionBarActivity {
	int flag=0;
	
	
	Button b1,b2,b3,b4,b5;
	Intent i2,i3,i4,i5,i6;
	


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth);
        
        
        b1 = (Button)findViewById(R.id.id);
        b2 = (Button)findViewById(R.id.area);
        b3 = (Button)findViewById(R.id.dept);
        b5 = (Button)findViewById(R.id.dept);

        b4 = (Button)findViewById(R.id.complaints);

        i2 = new Intent(this, all_complaints.class);
        i3 = new Intent(this, SeventhActivity.class);
        i4 = new Intent(this, SixthActivity.class);
        i5 = new Intent(this, ViewAllComplaints.class);
        i6 = new Intent(this, ViewAllComplaints.class);



        b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// DO NOTHING
				startActivity(i2);
				
			}
        });
        
        b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// DO NOTHING
				startActivity(i3);

			}
        });

        b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// DO NOTHING
				startActivity(i5);
				
			}
        });
        
        
        b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// This is the place to do the transitions

			      {
						startActivity(i4);

			      }
			   
			}
		});

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
