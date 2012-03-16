package cs.c301.project;

import java.io.File;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;


/**
 * @uml.dependency   supplier="model.Photo"
 */
public class PhotoSubView extends Activity implements PhotoModelListener {
	//private String folderName = "tmp";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_list);
		
		Bundle extra = getIntent().getExtras();
		
		String filepath = extra.getString("path"); //grabbing the file path, should be stored as an absolute path
		//String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp/";
		/*
		TextView title = new TextView(this);
		title = (TextView)findViewById(R.id.sub_group);
		title.setText(filepath);*/
		
		//Create an array of our photos
		File file = new File(filepath);
		TextView tv = (TextView)findViewById(R.id.sub_group);
		tv.setText(file.getName());
		
		BitmapArrayController imageBmp = new BitmapArrayController(filepath);
		String[] imagePaths = imageBmp.getPaths();
		Bitmap[] bmpArray = imageBmp.imageGallery(imagePaths);
		
		GridView gridview = (GridView) findViewById(R.id.sub_list);
	    gridview.setAdapter(new ImageAdapter(this, bmpArray));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	
	        }
	    });

/*
* TODO
* 	A lot of things will have to be moved around
* 	Likely that our bmp array will have to be moved out of the adapter into a controller
* 	to work properly. Also it is untested and some of the code was taken from the
* 	gridview tutorial, so there may be other issues to work out.
* 	Linking issues, such as how will the subView be passed the folder name?
* 	Where do we add the code to populate the bmp array?
*/
	}

	public void photosChanged(Vector<PhotoEntry> photos) {
		// TODO Auto-generated method stub
		
	}

	public void tagsChanged(Vector<String> tags) {
		// TODO Auto-generated method stub
		
	}

	public void groupsChanged(Vector<String> groups) {
		// TODO Auto-generated method stub
		
	}

}
