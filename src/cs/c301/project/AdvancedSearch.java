package cs.c301.project;

import java.util.Vector;

import cs.c301.project.Data.PhotoEntry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AdvancedSearch extends Activity {

	//private Vector<String> groupV;
	//private Vector<String> tagV;
	private String group;
	private String tag;
	
	EditText dateTextField, groupTextField, tagTextField;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adv_search);
		
		//groupV = new Vector<String>();
		//tagV = new Vector<String>();
		
		
		
		dateTextField = (EditText) findViewById(R.id.date_field);
		
		groupTextField = (EditText) findViewById(R.id.group_field);
		
		tagTextField = (EditText) findViewById(R.id.tag_field);
		
		Button searchButton = (Button) findViewById(R.id.adv_search);
		searchButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				 
				group = groupTextField.getText().toString();
				tag = tagTextField.getText().toString();
				
				//groupV.add(groupTextField.getText().toString());
				//tagV.add(tagTextField.getText().toString());
				//Vector<PhotoEntry> photos = PhotoApplication.getPhotosByValues(groupVector, null);
				Intent intent = new Intent(AdvancedSearch.this, PhotoSubView.class);
				intent.putExtra("group", group);
				intent.putExtra("tag", tag);
				startActivity(intent);
			}
			
		});
	}
}
