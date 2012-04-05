package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * SearchPhotoView will allow the user to search photo by tag
 * group and date of the photo. Currently not implemented and used
 * as a place holder.  Will be completed for part 4.
 */
public class SearchPhotoView extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		Button advancedSearchButton = (Button) findViewById(R.id.search_adv);
		advancedSearchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SearchPhotoView.this, AdvancedSearch.class);
				startActivity(intent);
			}
		});
		
	}
}