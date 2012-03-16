package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class MainView extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/* Camera button to direct to CameraView page */
		Button cameraButton = (Button) findViewById(R.id.main_camera);
		cameraButton.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
				
				/* Save into temporary folder */
				String folder = "tmp";
				String file = "temp"; // change later
				getPhoto();
			}
			
		});

		
		/* List button to direct to PartsListView page */
		Button listButton = (Button) findViewById(R.id.main_list);
		listButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, GroupList.class);
				startActivity(intent);
			}
		});
		
		/* Search button to direct to SearchPhoto page */
		Button searchButton = (Button) findViewById(R.id.main_search);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, SearchPhotoView.class);
				startActivity(intent);				
			}

		});
	}

	/**
	 * Getter of the property <tt>photo</tt>
	 * @return  Returns the photo.
	 * @uml.property  name="photo"
	 */
	
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
    protected void getPhoto() {
    	
    	/**
    	 * This is the method to call the Android camera from the user mobile
    	 * and here we have specify the folder and the name we want to save as.
    	 * 
    	 * @parameter intent	the intent to call the camera from the phone
    	 * @parameter photo		the result after the photo has been taken successfully
    	 */

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);		
		
	}

	/**
	 * Setter of the property <tt>photo</tt>
	 * @param photo  The photo to set.
	 * @uml.property  name="photo"
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		/**
		 * onActivityResult is the method to handle the result after taking a photo,
		 * if everything goes well, the program will switch into the interface of 
		 * PhotoReview.
		 * 
		 * @parameter requestCode	detect whether the photo is taken properly
		 * @parameter resultCode	detect if we should switch intent
		 * @parameter intent		switch the interface to PhotoReview
		 * 
		 */
		
		
		
		
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			
			if (resultCode == RESULT_OK) {
				Intent aIntent = new Intent(MainView.this, PhotoReview.class);
				startActivity(aIntent);
			}
			
		}
	}
}
