package com.eeroprittinen.imageswipe;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;



public class getImageFromURL extends AsyncTask<String, Void, Drawable>{

	public Drawable drawable;
	
	@SuppressWarnings("deprecation")
	@Override
	protected Drawable doInBackground(String... urls) {
		Bitmap x;
		try{
		HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();
		connection.setRequestProperty("user-agent", "Mozilla/4.0");
		
		connection.connect();
		InputStream input=connection.getInputStream();
		x=BitmapFactory.decodeStream(input);
		input.close();
		connection.disconnect();
		}catch(Exception e){
			x=null;
		}
		
		
		return new BitmapDrawable(x);
	}
    protected void onPostExecute(Drawable result) {
        drawable=result;
    }
    
	
	private Object getResources() {
		// TODO Auto-generated method stub
		return null;
	}

}
