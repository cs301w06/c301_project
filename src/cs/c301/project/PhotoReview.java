package cs.c301.project;


import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;

/**
 * Take recent taken photo from the sd card and draws the photo up on screen
 * for the user to review and decide whether to put into group and then keep 
 * the photo or user can discard the photo if it is unwanted
 *
 */
public class PhotoReview extends Activity implements PhotoModelListener {

	ListView partlist;
	Button keepButton;
	ProgressDialog p;
	private String groupName;
	private PhotoEntry photoEntry;

	@Override
	/**
	 * Method called upon activity creation
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);

		ImageView reviewPhoto = (ImageView) findViewById(R.id.review_photo);
		reviewPhoto.setImageDrawable(Drawable.createFromPath(photoEntry.getFilePath()));

		//ImageView comparePhoto = (ImageView) findViewById(R.id.review_photoCompare);

		/** 
		 * Discard button to allow the user to discard unwanted photos
		 */
		Button discardButton = (Button) findViewById(R.id.review_disc);

		discardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				PhotoApplication.removePhoto(photoEntry.getID());
			}

		});

		keepButton = (Button) findViewById(R.id.review_keep);
		keepButton.setText("Select Group");
		keepButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(getApplication(), GroupList.class);
				intent.putExtra("isUnderReview", true);
				startActivityForResult(intent, 0);
			}			
		});

		PhotoApplication.addPhotoModelListener(this);
	}

	/**
	 * Grab the group name of the photo from the group list intent
	 * @param a	
	 * @param b 		
	 * @param intent	extra data from the intent
	 */
	@Override
	protected void onActivityResult(int a, int b, Intent intent) {
		Bundle extra = intent.getExtras();

		groupName = extra.getString("groupname");
		photoEntry.setGroup(groupName);
	}

	/**
	 * Grab the latest photo from the sd card and give the global 
	 * variable the string path
	 * 
	 * @param photos	vector list of photos in the sd card
	 */
	public void photosChanged(Vector<PhotoEntry> photos) {
		int id = -1;
		Date latestDate = null;

		for (int i = 0; i < photos.size(); i++) {
			if (latestDate == null) {
				photos.elementAt(i).getDate();
				id = i;
			} else {
				Date tempDate = photos.elementAt(i).getDate();

				if (tempDate.compareTo(latestDate) > 0) {
					id = i;
					latestDate = tempDate;
				}
			}
		}

		if (id != -1) {
			photoEntry = photos.elementAt(id);

			onStart();
		}
	}

	/**
	 * Show the review photo page and draw the photo on screen
	 */
	protected void onStart() {
		super.onStart();
		ImageView reviewPhoto = (ImageView) findViewById(R.id.review_photo);
		reviewPhoto.setImageDrawable(Drawable.createFromPath(photoEntry.getFilePath()));
	}

	/**
	 * (non-Javadoc)
	 * @see cs.c301.project.Listeners.PhotoModelListener#tagsChanged(java.util.Vector)
	 */
	public void tagsChanged(Vector<String> tags) {
		// TODO Auto-generated method stub

	}

	/** 
	 * (non-Javadoc)
	 * @see cs.c301.project.Listeners.PhotoModelListener#groupsChanged(Vector)
	 */
	public void groupsChanged(Vector<String> groups) {
		// TODO Auto-generated method stub

	}

}
