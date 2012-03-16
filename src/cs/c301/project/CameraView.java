package cs.c301.project;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * CameraView is the view to call the camera from Android emulator,
 * which will allow the user to take a photo through the camera and 
 * getting ready to save it into the SD card. After taking the photo,
 * it will switch to the PhotoReview interface.
 * 
 * 
 * @author leyuan
 *
 */








public class CameraView extends Activity implements FView {
	/**
	 * Normal beginning to declare which layout the class needs to
	 * show, and set OnClickListener for
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        
        /* Back button to direct to MainView page */
		Button backButton = (Button) findViewById(R.id.camera_back);
		backButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(CameraView.this, MainView.class);
				startActivity(intent);				
			}

		});
		
		/* Take photo button direct to camera page */
		Button takePhotoButton = (Button) findViewById(R.id.camera_take);
		takePhotoButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				/* Save into temporary folder */
				String folder = "tmp";
				String file = "temp"; // change later
				Photo.savePhoto(folder, file);
				getPhoto();
			}
			
		});
		
	}
	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  inverse="cameraView:model.Photo"
	 */
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
				Intent aIntent = new Intent(CameraView.this, PhotoReview.class);
				startActivity(aIntent);
			}
			
		}
	}
}
