package cs.c301.project;

import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cs.c301.project.Data.PhotoEntry;

/**
 * PhotoSubView is the activity class and view for listing the photos
 * within a given group folder. It is given search parameters by the
 * calling activity, and it searches the databases for any photos with
 * those terms contained within. A grid view is then populated with
 * the bitmaps of the photos. The photos can be selected which will redirect
 * to a detail view showing meta data and options for the photo.
 * <p>
 * Also allows for selection of multiple photos at once when the isMultiSelected
 * 
 * boolean value is true.
 * @author esteckle
 * 
 * Significant bug fixes by yhu3
 *
 */
public class PhotoSubView extends Activity {

	private Bitmap[] bmpArray;
	private Vector<Integer> multiselect;
	private Vector<PhotoEntry> photos;
	private Vector<PhotoEntry> multiPhotos;
	private Vector<String> groupV;
	private Vector<String> tagsV;
	private GridView gridview;
	private String tags;
	private String group;
	private Date startDate, endDate;
	private boolean isMultiSelected;
	
	/**
	 * onCreate method is called when the activity starts. It initializes the grid view and
	 * populates it with our images from a given set of search terms.
	 * 
	 * @param savedInstanceState The instance state from the calling activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sub_list);
		
		Bundle extra = getIntent().getExtras();
		
		group = null;
		tags = null;
		
		group = extra.getString("group");
		tags = extra.getString("tag");
		
		try {
			startDate = (Date)extra.getSerializable("startDate");
			endDate = (Date)extra.getSerializable("endDate");
			Log.e("PhotoSubView", "dates " + startDate.toString() + ", " + endDate.toString());
		}
		
		catch (Exception e) {
			startDate = null;
			endDate = null;
		}
		
		groupV = new Vector<String>();
		tagsV = new Vector<String>();
		
		multiPhotos = new Vector<PhotoEntry>();
		multiselect = new Vector<Integer>();
		
		if (group != null)
		groupV.add(group);
		
		if (tags != null)
		tagsV.add(tags);
		
		isMultiSelected = extra.getBoolean("isMultiSelected");

		TextView tv = (TextView)findViewById(R.id.sub_group);
		
		if (group != null)
		tv.setText(group);
		else if (tags != null)
		tv.setText(tags);
		else {
			tv.setText("Search Result");
		}
		
		gridview = (GridView) findViewById(R.id.sub_list);

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	
	        	ImageView imageview = (ImageView) v;
	        	
	        	if (!isMultiSelected){
	        		Intent intent = new Intent(PhotoSubView.this, PhotoDetails.class);	        		
					intent.putExtra("photo", photos.elementAt(position).getID());
					startActivity(intent);
	        	}
	        	else{
	        		int matcher = -1;
	        		int pos = 0;
	        		for (int i = 0; i < multiselect.size(); i++){
	        			if (position == multiselect.elementAt(i)){
	        				matcher = position;
	        				pos = i;
	        				continue;
	        			}
	        		}
	        		if (matcher == position){
	        			multiselect.removeElementAt(pos);
	        			multiPhotos.removeElementAt(pos);
		        	
	        			imageview.setAlpha(255);
	        			imageview.setPadding(0, 0, 0, 0);
	        		}
	        		else{
	        			multiselect.add(position);
	        			multiPhotos.add(photos.elementAt(position));

	        			imageview.setAlpha(150);
	        			imageview.setPadding(8, 8, 8, 8);   
	        		}
	        	}
	        }
	    });
	}
	
	@Override
	/**
	 * Refreshes the photo list every time we return to this activity.
	 * 
	 * @see android.app.Activity#onStart()
	 */
	protected void onStart() {
		super.onStart();
		photos = PhotoApplication.getPhotosByValues(groupV, tagsV, startDate, endDate);
		bmpArray = new Bitmap[photos.size()];
		
		for (int i = 0; i < photos.size(); i++){
			bmpArray[i] = photos.elementAt(i).getThumbnail();
		}

	    gridview.setAdapter(new ImageAdapter(this, bmpArray));
	}
}
