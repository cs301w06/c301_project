package cs.c301.project;

import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class SearchPhoto extends Activity implements FView {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		/* Camera button to direct to CameraView page */
		Button backButton = (Button) findViewById(R.id.search_back);
		backButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SearchPhoto.this, MainView.class);
				startActivity(intent);				
			}

		});
	}
	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="searchPhoto:model.Photo"
	 */
	private Collection<Photo> photo;

	/**
	 * Getter of the property <tt>photo</tt>
	 * @return  Returns the photo.
	 * @uml.property  name="photo"
	 */
	public Collection<Photo> getPhoto() {
		return photo;
	}

	/**
	 * Setter of the property <tt>photo</tt>
	 * @param photo  The photo to set.
	 * @uml.property  name="photo"
	 */
	public void setPhoto(Collection<Photo> photo) {
		this.photo = photo;
	}

	/**
	 * @uml.property  name="photoController"
	 * @uml.associationEnd  inverse="searchPhoto1:controller.PhotoController"
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

}

