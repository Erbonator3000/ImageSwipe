package com.eeroprittinen.imageswipe;

import java.util.Arrays;
import java.util.Stack;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;


public class MainActivity extends Activity {

	private Stack<String> imageURLs;
	private Drawable nextImage;
	
	private float touchBeginX;
	static final int minSwipe=150;
	
	private ImageSwitcher imageSwitcher;
	private Button yesButton;
	private Button noButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
		yesButton=(Button)findViewById(R.id.button1);
		noButton=(Button)findViewById(R.id.button2);
		
		
		imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
			
			@Override
			public View makeView() {
				ImageView myView = new ImageView(getApplicationContext());
				myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				
		        myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		        return myView;
			}
		});
		
		imageURLs=new Stack<String>();
		//populate imageURLs with URLs from some list or from online
		imageURLs.addAll(Arrays.asList(getResources().getStringArray(R.array.URLs)));
		
		imageSwitcher.setImageDrawable(URLImage(imageURLs.pop()));
		nextImage=URLImage(imageURLs.pop());
		
		yesButton.setOnClickListener(new View.OnClickListener(){
		     @Override
	         public void onClick(View v) {
		    	 
	            dislikeImage();
	         }
		});
		
		noButton.setOnClickListener(new View.OnClickListener(){
		     @Override
	         public void onClick(View v) {
	            likeImage();
	         }
		});
		
		imageSwitcher.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//TODO detect swipe gestures better?
				switch (event.getAction())
				{case MotionEvent.ACTION_DOWN:
				touchBeginX=event.getX();
					break;
				case MotionEvent.ACTION_UP:
					if (touchBeginX-event.getX()>=minSwipe){
						dislikeImage();
					}else if (event.getX()-touchBeginX>=minSwipe){
						likeImage();
					}
					
					break;
				}
				
				return true;
			}
			
		});

	
	}
	
	
	
	
	
	private void switchImage(){
		//TODO limit click frequency because of animation
		
		imageSwitcher.setImageDrawable(nextImage);
		//TODO check if ImagesURLs empty and set images run out image if empty (and disable interactivity?)
		if (!imageURLs.empty()){
		nextImage=URLImage(imageURLs.pop());
		}else{
			nextImage=getResources().getDrawable(R.drawable.ic_launcher);//out of images
		}
		
	}

	private void likeImage(){
		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left));
		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.tilt_out_right)); 
		switchImage();
	}
	
	private void dislikeImage(){
		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_right));
		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.tilt_out_left));  
		switchImage();
	}
	
	
	private void outOfImages(){
		imageSwitcher.setClickable(false);
		yesButton.setClickable(false);
		noButton.setClickable(false);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private Drawable URLImage(String imageURL) { 
		Drawable image;
		try{
			image = new	getImageFromURL().execute(imageURL).get();
		
		}catch(Exception e){
			image=getResources().getDrawable(R.drawable.ic_launcher);//Image_not_found.png or some stuff
		}
		
		if (image==null){
			image=getResources().getDrawable(R.drawable.ic_launcher);//Image_not_found.png or some stuff
		} 
		
		
		return image;
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
