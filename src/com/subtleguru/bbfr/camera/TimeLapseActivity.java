package com.subtleguru.bbfr.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.subtleguru.bbfr.R;
import com.subtleguru.bbfr.RCBlackBoxApplication;

public class TimeLapseActivity extends Activity {
	
		Button startButton;
		Button stopButton;
		Photographer photographer;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera); 
        photographer = new Photographer(getApplicationContext(), 
        		(RCBlackBoxApplication)getApplication());
        
        startButton = (Button)findViewById(R.id.Button01);
        stopButton = (Button)findViewById(R.id.Button02);
        startButton.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						 startButton.setEnabled(false);
						 stopButton.setEnabled(true);
						 new RecordTask().execute();				
					}
        });
        
        stopButton.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						 stopButton.setEnabled(false);
						 startButton.setEnabled(true);
						 ((RCBlackBoxApplication)getApplication()).setStop(true);  			
					}
        });   
    }

    
    private class RecordTask extends AsyncTask<String, Void, Void> {
    	protected void onPreExecute() {
    		super.onPreExecute();
    		Log.d(RCBlackBoxApplication.APP_NAME, "Pre-execute - Taking pics");
    		Toast toast = Toast.makeText(getApplicationContext(), "Taking pictures ...",
	    			Toast.LENGTH_LONG);
				toast.show();
    	}

			@Override
			protected Void doInBackground(String... arg0) {			
				RCBlackBoxApplication app = (RCBlackBoxApplication)getApplication();
				app.setStop(false);
				
	    	do {
	    		publishProgress();
	    		Log.d(RCBlackBoxApplication.APP_NAME, "calling take pic");
	    		photographer.takePhoto();
	    		
	    		try {
	    			Log.d(RCBlackBoxApplication.APP_NAME, "sleeping 4000");
	    			Thread.sleep(4000);
	    		} catch (InterruptedException e) {}
	    	} while (!app.isStop());
  
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				Log.d(RCBlackBoxApplication.APP_NAME, "post exec - stopping");
			 	Toast toast = Toast.makeText(getApplicationContext(), "Stopping pictures ...",
	    			Toast.LENGTH_LONG);
				toast.show();
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
				RCBlackBoxApplication app = (RCBlackBoxApplication)getApplication();
				Log.d(RCBlackBoxApplication.APP_NAME, "Pic #" + app.currentSequence());
//			  Toast toast = Toast.makeText(getApplicationContext(), "Pic #" + app.currentSequence(), 
//			  		Toast.LENGTH_LONG);
//			  toast.show();
			}		
    }


		@Override
		protected void onDestroy() {
			super.onDestroy();
			Log.d(RCBlackBoxApplication.APP_NAME, "on destroy");
		}


		@Override
		protected void onPause() {
			super.onPause();
			Log.d(RCBlackBoxApplication.APP_NAME, "on pause - calling release");
			((RCBlackBoxApplication)getApplication()).getCamera().release();
		}


		@Override
		protected void onRestart() {
			super.onRestart();
			Log.d(RCBlackBoxApplication.APP_NAME, "restart");
		}


		@Override
		protected void onRestoreInstanceState(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onRestoreInstanceState(savedInstanceState);
			Log.d(RCBlackBoxApplication.APP_NAME, "restore instance state");
		}


		@Override
		protected void onResume() {
			super.onResume();
			Log.d(RCBlackBoxApplication.APP_NAME, "resume - open");
			((RCBlackBoxApplication)getApplication()).setCamera(Camera.open());
		}

		@Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
		}
    
    

}