package view;

import controller.PhotoController;
import model.Photo;


/**
 * @uml.dependency   supplier="model.Photo"
 */
public class PhotoSubView implements FView {

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
