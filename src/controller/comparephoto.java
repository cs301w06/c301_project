package controller;

import java.util.Collection;
import model.Photo;
import view.ComparePhotoResult;


public class comparephoto implements FController {

	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="comparephoto:model.Photo"
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
	 * @uml.property  name="comparePhotoResult"
	 * @uml.associationEnd  inverse="comparephoto:view.ComparePhotoResult"
	 */
	private ComparePhotoResult comparePhotoResult;

	/**
	 * Getter of the property <tt>comparePhotoResult</tt>
	 * @return  Returns the comparePhotoResult.
	 * @uml.property  name="comparePhotoResult"
	 */
	public ComparePhotoResult getComparePhotoResult() {
		return comparePhotoResult;
	}

	/**
	 * Setter of the property <tt>comparePhotoResult</tt>
	 * @param comparePhotoResult  The comparePhotoResult to set.
	 * @uml.property  name="comparePhotoResult"
	 */
	public void setComparePhotoResult(ComparePhotoResult comparePhotoResult) {
		this.comparePhotoResult = comparePhotoResult;
	}

}
