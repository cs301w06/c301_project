package cs.c301.project;

import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import cs.c301.project.Data.PhotoEntry;

/**
 * Take recent taken photo from file and draws the photo up on screen
 * for the user to review and decide whether to put into group and then keep 
 * the photo or user can discard the photo if it is unwanted
 *
 */
public class PhotoReview extends Activity {

	ListView partlist;
	Button groupButton, keepButton;
	ProgressDialog p;
	private String groupName;
	private PhotoEntry photoEntry;
	private boolean isGroupSelected;
	
	@Override
	/**
	 * Method called upon activity creation
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);
		
		isGroupSelected = false;
		
		Bundle extra = getIntent().getExtras();
		
		photoEntry = (PhotoEntry)extra.getSerializable("photo");
		
		ImageView reviewPhoto = (ImageView) findViewById(R.id.review_photo);
		reviewPhoto.setImageBitmap(photoEntry.getBitmap());
		
		Button discardButton = (Button) findViewById(R.id.review_disc);
		discardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Photo Discarded", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplication(), MainView.class);
				startActivity(intent);
			}

		});

		groupButton = (Button) findViewById(R.id.review_group);
		groupButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {

				Intent intent = new Intent(getApplication(), GroupList.class);
				intent.putExtra("isUnderReview", true);
				startActivityForResult(intent, 0);
			}			
		});

		keepButton = (Button) findViewById(R.id.review_keep);
		keepButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				if (isGroupSelected) {
					Intent intent = new Intent(getApplication(), PhotoSubView.class);
					PhotoApplication.addPhoto(photoEntry);
					Toast.makeText(getApplicationContext(), "Photo Saved", Toast.LENGTH_SHORT).show();
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "Please select a group first", Toast.LENGTH_SHORT).show();
				}
			}

		});
	}

	/**
	 * Grab the group name of the photo from the group list intent
	 * @param a	
	 * @param b 		
	 * @param intent	extra data from the intent
	 */
	@Override
	protected void onActivityResult(int a, int b, Intent intent) {	
		try {
			Bundle extra = intent.getExtras();

			groupName = extra.getString("group");
			photoEntry.setGroup(groupName);
			
			isGroupSelected = true;
		}

		catch (Exception e) {}
	}
}
