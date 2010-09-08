package com.subtleguru.bbfr.telemetry;

public interface MetricUpdater {

	public abstract void updateYaw(double x, double min, double max);

	public abstract void updateRoll(double x, double min, double max);

	public abstract void updatePitch(double x, double min, double max);
	
	public abstract void updateX(double x, double min, double max);

	public abstract void updateY(double x, double min, double max);

	public abstract void updateZ(double x, double min, double max);
	
	public abstract void updateLat(double lat, double min, double max);

	public abstract void updateLong(double longitude, double min, double max);

	public abstract void updateAlt(double alt, double min, double max);
	
	public abstract void updateSpeed(float speed, float maxSpeed, float bearing);
	
	public void updateGPSAccuracy(float accurary);
}