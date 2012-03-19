package cs.c301.project;

import java.io.File;
import java.util.Vector;
import java.util.WeakHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;
import cs.c301.project.Utilities.DirectoryFilter;
import cs.c301.project.Utilities.GroupListLayout;

/**
 * GroupList gets all photo groups, the body parts, and creates
 * a set list for the user to choose from.  Each photo should be listed
 * under a group.
 * <p>
 * Also allows the addition of new groups, which are immediately added to the list.
 */
public class GroupList extends Activity implements PhotoModelListener {
	
	private File location;
	private File[] items;
	private Vector<Integer> matchingPositions;
	private boolean isUnderReview, isInFilterState;
	private ListView lv;
	private TextWatcher filterWatcher;
	private String filterString;
	
	public WeakHashMap<Integer, AlertDialog.Builder> dialogs;

	/**
	 * Method called upon creation of the activity. Checks to see if the activity has been
	 * called from the camera (for save group selection) or main menu (browse group). Populates
	 * the list with all folders found, and allows selection of folders from the list. Selection of
	 * a folder allows browsing of it's contents.
	 * <p>
	 * Also allows addition of new groups.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grouplist);
		
		dialogs = new WeakHashMap<Integer, AlertDialog.Builder>();
		filterString = "";
		isInFilterState = false;
		matchingPositions = new Vector<Integer>(0, 1);
		
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
			matchingPositions.add(i);
		}

		lv = (ListView)findViewById(R.id.groupListView);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);

		lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!isUnderReview) {
					//start a new activity with the file path of the subgroup in the intent
					Intent intent = new Intent(view.getContext(), PhotoSubView.class);
					intent.putExtra("path", items[matchingPositions.elementAt(position)].getAbsolutePath());
					startActivity(intent);
					
					//reset the view in case of future back buttons
					LinearLayout panel = (LinearLayout)findViewById(R.id.groupTextViewWrapper);
			    	LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					panel.setLayoutParams(layoutParameters);
					
					LinearLayout linear = (LinearLayout)findViewById(R.id.groupButtonPaneWrapper);
					linear.setLayoutParams(layoutParameters);
					
					isInFilterState = false;
					
					InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					EditText filterEditText = (EditText) findViewById(R.id.groupEditText);
					inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
				} else {
					InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					EditText filterEditText = (EditText) findViewById(R.id.groupEditText);
					inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
					
					Intent intent = new Intent();
					intent.putExtra("groupname", items[matchingPositions.elementAt(position)].getName());
					setResult(1, intent);
					finish();
				}
			}
		});

		Button addGroupButton = (Button)findViewById(R.id.addGroup);
		addGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				requestUserInput();
			}
		});

		Button searchButton = (Button)findViewById(R.id.searchGroup);
		
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), SearchPhotoView.class);
				startActivity(intent);
			}
		});
		
		//dynamically remove the search button for select group in the camera activity
		if (isUnderReview) {
			LinearLayout linear = (LinearLayout)findViewById(R.id.groupButtonPane);
			linear.removeView(addGroupButton);
			linear.removeView(searchButton);
			LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParameters.weight = 1.0f;
			linear.addView(addGroupButton, layoutParameters);
		}
		
		//filter stuff
		filterWatcher = new TextWatcher() {
		    public void afterTextChanged(Editable s){
		    	filterString = s.toString().trim();
		    	
		    	onStart();
		    }
		    
		    public void  beforeTextChanged(CharSequence s, int start, int count, int after){
		    }
		    
		    public void  onTextChanged (CharSequence s, int start, int before,int count) {
		    } 
		};

		final EditText filterEditText = (EditText) findViewById(R.id.groupEditText);
		filterEditText.addTextChangedListener(filterWatcher);
		
		filterEditText.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				LinearLayout panel = (LinearLayout)findViewById(R.id.groupTextViewWrapper);
				LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0);
				panel.setLayoutParams(layoutParameters);
				
				LinearLayout linear = (LinearLayout)findViewById(R.id.groupButtonPaneWrapper);
				linear.setLayoutParams(layoutParameters);
				
				isInFilterState = true;
			}
		});
		
		filterEditText.setOnFocusChangeListener(new OnFocusChangeListener() {          
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (hasFocus) {
	            	LinearLayout panel = (LinearLayout)findViewById(R.id.groupTextViewWrapper);
					LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0);
					panel.setLayoutParams(layoutParameters);
					
					LinearLayout linear = (LinearLayout)findViewById(R.id.groupButtonPaneWrapper);
					linear.setLayoutParams(layoutParameters);
					
					isInFilterState = true;
					
					InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInputFromWindow(panel.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
	            } else {
	            	LinearLayout panel = (LinearLayout)findViewById(R.id.groupTextViewWrapper);
	    	    	LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    			panel.setLayoutParams(layoutParameters);
	    			
	    			LinearLayout linear = (LinearLayout)findViewById(R.id.groupButtonPaneWrapper);
	    			linear.setLayoutParams(layoutParameters);
	    			
	    			isInFilterState = false;
	    			
	    			InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    			EditText filterEditText = (EditText) findViewById(R.id.groupEditText);
	    			inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
	            }
            }
	    });
		
		GroupListLayout.setFilterActivity(this);
	}	

	/**
	 * User must enter new group name, will check the list to see if there is 
	 * an existing group in the list already
	 */
	//TODO: all of this functionality needs to be migrated to the model
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
    	
    	dialogs.put(0, newGroupDialog);
		newGroupDialog.show();
	}

	/** 
	 * Overrides the existing onStart command and pulls the directories
	 */
	@Override
	protected void onStart() {
		super.onStart();

		location = new File(PhotoApplication.getFilePath()); //creates the file object with path for manipulation
		items = location.listFiles(new DirectoryFilter()); //pulls all the directories 
		
		String[] names = new String[items.length];
		matchingPositions.removeAllElements();
		matchingPositions.trimToSize();
		
		for (int i = 0; i < items.length; i++) {
			names[i] = items[i].getName();
			matchingPositions.add(i);
		}
		
		if (!filterString.equals("") && filterString != null) {
			Vector<String> matching = new Vector<String>(0, 1);
			matchingPositions.removeAllElements();
			matchingPositions.trimToSize();
			
			for (int i = 0; i < names.length; i++) {
				if (names[i].toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
					matching.add(names[i]);
					matchingPositions.add(i);
				}
			}
			
			names = new String[matching.size()];
			
			for (int j = 0; j < matching.size(); j++) {
				names[j] = matching.elementAt(j);
			}
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);

		lv.setAdapter(adapter);
	}

	/**
	 * @see cs.c301.project.Listeners.PhotoModelListener#photosChanged(Vector)
	 */
	public void photosChanged(Vector<PhotoEntry> photos) {
	}

	/**
	 * @see cs.c301.project.Listeners.PhotoModelListener#tagsChanged(Vector)
	 */
	public void tagsChanged(Vector<String> tags) {
	}

	/** 
	 * @see cs.c301.project.Listeners.PhotoModelListener#groupsChanged(Vector)
	 */
	public void groupsChanged(Vector<String> groups) {
		//get the group data from the listener and use data for content
	}

	@Override
	public void onBackPressed() {
		if (isInFilterState) {
	    	LinearLayout panel = (LinearLayout)findViewById(R.id.groupTextViewWrapper);
	    	LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			panel.setLayoutParams(layoutParameters);
			
			LinearLayout linear = (LinearLayout)findViewById(R.id.groupButtonPaneWrapper);
			linear.setLayoutParams(layoutParameters);
			
			isInFilterState = false;
			
			InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			EditText filterEditText = (EditText) findViewById(R.id.groupEditText);
			inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
	    }
		else
			super.onBackPressed();
	}
}
