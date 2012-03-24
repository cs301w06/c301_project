package cs.c301.project;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * The main page of they project, gives choices for user to chose from.  
 * User can choose to take a photo, list the photos already taken, and search
 * the environment (saved data) for saved photos
 */
public class MainView extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button cameraButton = (Button) findViewById(R.id.main_camera);
		cameraButton.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "tmp.jpg"));
	
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					
					startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);	
			}
			
		});

		Button listButton = (Button) findViewById(R.id.view_by_group);
		listButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, GroupList.class);
				startActivity(intent);
			}
		});
		
		Button searchButton = (Button) findViewById(R.id.main_search);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, SearchPhotoView.class);
				startActivity(intent);				
			}

		});
		
		Button tagsButton = (Button) findViewById(R.id.view_by_tag);
		tagsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, TagList.class);
				startActivity(intent);				
			}

		});
	}
	
	/**
	 * onActivityResult is the method to handle the result after taking a photo,
	 * if the photo is taken successfully, switch the intent to PhotoReview.
	 * 
	 * @parameters	CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE 	detect if the photo is taken correctly
	 * @parameters	intent		switch intent
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
	
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Intent aIntent = new Intent(MainView.this, PhotoReview.class);
				startActivity(aIntent);
			}
	
		}
	}
}
