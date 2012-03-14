package cs.c301.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoReview extends Activity implements FView {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);
		
		/* ImageView of photo */
		ImageView reviewPhoto = (ImageView) findViewById(R.id.rev_photo);
		reviewPhoto.setImageDrawable(Drawable.createFromPath(Photo.getPhoto("tmp", "temp").getPath()));
		/* Discard button */
		Button discardButton = (Button) findViewById(R.id.rev_disc);
		discardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO delete photo from sd card
			}
			
		});
		
		/* Select group and keep button */
		final Button keepButton = (Button) findViewById(R.id.rev_keep);
		keepButton.setText("Select Group");
		keepButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO select group button
				// TODO save the changes to a good place
				keepButton.setText("Keep");
			}
		});
	}
	
	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  inverse="photoReview:model.Photo"
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

	/**
	 * @uml.property  name="photoController"
	 * @uml.associationEnd  inverse="photoReview:controller.PhotoController"
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
