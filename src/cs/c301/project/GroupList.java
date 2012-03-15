package cs.c301.project;

import java.io.File;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import cs.c301.project.Utilities.DirectoryFilter;

public class GroupList extends ListActivity {
	private File location;
	private File[] items;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//the filepath for the storage path is stored in this intent
		Bundle extra = getIntent().getExtras();
		
		String filepath = extra.getString("path"); //grabbing the file path, should be stored as an absolute path
		location = new File(filepath); //creates the file object with path for manipulation
		items = location.listFiles(new DirectoryFilter()); //pulls all the directories 
		
		String[] names = new String[items.length];
		
		for (int i = 0; i < items.length; i++) {
			names[i] = items[i].getName();
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.grouplist, R.id.grouplistview, names));
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//start a new activity with the file path of the subgroup in the intent
				Intent intent = new Intent(view.getContext(), PhotoSubView.class);
				intent.putExtra("path", items[position].getAbsolutePath()); //not sure if this position starts at 0 or 1, will need to trial and error
                startActivity(intent);
			}
		});
		
		Button addGroupButton = (Button)findViewById(R.id.searchgroup);
		addGroupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                requestUserInput();
            }
        });
		
		Button searchButton = (Button)findViewById(R.id.searchgroup);
		searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchPhotoView.class);
                startActivity(intent);
            }
        });
	}	
	
    private void requestUserInput() {
    	AlertDialog.Builder newGroupDialog = new AlertDialog.Builder(this);
    	newGroupDialog.setTitle("Add Group");
    	newGroupDialog.setMessage("Please enter the new group name:");

    	final EditText inputName = new EditText(this);
    	newGroupDialog.setView(inputName);

    	newGroupDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String newGroupName = inputName.getText().toString();
				File testDirectory = new File(location.getAbsolutePath() + File.separator + newGroupName);
	  	
				if (!testDirectory.exists()) { //test to see if the directory exists or not
					if (testDirectory.mkdir()) { //make the directory
						items = location.listFiles(new DirectoryFilter()); //pulls all the directories
						String[] names = new String[items.length];
	
						for (int i = 0; i < items.length; i++) {
							names[i] = items[i].getName();
						}
	
						setListAdapter(new ArrayAdapter<String>(this, R.layout.grouplist, R.id.grouplistview, names));
	
						Toast.makeText(getApplicationContext(), newGroupName + " has been successfully added to the group list.", Toast.LENGTH_SHORT).show();
					} else {
						//notify that it failed to make the directory
						Toast.makeText(getApplicationContext(), "An error has occurred while attempting to create group \"" + newGroupName + "\".", Toast.LENGTH_SHORT).show();
					}
				}
				else {
					//notify user that it already exists
					Toast.makeText(getApplicationContext(), newGroupName + " already exists!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	
    	newGroupDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			//do nothing because user cancelled
    		}
    	});
	
		newGroupDialog.show();
	}
}
