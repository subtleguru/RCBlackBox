package com.subtleguru.bbfr.telemetry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordingTape {
	
	public class Reading {
		  public Date ts;
		  public double x;
		  public double y;
		  public double z;
		  public Reading(double x, double y, double z) {
		  	this.x = x;
		  	this.y = y;
		  	this.z = z;
		  	this.ts = new Date();
		  }
	}
		
	List<Reading> data;
	double minX;
	double maxX;
	double minY;
	double maxY;
	double minZ;
	double maxZ;
	
	long maxSize;  
		
	public RecordingTape (long size) {
		maxSize = size;
		data = new ArrayList<Reading>();
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public double getMinZ() {
		return minZ;
	}

	public void setMinZ(double minZ) {
		this.minZ = minZ;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(double maxZ) {
		this.maxZ = maxZ;
	}

	public List<Reading> getData() {
		return data;
	}
	
	public void append(float x, float y, float z) {
		append((double)x, (double) y, (double) z);
	}

	public void append(double x, double y, double z) {
		if (data.isEmpty()) {
			minX = x;
			minY = y;
			minZ = z;
			maxX = x;
			maxY = y;
			maxZ = z;
		} else {
			minX = Math.min(x,minX);
			maxX = Math.max(x,maxX);
			minY = Math.min(y,minY);
			maxY = Math.max(y,maxY);
			minZ = Math.min(z, minZ);
			maxZ = Math.max(z, maxZ);
		}
		data.add(new Reading(x,y,z));
		
		if (data.size() > maxSize) {
			data.remove(0);
		}
	}

}
