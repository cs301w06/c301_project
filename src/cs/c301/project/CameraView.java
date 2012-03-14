package cs.c301.project;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;



public class CameraView extends Activity implements FView {
	Uri imageUri;

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
		
		
		
		Button takePhotoButton = (Button) findViewById(R.id.camera_take);
		OnClickListener listener = new OnClickListener(){

			public void onClick(View v) {
				getPhoto();
			}
			
		};
		takePhotoButton.setOnClickListener(listener);
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
		// TODO Auto-generated method stub
		//Intent to capture a pic
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// create abslute path
		String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
		
		File folderF = new File(folder);
		//check the folder exists, otherwise create one
		if(!folderF.exists()){
			
			folderF.mkdir();
			
		}
		
		// create a path/name for new pics, in form of time.jpg
		String imageFilePath = folder + "/" + "temp" + ".jpg";
		File imageFile = new File(imageFilePath);
		//creating uri
		imageUri = Uri.fromFile(imageFile);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		// start activity and get result
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);		
			
		
	}

	/**
	 * Setter of the property <tt>photo</tt>
	 * @param photo  The photo to set.
	 * @uml.property  name="photo"
	 */
	// handle result
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			
			if (resultCode == RESULT_OK){
				ImageView photoView = (ImageView) findViewById(R.id.camera_image);
				// get image and show it on the image button
				photoView.setImageDrawable(Drawable.createFromPath(imageUri.getPath()));
				
				/* added for debugging */
				Intent aIntent = new Intent(CameraView.this, PhotoReview.class);
				startActivity(aIntent);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
