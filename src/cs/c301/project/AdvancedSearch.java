package cs.c301.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AdvancedSearch extends Activity {

	EditText dateTextField, groupTextField, tagTextField;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adv_search);
		
		dateTextField = (EditText) findViewById(R.id.date_field);
		
		groupTextField = (EditText) findViewById(R.id.group_field);
		
		tagTextField = (EditText) findViewById(R.id.tag_field);
		
		Button searchButton = (Button) findViewById(R.id.adv_search);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
			}
			
		});
	}
}
