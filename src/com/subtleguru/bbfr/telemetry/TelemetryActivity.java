package com.subtleguru.bbfr.telemetry;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.subtleguru.bbfr.R;
import com.subtleguru.bbfr.RCBlackBoxApplication;

public class TelemetryActivity extends Activity implements MetricUpdater {

	TextView yawMin;
	TextView yawMax;
	TextView yawCurrent;
	TextView pitchMin;
	TextView pitchMax;
	TextView pitchCurrent;
	TextView rollMin;
	TextView rollMax;
	TextView rollCurrent;

	TextView xMin;
	TextView xMax;
	TextView xCurrent;
	TextView zMin;
	TextView zMax;
	TextView zCurrent;
	TextView yMin;
	TextView yMax;
	TextView yCurrent;
	
	TextView latMin;
	TextView latMax;
	TextView latCurrent;
	TextView longMin;
	TextView longMax;
	TextView longCurrent;
	TextView altMin;
	TextView altMax;
	TextView altCurrent;

	TextView currentSpeed;
	TextView speedMax;
	TextView gpsAccuracy;
	TextView gpsBearing;
	
	Button reset;
	Button startStop;

	RCBlackBoxApplication app;

	NumberFormat nf = null; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telemetry);
		app = (RCBlackBoxApplication) getApplication();

		yawMin = (TextView) findViewById(R.id.YawMin);
		yawMax = (TextView) findViewById(R.id.YawMax);
		yawCurrent = (TextView) findViewById(R.id.YawCurrent);
		pitchMin = (TextView) findViewById(R.id.PitchMin);
		pitchMax = (TextView) findViewById(R.id.PitchMax);
		pitchCurrent = (TextView) findViewById(R.id.PitchCurrent);
		rollMin = (TextView) findViewById(R.id.RollMin);
		rollMax = (TextView) findViewById(R.id.RollMax);
		rollCurrent = (TextView) findViewById(R.id.RollCurrent);
		
		xMin = (TextView) findViewById(R.id.XMin);
		xMax = (TextView) findViewById(R.id.XMax);
		xCurrent = (TextView) findViewById(R.id.XCurrent);
		zMin = (TextView) findViewById(R.id.ZMin);
		zMax = (TextView) findViewById(R.id.ZMax);
		zCurrent = (TextView) findViewById(R.id.ZCurrent);
		yMin = (TextView) findViewById(R.id.YMin);
		yMax = (TextView) findViewById(R.id.YMax);
		yCurrent = (TextView) findViewById(R.id.YCurrent);
		

		latMin = (TextView) findViewById(R.id.LatMin);
		latMax = (TextView) findViewById(R.id.LatMax);
		latCurrent = (TextView) findViewById(R.id.LatCurrent);
		altMin = (TextView) findViewById(R.id.AltMin);
		altMax = (TextView) findViewById(R.id.AltMax);
		altCurrent = (TextView) findViewById(R.id.AltCurrent);
		longMin = (TextView) findViewById(R.id.LongMin);
		longMax = (TextView) findViewById(R.id.LongMax);
		longCurrent = (TextView) findViewById(R.id.LongCurrent);
		

		currentSpeed = (TextView) findViewById(R.id.SpeedCurrent);
		speedMax = (TextView) findViewById(R.id.SpeedMax); 
		gpsAccuracy = (TextView) findViewById(R.id.GPSAccuracy);
		gpsBearing = (TextView) findViewById(R.id.BearingCurrent);
	
		reset = (Button) findViewById(R.id.reset_button);
		startStop = (Button) findViewById(R.id.start_button);

		reset.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				app.reset();
				initialValues();
			}
		});
		
		startStop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleStartStop();
			}
		});

		nf = new DecimalFormat("##.#######");
	}
	
	
	private void initialValues() {
		String na = "n/a";
		yawCurrent.setText(na);
		yawMax.setText(na);
		yawMin.setText(na);
		rollCurrent.setText(na);
		rollMax.setText(na);
		rollMin.setText(na);
		pitchCurrent.setText(na);
		pitchMax.setText(na);
		pitchMin.setText(na);
		
		yCurrent.setText(na);
		yMax.setText(na);
		yMin.setText(na);
		xCurrent.setText(na);
		xMax.setText(na);
		xMin.setText(na);
		zCurrent.setText(na);
		zMax.setText(na);
		zMin.setText(na);	
		
		longCurrent.setText(na);
	  longMax.setText(na);
		longMin.setText(na);
		latCurrent.setText(na);
		latMax.setText(na);
		latMin.setText(na);
		altCurrent.setText(na);
		altMax.setText(na);
		altMin.setText(na);	
	}

	void toggleStartStop() {
		if (app.isRecording()) {
			app.stopRecording();
			startStop.setText("Start");
		} else {
			app.startRecording(this);
			startStop.setText("Stop");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.subtleguru.rcflightbox.MetricUpdater#updateYaw(double, double,
	 * double)
	 */
	public void updateYaw(double x, double min, double max) {
		yawCurrent.setText(nf.format(x));
		yawMax.setText(nf.format(max));
		yawMin.setText(nf.format(min));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.subtleguru.rcflightbox.MetricUpdater#updateRoll(double, double,
	 * double)
	 */
	public void updateRoll(double x, double min, double max) {
		rollCurrent.setText(nf.format(x));
		rollMax.setText(nf.format(max));
		rollMin.setText(nf.format(min));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.subtleguru.rcflightbox.MetricUpdater#updatePitch(double, double,
	 * double)
	 */
	public void updatePitch(double x, double min, double max) {
		pitchCurrent.setText(nf.format(x));
		pitchMax.setText(nf.format(max));
		pitchMin.setText(nf.format(min));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.subtleguru.rcflightbox.MetricUpdater#updateX(double, double,
	 * double)
	 */
	public void updateX(double x, double min, double max) {
		xCurrent.setText(nf.format(x));
		xMax.setText(nf.format(max));
		xMin.setText(nf.format(min));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.subtleguru.rcflightbox.MetricUpdater#update(double, double,
	 * double)
	 */
	public void updateY(double x, double min, double max) {
		yCurrent.setText(nf.format(x));
		yMax.setText(nf.format(max));
		yMin.setText(nf.format(min));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.subtleguru.rcflightbox.MetricUpdater#updateZ(double, double,
	 * double)
	 */
	public void updateZ(double x, double min, double max) {
		zCurrent.setText(nf.format(x));
		zMax.setText(nf.format(max));
		zMin.setText(nf.format(min));
	}


	public void updateLong(double longitude, double min, double max) {
		longCurrent.setText(nf.format(longitude));
		longMax.setText(nf.format(max));
		longMin.setText(nf.format(min));
	}


	public void updateAlt(double alt, double min, double max) {
		altCurrent.setText(nf.format(alt));
		altMax.setText(nf.format(max));
		altMin.setText(nf.format(min));		
	}

	public void updateLat(double lat, double min, double max) {
		latCurrent.setText(nf.format(lat));
		latMax.setText(nf.format(max));
		latMin.setText(nf.format(min));
	}
	
	public void updateSpeed(float speed, float maxSpeed, float bearing) {
		 currentSpeed.setText(nf.format(speed));
		 gpsBearing.setText(""+bearing);
		 speedMax.setText(""+maxSpeed);
	}
	
	
	public void updateGPSAccuracy(float accuracy) {
		gpsAccuracy.setText(""+accuracy);
	}
	
}