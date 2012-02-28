package model;

import java.util.Collection;


public class PhotoList {

	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="photoList:model.Photo"
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

}
