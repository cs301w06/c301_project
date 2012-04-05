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

/**
 * Photo details allows the user to view the photo with 
 * extra details like description, tags and date of the 
 * photo
 * 
 * @author wjtran
 *
 */
public class PhotoDetails extends Activity {
	
	private PhotoEntry photoEntry;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		Bundle extra = getIntent().getExtras();
		
		final int photoId = (Integer) extra.get("photo");
		
		photoEntry = PhotoApplication.getPhotoByID(photoId);
		
		Button deleteButton = (Button) findViewById(R.id.det_delete);
		deleteButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				PhotoApplication.removePhoto(photoId);
				finish();			
			}
			
		});
		
		Button tagButton = (Button) findViewById(R.id.det_tag_button);
		tagButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//TODO add tag
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
		descriptionText.setText(photoEntry.getAnnotation());
		
		TextView dateText = (TextView) findViewById(R.id.date_text);
		dateText.setText(photoEntry.getDate().toString());
		
		TextView tagText = (TextView) findViewById(R.id.tag_text);
	
		tagText.setText(photoEntry.getTagsForDatabase());
	}
	
	protected void onStart() {
		super.onStart();
		ImageView reviewPhoto = (ImageView) findViewById(R.id.det_photo);
		reviewPhoto.setImageBitmap(photoEntry.getBitmap());
	}
}
