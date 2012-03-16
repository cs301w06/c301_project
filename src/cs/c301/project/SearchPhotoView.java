package cs.c301.project;

import java.util.Collection;
import java.util.Vector;

import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class SearchPhotoView extends Activity implements PhotoModelListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		/* TODO: Link to advanced search screen */
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

