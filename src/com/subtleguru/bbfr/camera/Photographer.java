package com.subtleguru.bbfr.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.subtleguru.bbfr.RCBlackBoxApplication;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;

public class Photographer {
	
	ShutterCallback shutterCallback;
	PictureCallback rawCallback;
	PictureCallback jpegCallback;

	final Context context;
	
	RCBlackBoxApplication app;
	
	public Photographer (final Context context, final RCBlackBoxApplication app) {
	
	  this.context = context;
	  context.getApplicationContext();
	  this.app = app;
	  
		shutterCallback = new ShutterCallback() {
			public void onShutter() {	
				Log.d(RCBlackBoxApplication.APP_NAME, "Shutter ...");
			}			
		};
		
		rawCallback = new PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				Log.d(RCBlackBoxApplication.APP_NAME, "RAW Image accuired ....");
			}			
		};
		
		jpegCallback = new PictureCallback() {
			
			public void onPictureTaken(byte[] data, Camera camera) {
				try {	
					Log.d(RCBlackBoxApplication.APP_NAME, "Accuired jpeg ..... ");
					File f = new File("/sdcard/" + RCBlackBoxApplication.APP_NAME);
					if (!f.isDirectory()) f.mkdir();
					String fname = "/sdcard/" + RCBlackBoxApplication.APP_NAME + "/photo_"
												+ app.nextSequence() + ".jpg";
				  FileOutputStream out = new FileOutputStream(fname);
				  out.write(data);
				  out.close();
				} catch (FileNotFoundException fnfe) {
					Log.d(RCBlackBoxApplication.APP_NAME, fnfe.getMessage());
				} catch (IOException ioe) {
					Log.d(RCBlackBoxApplication.APP_NAME, ioe.getMessage());
				}
			}	
		};		
	}
	
	public void takePhoto() {
			Log.d(RCBlackBoxApplication.APP_NAME, "start preview ...");
			app.getCamera().startPreview();
			Log.d(RCBlackBoxApplication.APP_NAME, "take picture ...");
			app.getCamera().takePicture(shutterCallback, rawCallback, jpegCallback);
	}
}
