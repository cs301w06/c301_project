package cs.c301.project;

import java.util.Vector;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;
import android.app.Activity;
import android.os.Bundle;

/**
 * SearchPhotoView will allow the user to search photo by tag
 * group and date of the photo. Currently not implemented and used
 * as a place holder.  Will be completed for part 4.
 */
public class SearchPhotoView extends Activity implements PhotoModelListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		/* TODO: Link to advanced search screen */
	}
	
	/**
	 * @see cs.c301.project.Listeners.PhotoModelListener#photosChanged(Vector)
	 */
	public void photosChanged(Vector<PhotoEntry> photos) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see cs.c301.project.Listeners.PhotoModelListener#tagsChanged(Vector)
	 */
	public void tagsChanged(Vector<String> tags) {
		// TODO Auto-generated method stub
		
	}

	/** 
	 * @see cs.c301.project.Listeners.PhotoModelListener#groupsChanged(Vector)
	 */
	public void groupsChanged(Vector<String> groups) {
		// TODO Auto-generated method stub
		
	}
}

