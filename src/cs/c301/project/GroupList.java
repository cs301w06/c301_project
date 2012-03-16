package cs.c301.project;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;
import cs.c301.project.Utilities.DirectoryFilter;

public class GroupList extends Activity implements PhotoModelListener {
	private File location;
	private File[] items;
	private boolean isUnderReview;
	private ListView lv;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grouplist);
		
		//the filepath for the storage path is stored in this intent
		Bundle extra = getIntent().getExtras();
		
		try {
			isUnderReview = extra.getBoolean("isUnderReview");
		}
		
		catch (Exception e) {
			isUnderReview = false;
		}
		
		//filepath = extra.getString("path"); //grabbing the file path, should be stored as an absolute path
		location = new File(PhotoApplication.getFilePath()); //creates the file object with path for manipulation
		items = location.listFiles(new DirectoryFilter()); //pulls all the directories 
		
		String[] names = new String[items.length];
		
		for (int i = 0; i < items.length; i++) {
			names[i] = items[i].getName();
		}
		
		lv = (ListView)findViewById(R.id.grouplistview);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);
		
		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!isUnderReview) {
					//start a new activity with the file path of the subgroup in the intent
					Intent intent = new Intent(view.getContext(), PhotoSubView.class);
					intent.putExtra("path", items[position].getAbsolutePath()); //not sure if this position starts at 0 or 1, will need to trial and error
	                startActivity(intent);
				} else {
					Intent intent = new Intent();
	                intent.putExtra("groupname", items[position].getName());
	                setResult(1, intent);
	                finish();
				}
			}
		});
		
		Button addGroupButton = (Button)findViewById(R.id.addgroup);
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
						onStart();
						
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
    
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	location = new File(PhotoApplication.getFilePath()); //creates the file object with path for manipulation
		items = location.listFiles(new DirectoryFilter()); //pulls all the directories 
		
		String[] names = new String[items.length];
		
		for (int i = 0; i < items.length; i++) {
			names[i] = items[i].getName();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);
		
		lv.setAdapter(adapter);
    }
    
	public void photosChanged(Vector<PhotoEntry> photos) {
		
	}

	public void tagsChanged(Vector<String> tags) {
		// TODO Auto-generated method stub
		
	}

	public void groupsChanged(Vector<String> groups) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @uml.property  name="mainView"
	 * @uml.associationEnd  inverse="groupList:cs.c301.project.MainView"
	 */
	private MainView mainView;

	/**
	 * Getter of the property <tt>mainView</tt>
	 * @return  Returns the mainView.
	 * @uml.property  name="mainView"
	 */
	public MainView getMainView() {
		return mainView;
	}

	/**
	 * Setter of the property <tt>mainView</tt>
	 * @param mainView  The mainView to set.
	 * @uml.property  name="mainView"
	 */
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
}
