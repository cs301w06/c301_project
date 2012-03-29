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
import cs.c301.project.Utilities.TagListLayout;

/**
 * tagList gets all photo tags, the body parts, and creates
 * a set list for the user to choose from.  Each photo should be listed
 * under a tag.
 * <p>
 * Also allows the addition of new tags, which are immediately added to the list.
 */
public class TagList extends Activity implements PhotoModelListener {
	
	private Vector<Integer> matchingPositions;
	private boolean isUnderReview, isInFilterState;
	private ListView lv;
	private TextWatcher filterWatcher;
	private String filterString;
	private Vector<String> tags;
	
	public WeakHashMap<Integer, AlertDialog.Builder> dialogs;

	/**
	 * Method called upon creation of the activity. Checks to see if the activity has been
	 * called from the camera (for save tag selection) or main menu (browse tag). Populates
	 * the list with all folders found, and allows selection of folders from the list. Selection of
	 * a folder allows browsing of it's contents.
	 * <p>
	 * Also allows addition of new tags.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taglist);
		
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

		lv = (ListView)findViewById(R.id.tagListView);
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!isUnderReview) {
					//start a new activity with the file path of the subtag in the intent
					Intent intent = new Intent(view.getContext(), PhotoSubView.class);
					intent.putExtra("tag", tags.elementAt(matchingPositions.elementAt(position)));
					startActivity(intent);
					
					//reset the view in case of future back buttons
					LinearLayout panel = (LinearLayout)findViewById(R.id.tagTextViewWrapper);
			    	LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					panel.setLayoutParams(layoutParameters);
					
					LinearLayout linear = (LinearLayout)findViewById(R.id.tagButtonPaneWrapper);
					linear.setLayoutParams(layoutParameters);
					
					isInFilterState = false;
					
					InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					EditText filterEditText = (EditText) findViewById(R.id.tagEditText);
					inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
				} else {
					InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					EditText filterEditText = (EditText) findViewById(R.id.tagEditText);
					inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
					
					Intent intent = new Intent();
					intent.putExtra("tag", tags.elementAt(matchingPositions.elementAt(position)));
					setResult(1, intent);
					finish();
				}
			}
		});

		Button addtagButton = (Button)findViewById(R.id.addtag);
		addtagButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				requestUserInput();
			}
		});

		Button searchButton = (Button)findViewById(R.id.searchtag);
		
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), SearchPhotoView.class);
				startActivity(intent);
			}
		});
		
		//dynamically remove the search button for select tag in the camera activity
		if (isUnderReview) {
			LinearLayout linear = (LinearLayout)findViewById(R.id.tagButtonPane);
			linear.removeView(addtagButton);
			linear.removeView(searchButton);
			LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParameters.weight = 1.0f;
			linear.addView(addtagButton, layoutParameters);
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

		final EditText filterEditText = (EditText) findViewById(R.id.tagEditText);
		filterEditText.addTextChangedListener(filterWatcher);
		
		filterEditText.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				LinearLayout panel = (LinearLayout)findViewById(R.id.tagTextViewWrapper);
				LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0);
				panel.setLayoutParams(layoutParameters);
				
				LinearLayout linear = (LinearLayout)findViewById(R.id.tagButtonPaneWrapper);
				linear.setLayoutParams(layoutParameters);
				
				isInFilterState = true;
			}
		});
		
		filterEditText.setOnFocusChangeListener(new OnFocusChangeListener() {          
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (hasFocus) {
	            	LinearLayout panel = (LinearLayout)findViewById(R.id.tagTextViewWrapper);
					LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0);
					panel.setLayoutParams(layoutParameters);
					
					LinearLayout linear = (LinearLayout)findViewById(R.id.tagButtonPaneWrapper);
					linear.setLayoutParams(layoutParameters);
					
					isInFilterState = true;
					
					InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInputFromWindow(panel.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
	            } else {
	            	LinearLayout panel = (LinearLayout)findViewById(R.id.tagTextViewWrapper);
	    	    	LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    			panel.setLayoutParams(layoutParameters);
	    			
	    			LinearLayout linear = (LinearLayout)findViewById(R.id.tagButtonPaneWrapper);
	    			linear.setLayoutParams(layoutParameters);
	    			
	    			isInFilterState = false;
	    			
	    			InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    			EditText filterEditText = (EditText) findViewById(R.id.tagEditText);
	    			inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
	            }
            }
	    });
		
		TagListLayout.setFilterActivity(this);
		PhotoApplication.addPhotoModelListener(this);
	}	

	/**
	 * User must enter new tag name, will check the list to see if there is 
	 * an existing tag in the list already
	 */
	//TODO: all of this functionality needs to be migrated to the model
	private void requestUserInput() {
		AlertDialog.Builder newtagDialog = new AlertDialog.Builder(this);
		newtagDialog.setTitle("Add Tag");
		newtagDialog.setMessage("Please enter the new tag name:");

		final EditText inputName = new EditText(this);
		newtagDialog.setView(inputName);

		newtagDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String newtagName = inputName.getText().toString().trim();
				boolean isSuccessful = PhotoApplication.addTag(newtagName);

				if (isSuccessful) { //test to see if the tag adding was successful
					onStart();

					Toast.makeText(getApplicationContext(), newtagName + " has been successfully added to the tag list.", Toast.LENGTH_SHORT).show();
				}
				else {
					//notify user that it already exists
					Toast.makeText(getApplicationContext(), newtagName + " already exists!", Toast.LENGTH_SHORT).show();
				}
			}
		});

    	newtagDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			//do nothing because user cancelled
    		}
    	});
    	
    	dialogs.put(0, newtagDialog);
		newtagDialog.show();
	}

	/** 
	 * Overrides the existing onStart command and pulls the directories
	 */
	@Override
	protected void onStart() {
		super.onStart();
		
		if (!filterString.equals("") && filterString != null) {
			Vector<String> matching = new Vector<String>(0, 1);
			matchingPositions.removeAllElements();
			matchingPositions.trimToSize();
			
			for (int i = 0; i < tags.size(); i++) {
				if (tags.elementAt(i).toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
					matching.add(tags.elementAt(i));
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
			
			for (int i = 0; i < tags.size(); i++) {
				matchingPositions.add(i);
			}
			
			String[] names = new String[tags.size()];
			
			for (int j = 0; j < tags.size(); j++) {
				names[j] = tags.elementAt(j);
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);

			lv.setAdapter(adapter);
		}
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
		this.tags = tags;
		
		onStart();
	}

	/** 
	 * @see cs.c301.project.Listeners.PhotoModelListener#tagsChanged(Vector)
	 */
	public void groupsChanged(Vector<String> tags) {
	}

	@Override
	public void onBackPressed() {
		if (isInFilterState) {
	    	LinearLayout panel = (LinearLayout)findViewById(R.id.tagTextViewWrapper);
	    	LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			panel.setLayoutParams(layoutParameters);
			
			LinearLayout linear = (LinearLayout)findViewById(R.id.tagButtonPaneWrapper);
			linear.setLayoutParams(layoutParameters);
			
			isInFilterState = false;
			
			InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			EditText filterEditText = (EditText) findViewById(R.id.tagEditText);
			inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
	    }
		else
			super.onBackPressed();
	}
}
