package cs.c301.project;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cs.c301.project.Data.PhotoEntry;

/**
 * The main page of they project, gives choices for user to chose from.  
 * User can choose to take a photo, list the photos already taken, and search
 * the environment (saved data) for saved photos
 */
public class MainView extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/**
		 *  Camera button to direct to CameraView page 
		 *  
		 *  @parameters entry 	to get the temporary image
		 *  @parameters imageUri 	Uri for image
		 *  @parameters intent 	call the camera
		 *  
		 */
		Button cameraButton = (Button) findViewById(R.id.main_camera);
		cameraButton.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
				
					PhotoEntry entry = PhotoApplication.getTemporaryImage();
					
					File imageFile = new File(entry.getFilePath());
					Uri imageUri = Uri.fromFile(imageFile);
	
					PhotoApplication.addPhoto(entry);
					
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					
					startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);	
			}
			
		});

		Button listButton = (Button) findViewById(R.id.main_list);
		listButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, GroupList.class);
				startActivity(intent);
			}
		});
		
		Button searchButton = (Button) findViewById(R.id.main_search);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, SearchPhotoView.class);
				startActivity(intent);				
			}

		});
	}
	

	
	/**
	 * onActivityResult is the method to handle the result after taking a photo,
	 * if the photo is taken successfully, switch the intent to PhotoReview.
	 * 
	 * @parameters	CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE 	detect if the photo is taken correctly
	 * @parameters	intent		switch intent
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
	
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Intent aIntent = new Intent(MainView.this, PhotoReview.class);
				startActivity(aIntent);
			}
	
		}
	}


	/**
	 * @uml.property  name="groupList"
	 * @uml.associationEnd  inverse="mainView:cs.c301.project.GroupList"
	 */
	private GroupList groupList;

	/**
	 * Getter of the property <tt>groupList</tt>
	 * @return  Returns the groupList.
	 * @uml.property  name="groupList"
	 */
	public GroupList getGroupList() {
		return groupList;
	}


	/**
	 * Setter of the property <tt>groupList</tt>
	 * @param groupList  The groupList to set.
	 * @uml.property  name="groupList"
	 */
	public void setGroupList(GroupList groupList) {
		this.groupList = groupList;
	}


	/**
	 * @uml.property  name="searchPhotoView"
	 * @uml.associationEnd  inverse="mainView:cs.c301.project.SearchPhotoView"
	 */
	private SearchPhotoView searchPhotoView;

	/**
	 * Getter of the property <tt>searchPhotoView</tt>
	 * @return  Returns the searchPhotoView.
	 * @uml.property  name="searchPhotoView"
	 */
	public SearchPhotoView getSearchPhotoView() {
		return searchPhotoView;
	}


	/**
	 * Setter of the property <tt>searchPhotoView</tt>
	 * @param searchPhotoView  The searchPhotoView to set.
	 * @uml.property  name="searchPhotoView"
	 */
	public void setSearchPhotoView(SearchPhotoView searchPhotoView) {
		this.searchPhotoView = searchPhotoView;
	}
}
