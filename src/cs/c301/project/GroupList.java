package cs.c301.project;

import java.io.File;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cs.c301.project.Utilities.DirectoryFilter;

public class GroupList extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//the filepath for the storage path is stored in this intent
		Bundle extra = getIntent().getExtras();
		
		String filepath = extra.getString("path"); //grabbing the file path, should be stored as an absolute path
		File location = new File(filepath); //creates the file object with path for manipulation
		final File[] items = location.listFiles(new DirectoryFilter()); //pulls all the directories 
		
		String[] names = new String[items.length];
		
		for (int i = 0; i < items.length; i++) {
			names[i] = items[i].getName();
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.grouplist, names));
		
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
	}
}
