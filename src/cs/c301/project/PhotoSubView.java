package cs.c301.project;

import java.io.File;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cs.c301.project.Data.PhotoEntry;

/**
 * PhotoSubView is the activity class and view for listing the images
 * within a given group folder. It is passed the file path of the folder,
 * and then passes the path to a controller. The controller creates an
 * array of the images within the folder in the form of bitmaps. It then
 * calls the image adapter to put the images in the array into the grid view.
 * 
 * @author esteckle
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
	private boolean isMultiSelected;
	
	/**
	 * onCreate method is called when the activity starts. It initializes the grid view and
	 * populates it with our images from a given folder. 
	 * 
	 * @param savedInstanceState The instance state from the calling activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sub_list);
		
		Bundle extra = getIntent().getExtras();
		
		group = extra.getString("group");
		tags = extra.getString("tag");
		Boolean advSearch = extra.getBoolean("advsearch");
		
		groupV = new Vector<String>();
		tagsV = new Vector<String>();
		
		multiPhotos = new Vector<PhotoEntry>();
		multiselect = new Vector<Integer>();
		
		groupV.add(group);
		tagsV.add(tags);
		
		isMultiSelected = extra.getBoolean("isMultiSelected");
	
		
		photos = PhotoApplication.getPhotosByValues(groupV, tagsV);

		if (advSearch)
			advSearch();
		
		TextView tv = (TextView)findViewById(R.id.sub_group);
		tv.setText(group);
		
		bmpArray = new Bitmap[photos.size()];
		
		for (int i = 0; i < photos.size(); i++){
			bmpArray[i] = photos.elementAt(i).getBitmap();
		}

		gridview = (GridView) findViewById(R.id.sub_list);

	    gridview.setAdapter(new ImageAdapter(this, bmpArray));
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
	
	protected void onStart() {
		super.onStart();
		photos = PhotoApplication.getPhotosByValues(groupV, tagsV);
		bmpArray = new Bitmap[photos.size()];
		
		for (int i = 0; i < photos.size(); i++){
			bmpArray[i] = photos.elementAt(i).getBitmap();
		}

		gridview = (GridView) findViewById(R.id.sub_list);

	    gridview.setAdapter(new ImageAdapter(this, bmpArray));
	}
	
	private void advSearch(){
		Vector<PhotoEntry> tempPhotos = new Vector<PhotoEntry>();
		for (int i = 0; i < photos.size(); i++){
			if (photos.elementAt(i).getGroup() == group && photos.elementAt(i).getTagsForDatabase() == tags)
				tempPhotos.add(photos.elementAt(i));		
		}
	}
}
