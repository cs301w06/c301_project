package cs.c301.project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.view.View;


/**
 * @uml.dependency   supplier="model.Photo"
 */
public class PhotoSubView extends Activity implements FView {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_list);
		
		GridView gridview = (GridView) findViewById(R.id.sub_list);
	    gridview.setAdapter(new ImageAdapter(this));

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
	
	/**
	 * @uml.property  name="photoController"
	 * @uml.associationEnd  inverse="photoSubView:controller.PhotoController"
	 */
	private PhotoController photoController;

	/**
	 * Getter of the property <tt>photoController</tt>
	 * @return  Returns the photoController.
	 * @uml.property  name="photoController"
	 */
	public PhotoController getPhotoController() {
		return photoController;
	}

	/**
	 * Setter of the property <tt>photoController</tt>
	 * @param photoController  The photoController to set.
	 * @uml.property  name="photoController"
	 */
	public void setPhotoController(PhotoController photoController) {
		this.photoController = photoController;
	}

	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  inverse="photoSubView:model.Photo"
	 */
	private Photo photo;

	/**
	 * Getter of the property <tt>photo</tt>
	 * @return  Returns the photo.
	 * @uml.property  name="photo"
	 */
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * Setter of the property <tt>photo</tt>
	 * @param photo  The photo to set.
	 * @uml.property  name="photo"
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

}
