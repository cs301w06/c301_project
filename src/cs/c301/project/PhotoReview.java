package cs.c301.project;

import java.util.WeakHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cs.c301.project.Data.PhotoEntry;

/**
 * Take recent taken photo from file and draws the photo up on screen
 * for the user to review and decide whether to put into group and then keep
 * the photo or user can discard the photo if it is unwanted
 *
 */
public class PhotoReview extends Activity {

	private Button groupButton, keepButton, tagButton, annotationButton;
	private String groupName, tagName, annotationText;
	private Bitmap newBMP;
	private PhotoEntry newPhoto;

	public WeakHashMap<Integer, AlertDialog.Builder> dialogs;

	@Override
	/** Method called upon activity creation */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.review);

		newBMP = setBogoPic();
		dialogs = new WeakHashMap<Integer, AlertDialog.Builder>();

		Button discardButton = (Button) findViewById(R.id.review_disc);
		discardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
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

		tagButton = (Button) findViewById(R.id.review_tag);
		tagButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Please select a group first", Toast.LENGTH_SHORT).show();
			}

		});

		keepButton = (Button) findViewById(R.id.review_keep);
		keepButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Please select a group first", Toast.LENGTH_SHORT).show();
			}
		});
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

		try {
			Bundle extra = intent.getExtras();
			newPhoto = new PhotoEntry();
			groupName = extra.getString("group");
			tagName = extra.getString("tag");

			newPhoto.setTags(tagName);

			if (groupName != null) {
				newPhoto.setGroup(groupName);

				tagButton = (Button) findViewById(R.id.review_tag);
				tagButton.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						Intent intent = new Intent(getApplication(), TagList.class);
						intent.putExtra("isUnderReview", true);
						startActivityForResult(intent, 0);
					}

				});
				
				keepButton.setOnClickListener(new Button.OnClickListener() {

					public void onClick(View v) {
						requestAnnotation();
					}
				});
			}

			newPhoto.setBitmap(newBMP);
			
			if (newPhoto.getBitmap() == null) {
				Toast.makeText(getApplicationContext(), "Photo Null", Toast.LENGTH_SHORT).show();
			}
		}
		catch (Exception e) {}
	}

	/** Show the review photo page and draw the photo on screen */
	protected void onStart() {
		super.onStart();

		ImageView reviewPhoto = (ImageView) findViewById(R.id.review_photo);
		reviewPhoto.setImageBitmap(newBMP);
	}

	/** Generate new bmp */
	private Bitmap setBogoPic() {
		return BogoPicGen.generateBitmap(400, 400);
	}

	/** User enters in a new annotation of the photo */
	private void requestAnnotation() {
		AlertDialog.Builder annotationDialog = new AlertDialog.Builder(this);
		annotationDialog.setTitle("Add Annotation");
		annotationDialog.setMessage("Would you like to add a description? ");

		final EditText inputName = new EditText(this);
		annotationDialog.setView(inputName);

		annotationDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				annotationText = inputName.getText().toString();
				newPhoto.setAnnotation(annotationText);
				Toast.makeText(getApplicationContext(), "Photo Saved", Toast.LENGTH_SHORT).show();
				PhotoApplication.addPhoto(newPhoto);
				Intent intent = new Intent(PhotoReview.this, PhotoSubView.class);
				intent.putExtra("group", groupName);
				startActivity(intent);
				finish();
			}
		});

		annotationDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				PhotoApplication.addPhoto(newPhoto);
				Toast.makeText(getApplicationContext(), "Photo Saved", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(PhotoReview.this, PhotoSubView.class);
				intent.putExtra("group", groupName);
				startActivity(intent);
				finish();
			}
		});

		dialogs.put(0, annotationDialog);
		annotationDialog.show();
	}
}
