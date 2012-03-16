package cs.c301.project;

import java.util.Vector;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;
import android.app.Activity;
import android.os.Bundle;

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

	/**
	 * @uml.property  name="mainView"
	 * @uml.associationEnd  inverse="searchPhotoView:cs.c301.project.MainView"
	 */
	private MainView mainView;

	/**
	 * Getter of the property <tt>mainView</tt>
	 * @return  Returns the mainView.
	 * @uml.property  name="mainView"
	 */
	public MainView getMainView() {
		return mainView;
	}

	/**
	 * Setter of the property <tt>mainView</tt>
	 * @param mainView  The mainView to set.
	 * @uml.property  name="mainView"
	 */
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

}

