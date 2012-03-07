package view;

import controller.PhotoController;


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

}
