package cs.c301.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * The main page of they project, gives choices for user to chose from.  
 * User can choose to take a photo, list the photos already taken, and search
 * the environment (saved data) for saved photos
 * @author yhu3
 */
public class MainView extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private boolean isToLogout;
	private boolean isDoctor;
	private boolean isPatient;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		isToLogout = false;
		isDoctor = false;
		isPatient = false;
		
		Bundle extra = getIntent().getExtras();
		
		try {
			isDoctor = extra.getBoolean("isDoctor");
		}
		
		catch (Exception e) {}
		
		try {	
			isPatient = extra.getBoolean("isPatient");
		
			if (isPatient)
				isToLogout = true;
		}
		
		catch (Exception e) {}
		
		Button cameraButton = (Button) findViewById(R.id.main_camera);
		cameraButton.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					if (!isDoctor) {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, PhotoApplication.getTemporaryImage());
						
						startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
					} else if (isDoctor) {
						Intent intent = new Intent(getApplicationContext(), PatientList.class);
						startActivity(intent);
					}
			}
		});
		
		Button listButton = (Button) findViewById(R.id.view_by_group);
		listButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, GroupList.class);
				intent.putExtra("isPatient", isPatient);
				startActivity(intent);
			}
		});
		
		Button searchButton = (Button) findViewById(R.id.main_search);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, AdvancedSearch.class);
				startActivity(intent);	
			}

		});
		
		Button tagsButton = (Button) findViewById(R.id.view_by_tag);
		tagsButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, TagList.class);
				intent.putExtra("isPatient", isPatient);
				startActivity(intent);				
			}

		});
		
		Button settingButton = (Button) findViewById(R.id.setting);
		settingButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v)
			{
				Intent intent = new Intent(MainView.this, settingView.class);
				startActivity(intent);
			}
		});
		
		if (isDoctor) {
			cameraButton.setText("View Patients");
			tagsButton.setVisibility(View.INVISIBLE);
			listButton.setVisibility(View.INVISIBLE);
			searchButton.setVisibility(View.INVISIBLE);
		}
		
		if (isPatient) {
			tagsButton.setText("View By Patient Tags");
			settingButton.setVisibility(View.INVISIBLE);
			TextView mainView = (TextView)findViewById(R.id.mainTitle);
			mainView.setText(extra.getString("patientName"));
		}
	}
	
	/**
	* onActivityResult is the method to handle the result after taking a photo,
	* if the photo is taken successfully, switch the intent to PhotoReview.
	*
	* @param requestCode Integer request code originally supplied, allowing you to identify who
	* this result came from
	* @param resultCode Integer result code returned by child activity through its setResult()
	* @param intent Intent, which can return result data to caller
	*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Intent aIntent = new Intent(getApplication(), PhotoReview.class);
				startActivity(aIntent);
			}
		}
	}

	@Override
	public void onBackPressed() {
		if (isPatient) {
			PhotoApplication.toggleDoctor();
		}
		
		if (isToLogout)
			super.onBackPressed();
		else {
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE:
			        	isToLogout = true;
			        	onBackPressed();
			            break;
	
			        case DialogInterface.BUTTON_NEGATIVE:
			            
			            break;
			        }
			    }
			};
	
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show();
		}
	}
}
