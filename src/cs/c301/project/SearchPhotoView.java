package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * SearchPhotoView allows the user to search all groups and tags
 * by any text. A search will be run on the tags and groups with the
 * same parameter, so it will return everything in both tags and groups
 * containing the parameter
 * 
 * @author esteckle
 */
public class SearchPhotoView extends Activity {
	EditText searchTextField;
	private String search;
	
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		searchTextField = (EditText) findViewById(R.id.search_text);
		
		Button advancedSearchButton = (Button) findViewById(R.id.search_adv);
		advancedSearchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SearchPhotoView.this, AdvancedSearch.class);
				startActivity(intent);
			}
		});
		
		Button searchButton = (Button) findViewById(R.id.search_b);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int check = searchTextField.getText().toString().compareTo("");
				if (check != 0)
					search = searchTextField.getText().toString();
				else
					search = null;
				
				Intent intent = new Intent(SearchPhotoView.this, PhotoSubView.class);
				intent.putExtra("group", search);
				intent.putExtra("tag", search);
				startActivity(intent);
			}
			
		});
		
	}
}