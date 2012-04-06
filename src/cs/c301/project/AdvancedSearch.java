package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * Allows for searching for groups and tags specifically, rather than together.
 * Currently it will search for both tags and groups simultaneously,
 * so it will return items that have either the tag term AND/OR the group term.
 * 
 * @author esteckle
 *
 */
public class AdvancedSearch extends Activity {

	private String group;
	private String tag;
	
	EditText dateTextField, groupTextField, tagTextField;
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adv_search);
		
		groupTextField = (EditText) findViewById(R.id.group_field);
		
		tagTextField = (EditText) findViewById(R.id.tag_field);
		
		Button searchButton = (Button) findViewById(R.id.adv_search);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				int check = groupTextField.getText().toString().compareTo("");
				if (check != 0)
					group = groupTextField.getText().toString();
				else
					group = null;
				
				check = tagTextField.getText().toString().compareTo("");
				if (check != 0)
					tag = tagTextField.getText().toString();
				else
					tag = null;
				
				Intent intent = new Intent(AdvancedSearch.this, PhotoSubView.class);
				intent.putExtra("group", group);
				intent.putExtra("tag", tag);
				startActivity(intent);
			}
			
		});
	}
}
