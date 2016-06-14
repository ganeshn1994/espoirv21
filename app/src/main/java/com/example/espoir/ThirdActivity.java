package com.example.espoir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class ThirdActivity extends ActionBarActivity {
	
//	Button b5,b6,b7;
	FloatingActionButton fab,fab1;


	Intent i5,i6,fa,fa1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third);
		
//		 	b5 = (Button)findViewById(R.id.lodge);
//	        b6 = (Button)findViewById(R.id.view);
		fab=(FloatingActionButton)findViewById(R.id.fab);
		fab1=(FloatingActionButton)findViewById(R.id.fab1);


//		i5 = new Intent(this, FourthActivity.class);
//	        i6 = new Intent(this, FifthtActivity.class);
		fa = new Intent(this,FourthActivity.class);
		fa1 = new Intent(this,FifthtActivity.class);



//		b5.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v)
//				{
//					startActivity(i5);
//
//				}
//	        });
//
//
//	        b6.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v)
//				{
//					startActivity(i6);
//
//				}
//	        });

		fab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(fa);
			}
		});

		fab1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(fa1);
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
