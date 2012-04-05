package cs.c301.project;

import cs.c301.project.Data.PhotoEntry;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoDetails extends Activity {
	
	private PhotoEntry photoEntry;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		Bundle extra = getIntent().getExtras();
		photoEntry = (PhotoEntry) extra.get("photo");
		
	}
	
	protected void onStart() {
		super.onStart();
		ImageView reviewPhoto = (ImageView) findViewById(R.id.det_photo);
		reviewPhoto.setImageBitmap(photoEntry.getBitmap());
		
	}
}
