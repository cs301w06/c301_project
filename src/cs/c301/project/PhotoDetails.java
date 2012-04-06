package cs.c301.project;

import cs.c301.project.Data.PhotoEntry;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

		Button tagButton = (Button) findViewById(R.id.det_tag_button);
		tagButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(PhotoDetails.this, TagList.class);
				intent.putExtra("isUnderReview", true);
				startActivityForResult(intent, 0);
			}

		});

		Button compareButton = (Button) findViewById(R.id.det_compare);
		compareButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(PhotoDetails.this, PhotoCompare.class);
				intent.putExtra("photo", photoId);
				startActivity(intent);
			}

		});

		Button delButton = (Button) findViewById(R.id.det_del);
		delButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v)	{

				new AlertDialog.Builder(PhotoDetails.this)
				.setTitle("Confirm Information")
				.setMessage("Are you sure you want to delete?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener()	{

					public void onClick(DialogInterface dialog, int which) {
						PhotoApplication.removePhoto(photoId);
						Toast.makeText(getApplicationContext(), "Photo has been deleted", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(PhotoDetails.this, MainView.class);
						startActivity(intent);
						finish();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
			}
		});

		TextView descriptionText = (TextView) findViewById(R.id.description_text);
		descriptionText.setText(photoEntry.getAnnotation());

		TextView dateText = (TextView) findViewById(R.id.date_text);
		dateText.setText(photoEntry.getDate().toString());

		TextView tagText = (TextView) findViewById(R.id.tag_text);
		tagText.setText(photoEntry.getTagsForDatabase());
	}

	/**
	 * overrides the onstart of the intent that will show the most recent photo
	 * captured to be reviewed by the user to take action
	 */
	protected void onStart() {
		super.onStart();
		ImageView reviewPhoto = (ImageView) findViewById(R.id.det_photo);
		reviewPhoto.setImageBitmap(photoEntry.getBitmap());

		TextView tagText = (TextView) findViewById(R.id.tag_text);
		tagText.setText(photoEntry.getTagsForDatabase());
	}

	/**
	 * Grab the group name of the photo from the group list intent
	 * @param requestCode Integer request code originally supplied, allowing you to identify who
	 * this result came from
	 * @param resultCode Integer result code returned by child activity through its setResult()
	 * @param intent Intent, which can return result data to caller
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		try{
			Bundle extra = intent.getExtras();
			String tagName = null;
			if (extra.getString("tag") != null)
				tagName = extra.getString("tag");
			if (tagName != null)
				photoEntry.addTag(tagName);
			PhotoApplication.updatePhoto(photoEntry);
		}
		catch(Exception e){}
	}
}
