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
import android.widget.Toast;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;

public class PhotoReview extends Activity implements PhotoModelListener {
	ListView partlist;
	Button keepButton;
	ProgressDialog p;
	private String groupName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);
//		PhotoApplication.addPhotoModelListener(this);
		
		/* Review of photo */
		ImageView reviewPhoto = (ImageView) findViewById(R.id.review_photo);
		reviewPhoto.setImageDrawable(Drawable.createFromPath(PhotoApplication.getLatestImage().getFilePath()));
		
		/* Comparasion photo */
		ImageView comparePhoto = (ImageView) findViewById(R.id.review_photoCompare);

		/* Discard button */
		Button discardButton = (Button) findViewById(R.id.review_disc);
		discardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

			}

		});

		/* Select group and keep button */
		keepButton = (Button) findViewById(R.id.review_keep);
		keepButton.setText("Select Group");
		keepButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				Intent intent = new Intent(getApplication(), GroupList.class);
				intent.putExtra("isUnderReview", true);
				startActivityForResult(intent, 0);
			}			
		});
	}
	
    @Override
    protected void onActivityResult(int a, int b, Intent intent) {
    	Bundle extra = intent.getExtras();
    	
    	groupName = extra.getString("groupname");
    }
    
	public void photosChanged(Vector<PhotoEntry> photos) {
		
	}

	public void tagsChanged(Vector<String> tags) {
		// TODO Auto-generated method stub
		
	}

	public void groupsChanged(Vector<String> groups) {
		// TODO Auto-generated method stub
		
	}

}
