/*package cs.c301.project;

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
import android.widget.ImageButton;

public class CameraActivity extends Activity {
	
	Uri imageUri;
	
    /** Called when the activity is first created. 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
        
        OnClickListener listener = new OnClickListener() {

			public void onClick(View arg0) {
				// click button and it will take photo
				takeAPhoto();
			}
        };
        button.setOnClickListener(listener);
    }
    
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	protected void takeAPhoto() {
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Folder destination so that photos can be saved here
		String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
		// Create a new file
		File folderF = new File(folder);
		// Create folder if does not exist
		if (!folderF.exists()) {
			folderF.mkdir();
		}
		// Save image in folder and current time as name
		String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		// Create Uri
		File imageFile = new File(imageFilePath);
		imageUri = Uri.fromFile(imageFile);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Cache and show image on button
				ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
				button.setImageDrawable(Drawable.createFromPath(imageUri.getPath()));
			}
		}
	}
}
*/