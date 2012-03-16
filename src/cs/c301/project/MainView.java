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
				Photo.savePhoto(folder, file);
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
	private Photo photo;

	/**
	 * Getter of the property <tt>photo</tt>
	 * @return  Returns the photo.
	 * @uml.property  name="photo"
	 */
	
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
    protected void getPhoto() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		Photo.savePhoto("tmp", "temp");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Photo.getPhoto("tmp", "temp"));
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);		
		
	}

	/**
	 * Setter of the property <tt>photo</tt>
	 * @param photo  The photo to set.
	 * @uml.property  name="photo"
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
