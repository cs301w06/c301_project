package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainView extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/* Camera button to direct to CameraView page */
		Button cameraButton = (Button) findViewById(R.id.main_camera);
		cameraButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, CameraView.class);
				startActivity(intent);				
			}

		});
		
		/* List button to direct to PartsListView page */
		Button listButton = (Button) findViewById(R.id.main_list);
		listButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, GroupList.class);
				startActivity(intent);
			}
		});
		
		/* Search button to direct to SearchPhoto page */
		Button searchButton = (Button) findViewById(R.id.main_search);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(MainView.this, SearchPhotoView.class);
				startActivity(intent);				
			}

		});
	}
}