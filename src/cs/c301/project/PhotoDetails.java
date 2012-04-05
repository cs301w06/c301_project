package cs.c301.project;

import cs.c301.project.Data.PhotoEntry;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoDetails extends Activity{
	
	//private PhotoEntry photoEntry;
	private String filepath;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		//Bundle extra = getIntent().getExtras();
		
		//filepath = extra.getString("path");
	}
	
	protected void onStart() {
		super.onStart();
		ImageView reviewPhoto = (ImageView) findViewById(R.id.det_photo);
		reviewPhoto.setImageDrawable(Drawable.createFromPath(filepath));
	}
}
