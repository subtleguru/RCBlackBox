package com.subtleguru.bbfr;

import com.subtleguru.bbfr.telemetry.MetricUpdater;
import com.subtleguru.bbfr.telemetry.RecordingTape;

import android.app.Application;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class RCBlackBoxApplication extends Application implements
		SensorEventListener, LocationListener {

	public static final String APP_NAME = "RCBlackBox";

	SensorManager sm;
	LocationManager lm;
	boolean recording = false;
	RecordingTape accelData, orientData, positionData;
	MetricUpdater mu;

	float maxSpeed;

	Camera camera;
	private boolean stop = true;
	int pictureSeq = 1;

	@Override
	public void onCreate() {
		Log.d(APP_NAME, "APPLICATION onCreate");
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		reset();
	}

	public boolean isRecording() {
		return recording;
	}

	public void startRecording(MetricUpdater mu) {
		recording = true;
		this.mu = mu;
		sm.registerListener(this, sm
				.getDefaultSensor(SensorManager.SENSOR_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL, new Handler());
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL, new Handler());
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}

	public void stopRecording() {
		recording = false;
		sm.unregisterListener(this);
		lm.removeUpdates(this);
	}

	public void reset() {
		accelData = new RecordingTape(10000);
		orientData = new RecordingTape(10000);
		positionData = new RecordingTape(10000);
		maxSpeed = 0;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent e) {
		if (e.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			accelData.append(e.values[0], e.values[2], e.values[1]);
			mu.updateYaw(e.values[0], accelData.getMinX(), accelData.getMaxX());
			mu.updateRoll(e.values[2], accelData.getMinY(), accelData.getMaxY());
			mu.updatePitch(e.values[1], accelData.getMinZ(), accelData.getMaxZ());
		}

		if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			orientData.append(e.values[0], e.values[1], e.values[2]);
			mu.updateX(e.values[0], orientData.getMinX(), orientData.getMaxX());
			mu.updateY(e.values[1], orientData.getMinY(), orientData.getMaxY());
			mu.updateZ(e.values[2], orientData.getMinZ(), orientData.getMaxZ());
		}
	}

	public void onLocationChanged(Location location) {
		positionData.append(location.getLatitude(), location.getLongitude(),
				location.getAltitude());
		mu.updateLong(location.getLongitude(), positionData.getMinY(),
				positionData.getMaxY());
		mu.updateLat(location.getLatitude(), positionData.getMinX(),
				positionData.getMaxX());
		mu.updateAlt(location.getAltitude(), positionData.getMinZ(),
				positionData.getMaxZ());
		if (location.getSpeed() > maxSpeed) {
			maxSpeed = location.getSpeed();
		}
		mu.updateSpeed(location.getSpeed(), maxSpeed, location.getBearing());
		mu.updateGPSAccuracy(location.getAccuracy());
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public int nextSequence() {
		return pictureSeq++;
	}

	public int currentSequence() {
		return pictureSeq;
	}

	@Override
	public void onTerminate() {
		Log.d(APP_NAME, "APPLICATION onTerminate");
		super.onTerminate();
		if (camera != null)
			camera.release();
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}
