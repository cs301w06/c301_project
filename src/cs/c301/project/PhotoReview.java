package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoReview extends Activity implements FView {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);

		/* Review of photo */
		final String folderTmp = "tmp";
		final String fileTemp = "temp";
		ImageView reviewPhoto = (ImageView) findViewById(R.id.review_photo);
		reviewPhoto.setImageDrawable(Drawable.createFromPath(Photo.getPhoto(folderTmp, fileTemp).getPath()));

		/* Comparasion photo */
		ImageView comparePhoto = (ImageView) findViewById(R.id.review_photoCompare);

		/* Discard button */
		Button discardButton = (Button) findViewById(R.id.review_disc);
		discardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

			}

		});

		/* Select group and keep button */
		final Button keepButton = (Button) findViewById(R.id.review_keep);
		keepButton.setText("Select Group");
		keepButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO select group button
				String folderPath = "Group"; // Placeholder for now, will replace with actual group
				String filePath = "Name";
				Photo.savePhoto(folderPath, filePath);
				Photo.deletePhoto(folderTmp, fileTemp);
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
