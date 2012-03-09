package cs.c301.project;

import java.util.Collection;


/**
 * @uml.dependency   supplier="view.FView"
 */
public class PartsListView implements FView {

	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  inverse="partsListView:model.Photo"
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
	 * @uml.property  name="photo1"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="partsListView1:model.Photo"
	 */
	private Collection<Photo> photo1;

	/**
	 * Getter of the property <tt>photo1</tt>
	 * @return  Returns the photo1.
	 * @uml.property  name="photo1"
	 */
	public Collection<Photo> getPhoto1() {
		return photo1;
	}

	/**
	 * Setter of the property <tt>photo1</tt>
	 * @param photo1  The photo1 to set.
	 * @uml.property  name="photo1"
	 */
	public void setPhoto1(Collection<Photo> photo1) {
		this.photo1 = photo1;
	}

	/**
	 * @uml.property  name="photoController"
	 * @uml.associationEnd  inverse="partsListView:controller.PhotoController"
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
