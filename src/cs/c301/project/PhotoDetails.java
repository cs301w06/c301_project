package cs.c301.project;

import cs.c301.project.Data.PhotoEntry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoDetails extends Activity {
	
	private PhotoEntry photoEntry;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		Bundle extra = getIntent().getExtras();
		photoEntry = (PhotoEntry) extra.get("photo");
		
		Button deleteButton = (Button) findViewById(R.id.det_delete);
		deleteButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				PhotoApplication.removePhoto(photoEntry.getID());
				finish();			
			}
			
		});
		
		Button tagButton = (Button) findViewById(R.id.det_tag_button);
		tagButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
			}
			
		});
		
		Button compareButton = (Button) findViewById(R.id.det_compare);
		compareButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(PhotoDetails.this, PhotoCompare.class);
				startActivity(intent);
			}
			
		});
		
		TextView descriptionText = (TextView) findViewById(R.id.description_text);

		TextView dateText = (TextView) findViewById(R.id.date_text);
		dateText.setText(photoEntry.getDate().toString());
		
		TextView tagText = (TextView) findViewById(R.id.tag_text);
	}
	
	protected void onStart() {
		super.onStart();
		ImageView reviewPhoto = (ImageView) findViewById(R.id.det_photo);
		reviewPhoto.setImageBitmap(photoEntry.getBitmap());
	}
}
