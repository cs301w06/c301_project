package cs.c301.project;

import java.util.Vector;
import java.util.WeakHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import cs.c301.project.Utilities.PatientListLayout;

/**
 * patientList gets all photo patients, the body parts, and creates
 * a set list for the user to choose from.  Each photo should be listed
 * under a patient.
 * <p>
 * Also allows the addition of new patients, which are immediately added to the list.
 * 
 * @author yhu3
 */
public class PatientList extends Activity {
	
	private Vector<Integer> matchingPositions;
	private boolean isUnderReview, isInFilterState;
	private ListView listView;
	private TextWatcher filterWatcher;
	private String filterString;
	private Vector<String> patients;
	
	public WeakHashMap<Integer, AlertDialog.Builder> dialogs;

	/**
	 * Method called upon creation of the activity. Checks to see if the activity has been
	 * called from the camera (for save patient selection) or main menu (browse patient). Populates
	 * the list with all folders found, and allows selection of folders from the list. Selection of
	 * a folder allows browsing of it's contents.
	 * <p>
	 * Also allows addition of new patients.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.patientlist);
		
		patients = PhotoApplication.getPatients();
		
		dialogs = new WeakHashMap<Integer, AlertDialog.Builder>();
		filterString = "";
		isInFilterState = false;
		matchingPositions = new Vector<Integer>(0, 1);
		
		listView = (ListView)findViewById(R.id.patientListView);
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//start a new activity with the file path of the subpatient in the intent
				Intent intent = new Intent(view.getContext(), MainView.class);
				intent.putExtra("patientName", patients.elementAt(matchingPositions.elementAt(position)));
				intent.putExtra("isPatient", true);
				PhotoApplication.login(patients.elementAt(matchingPositions.elementAt(position)), null);
				PhotoApplication.toggleDoctor();
				
				startActivity(intent);
				
				InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				EditText filterEditText = (EditText) findViewById(R.id.patientEditText);
				inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
			}
		});
		
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

		final EditText filterEditText = (EditText) findViewById(R.id.patientEditText);
		filterEditText.addTextChangedListener(filterWatcher);
		
		filterEditText.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				LinearLayout panel = (LinearLayout)findViewById(R.id.patientTextViewWrapper);
				LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0);
				panel.setLayoutParams(layoutParameters);
				
				isInFilterState = true;
			}
		});
		
		filterEditText.setOnFocusChangeListener(new OnFocusChangeListener() {          
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (hasFocus) {
	            	LinearLayout panel = (LinearLayout)findViewById(R.id.patientTextViewWrapper);
					LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0);
					panel.setLayoutParams(layoutParameters);
					
					isInFilterState = true;
					
					InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.toggleSoftInputFromWindow(panel.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
	            } else {
	            	LinearLayout panel = (LinearLayout)findViewById(R.id.patientTextViewWrapper);
	    	    	LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    			panel.setLayoutParams(layoutParameters);
	    			
	    			isInFilterState = false;
	    			
	    			InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    			EditText filterEditText = (EditText) findViewById(R.id.patientEditText);
	    			inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
	            }
            }
	    });
		
		PatientListLayout.setFilterActivity(this);

	}	

	/** 
	 * Overrides the existing onStart command and pulls the directories
	 */
	@Override
	protected void onStart() {
		super.onStart();
		
		patients = PhotoApplication.getPatients();
		
		if (!filterString.equals("") && filterString != null) {
			Vector<String> matching = new Vector<String>(0, 1);
			matchingPositions.removeAllElements();
			matchingPositions.trimToSize();
			
			for (int i = 0; i < patients.size(); i++) {
				if (patients.elementAt(i).toLowerCase().indexOf(filterString.toLowerCase()) != -1) {
					matching.add(patients.elementAt(i));
					matchingPositions.add(i);
				}
			}
			
			String[] names = new String[matching.size()];
			
			for (int j = 0; j < matching.size(); j++) {
				names[j] = matching.elementAt(j);
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);

			listView.setAdapter(adapter);
		} else {

			matchingPositions.removeAllElements();
			matchingPositions.trimToSize();
			
			for (int i = 0; i < patients.size(); i++) {
				matchingPositions.add(i);
			}
			
			String[] names = new String[patients.size()];
			
			for (int j = 0; j < patients.size(); j++) {
				names[j] = patients.elementAt(j);
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, names);

			listView.setAdapter(adapter);
		}
	}

	@Override
	public void onBackPressed() {
		if (isInFilterState) {
	    	LinearLayout panel = (LinearLayout)findViewById(R.id.patientTextViewWrapper);
	    	LinearLayout.LayoutParams layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			panel.setLayoutParams(layoutParameters);
			
			isInFilterState = false;
			
			InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			EditText filterEditText = (EditText) findViewById(R.id.patientEditText);
			inputMethodManager.hideSoftInputFromWindow(filterEditText.getWindowToken(), 0);
	    }
		else
			super.onBackPressed();
	}
}
