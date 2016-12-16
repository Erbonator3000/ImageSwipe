package com.eeroprittinen.imageswipe;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class getImageURLs extends AsyncTask<String, Void, String[]>{

	
	@Override
	protected String[] doInBackground(String... urls) {
		try{
		HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();
		connection.setRequestProperty("user-agent", "Mozilla/4.0");
		
		connection.connect();
		InputStream input=connection.getInputStream();
		}catch(Exception e){
		}
		
		return urls; //TODO implement downloading URLs
	}
    protected void onPostExecute(String[] result) {
        
    }
    
	
	private Object getResources() {
		// TODO Auto-generated method stub
		return null;
	}

}
