package cs.c301.project;

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
import android.view.Window;
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
import cs.c301.project.Utilities.GroupListLayout;

/**
 * GroupList gets all photo groups, the body parts, and creates
 * a set list for the user to choose from.  Each photo should be listed
 * under a group.
 * <p>
 * Also allows the addition of new groups, which are immediately added to the list.
 * 
 * @author yhu3
 */
public class GroupList extends Activity {
	
	private Vector<Integer> matchingPositions;
	private boolean isUnderReview, isInFilterState;
	private ListView lv;
	private TextWatcher filterWatcher;
	private String filterString;
	private Vector<String> groups;
	private boolean isPatient;
	
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.grouplist);
		
		groups = PhotoApplication.getGroups();
		
		dialogs = new WeakHashMap<Integer, AlertDialog.Builder>();
		filterString = "";
		isInFilterState = false;
		matchingPositions = new Vector<Integer>(0, 1);
		
		//isUnderReview should be stored in this Bundle
		Bundle extra = getIntent().getExtras();

		try {
			isUnderReview = extra.getBoolean("isUnderReview");
		}

		catch (Exception e) {
			isUnderReview = false;
		}
		
		try {
			isPatient = extra.getBoolean("isPatient");
		}

		catch (Exception e) {
			isPatient = false;
		}


		lv = (ListView)findViewById(R.id.groupListView);
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!isUnderReview) {
					//start a new activity with the file path of the subgroup in the intent
					Intent intent = new Intent(view.getContext(), PhotoSubView.class);
					intent.putExtra("group", groups.elementAt(matchingPositions.elementAt(position)));
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
					intent.putExtra("group", groups.elementAt(matchingPositions.elementAt(position)));
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
				Intent intent = new Intent(view.getContext(), AdvancedSearch.class);
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
		
		if (isPatient) {
			LinearLayout panel = (LinearLayout)findViewById(R.id.groupButtonPaneWrapper);
			panel.setVisibility(View.INVISIBLE);
		}
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
				String newGroupName = inputName.getText().toString().trim();
				boolean isSuccessful = PhotoApplication.addGroup(newGroupName);

				if (isSuccessful) { //test to see if the group adding was successful
					onStart();

					Toast.makeText(getApplicationContext(), newGroupName + " has been successfully added to the group list.", Toast.LENGTH_SHORT).show();
				}
				else {
					//notify user that it already exists
					Toast.makeText(getApplicationContext(), newGroupName + " already exists!", Toast.LENGTH_SHORT).show();
				}
			}
		});

    	newGroupDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			//do nothing because user canceled
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
		
		groups = PhotoApplication.getGroups();
		
		if (!filterString.equals("") && filterString != null) {
			Vector<String> matching = new Vector<String>(0, 1);
			matchingPositions.removeAllElements();
			matchingPositions.trimToSize();
			
			for (int i = 0; i < groups.size(); i++) {
				if (groups.elementAt(i).toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
					matching.add(groups.elementAt(i));
					matchingPositions.add(i);
				}
			}
			
			String[] names = new String[matching.size()];
			
			for (int j = 0; j < matching.size(); j++) {
				names[j] = matching.elementAt(j);
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);

			lv.setAdapter(adapter);
		} else {

			matchingPositions.removeAllElements();
			matchingPositions.trimToSize();
			
			for (int i = 0; i < groups.size(); i++) {
				matchingPositions.add(i);
			}
			
			String[] names = new String[groups.size()];
			
			for (int j = 0; j < groups.size(); j++) {
				names[j] = groups.elementAt(j);
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);

			lv.setAdapter(adapter);
		}
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
