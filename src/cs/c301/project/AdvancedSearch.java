package cs.c301.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * @author yhu3
 */
public class AdvancedSearch extends Activity {
	
	private Date startDate, endDate;
	private boolean startSelected, endSelected;
	private static final int START_DATE_DIALOG_ID = 199, END_DATE_DIALOG_ID = 200;
	private DatePickerDialog.OnDateSetListener startDateListener, endDateListener;
	private Spinner tagsSpinner, groupSpinner;
	
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adv_search);
		
		startSelected = false;
		endSelected = false;
		
		groupSpinner = (Spinner)findViewById(R.id.groupSpinner);
		tagsSpinner = (Spinner)findViewById(R.id.tagSpinner);
		
		List<String> groupList = new ArrayList<String>();
		Vector<String> groups = PhotoApplication.getGroups();
		
		for (int i = 0; i < groups.size(); i++) {
			groupList.add(groups.elementAt(i));
		}
		
		ArrayAdapter<String> groupsAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, groupList);
		groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		groupSpinner.setAdapter(groupsAdapter);
		
		List<String> tagsList = new ArrayList<String>();
		Vector<String> tags = PhotoApplication.getTags();
		
		for (int i = 0; i < tags.size(); i++) {
			tagsList.add(tags.elementAt(i));
		}
		
		if (tags.size() == 0)
			tagsSpinner.setEnabled(false);
		
		ArrayAdapter<String> tagsAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, tagsList);
		tagsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tagsSpinner.setAdapter(tagsAdapter);
		
		
		//date stuff
		final Button pickStartDate = (Button)findViewById(R.id.startDate);
		final Button pickEndDate = (Button)findViewById(R.id.endDate);
		
		startDate = new Date();
		endDate = new Date();
		
		startDateListener = new DatePickerDialog.OnDateSetListener() {
	                public void onDateSet(DatePicker view, int year, 
	                                      int monthOfYear, int dayOfMonth) {
	                	Date tempDate = new Date();
	                	tempDate.setYear(year);
	                	tempDate.setDate(dayOfMonth);
	                	tempDate.setMonth(monthOfYear);
	                	
	                	if (tempDate.compareTo(endDate) <= 0) {
		                    pickStartDate.setText(new StringBuilder()
		                    // Month is 0 based so add 1
		                    .append(getMonth(monthOfYear + 1)).append(" ")
		                    .append(dayOfMonth).append(",")
		                    .append(year));
		                    startDate = tempDate;
		                    startSelected = true;
	                	} else {
	                		Toast.makeText(getApplicationContext(), "The start date cannot be after the end date.", Toast.LENGTH_SHORT).show();
	                	}
	                }
	            };
	            
        endDateListener = new DatePickerDialog.OnDateSetListener() {
	                public void onDateSet(DatePicker view, int year, 
	                                      int monthOfYear, int dayOfMonth) {
	                	Date tempDate = new Date();
	                	tempDate.setYear(year);
	                	tempDate.setDate(dayOfMonth);
	                	tempDate.setMonth(monthOfYear);
	                	
	                	if (tempDate.compareTo(endDate) > 0) {
		                    pickEndDate.setText(new StringBuilder()
		                    // Month is 0 based so add 1
		                    .append(getMonth(monthOfYear + 1)).append(" ")
		                    .append(dayOfMonth).append(",")
		                    .append(year));
		                    endDate = tempDate;
		                    endSelected = true;
	                	} else {
	                		Toast.makeText(getApplicationContext(), "The end date cannot be before the start date.", Toast.LENGTH_SHORT).show();
	                	}
	                }
	            };
		
	    pickStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);
            }
        });
	    
	    pickEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);
            }
        });
	    
		//submission needs to be cleaned up for nulls
		Button searchButton = (Button) findViewById(R.id.adv_search);
		searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean submit = true;
				Intent intent = new Intent(AdvancedSearch.this, PhotoSubView.class);
				String group = String.valueOf(groupSpinner.getSelectedItem());
				
				if (groupSpinner.getSelectedItemPosition() != Spinner.INVALID_POSITION) {
					intent.putExtra("group", group);
				}
				
				String tag = String.valueOf(tagsSpinner.getSelectedItem());
				
				if (tagsSpinner.getSelectedItemPosition() != Spinner.INVALID_POSITION) {
					intent.putExtra("tag", tag);
				}
				
				if ((startSelected && !endSelected) || (!startSelected && endSelected)) {
					submit = false;
					Toast.makeText(getApplicationContext(), "Please make sure both dates are selected.", Toast.LENGTH_SHORT).show();
				} else {
					intent.putExtra("startDate", startDate);
					intent.putExtra("endDate", endDate);
				}
				
				if (tagsSpinner.getSelectedItemPosition() != Spinner.INVALID_POSITION && groupSpinner.getSelectedItemPosition() != Spinner.INVALID_POSITION && (!startSelected && !endSelected)) {
					submit = false;
					Toast.makeText(getApplicationContext(), "Please select a search query.", Toast.LENGTH_SHORT).show();
				}
					
				if (submit)
				startActivity(intent);
			}
			
		});
		
		if (groups.size() == 0) {
			groupSpinner.setEnabled(false);
			searchButton.setEnabled(false);
			pickStartDate.setEnabled(false);
			pickEndDate.setEnabled(false);
		}
	}
	
	public String getMonth(int month) {
		switch (month) {
			case 1:
				return "January";
			case 2:
				return "February";
			case 3:
				return "March";
			case 4:
				return "April";
			case 5:
				return "May";
			case 6:
				return "June";
			case 7:
				return "July";
			case 8:
				return "August";
			case 9:
				return "September";
			case 10:
				return "October";
			case 11:
				return "November";
			case 12:
				return "December";
		}
		
		return null;
	}
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case START_DATE_DIALOG_ID:
	        return new DatePickerDialog(this,
	        		startDateListener,
	                    startDate.getYear() + 1900, startDate.getMonth(), startDate.getDate());
	    case END_DATE_DIALOG_ID:
	        return new DatePickerDialog(this,
	        		endDateListener,
	        		endDate.getYear() + 1900, endDate.getMonth(), endDate.getDate());
	    }
	    return null;
	}
}
