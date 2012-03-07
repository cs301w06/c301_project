package controller;

import java.util.Collection;
import model.Photo;
import view.MainView;
import view.SearchPhotoResult;


public class searchphoto implements FController {

	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="searchphoto:model.Photo"
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
	 * @uml.property  name="mainView"
	 * @uml.associationEnd  inverse="searchphoto:view.MainView"
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

	/**
	 * @uml.property  name="searchPhotoResult"
	 * @uml.associationEnd  inverse="searchphoto:view.SearchPhotoResult"
	 */
	private SearchPhotoResult searchPhotoResult;

	/**
	 * Getter of the property <tt>searchPhotoResult</tt>
	 * @return  Returns the searchPhotoResult.
	 * @uml.property  name="searchPhotoResult"
	 */
	public SearchPhotoResult getSearchPhotoResult() {
		return searchPhotoResult;
	}

	/**
	 * Setter of the property <tt>searchPhotoResult</tt>
	 * @param searchPhotoResult  The searchPhotoResult to set.
	 * @uml.property  name="searchPhotoResult"
	 */
	public void setSearchPhotoResult(SearchPhotoResult searchPhotoResult) {
		this.searchPhotoResult = searchPhotoResult;
	}

}
