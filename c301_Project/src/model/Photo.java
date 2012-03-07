package model;

import java.util.Collection;
import view.PartsListView;
import view.PhotoReview;
import view.SearchPhoto;
import controller.PhotoController;


public class Photo implements FModel {

	/**
	 * @uml.property  name="tag"
	 */
	private String tag;

	/**
	 * Getter of the property <tt>tag</tt>
	 * @return  Returns the tag.
	 * @uml.property  name="tag"
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Setter of the property <tt>tag</tt>
	 * @param tag  The tag to set.
	 * @uml.property  name="tag"
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @uml.property  name="photoList"
	 * @uml.associationEnd  inverse="photo:model.PhotoList"
	 */
	private PhotoList photoList;

	/**
	 * Getter of the property <tt>photoList</tt>
	 * @return  Returns the photoList.
	 * @uml.property  name="photoList"
	 */
	public PhotoList getPhotoList() {
		return photoList;
	}

	/**
	 * Setter of the property <tt>photoList</tt>
	 * @param photoList  The photoList to set.
	 * @uml.property  name="photoList"
	 */
	public void setPhotoList(PhotoList photoList) {
		this.photoList = photoList;
	}

	/**
	 * @uml.property  name="partsListView"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="photo:view.PartsListView"
	 */
	private Collection<PartsListView> partsListView;

	/**
	 * Getter of the property <tt>partsListView</tt>
	 * @return  Returns the partsListView.
	 * @uml.property  name="partsListView"
	 */
	public Collection<PartsListView> getPartsListView() {
		return partsListView;
	}

	/**
	 * Setter of the property <tt>partsListView</tt>
	 * @param partsListView  The partsListView to set.
	 * @uml.property  name="partsListView"
	 */
	public void setPartsListView(Collection<PartsListView> partsListView) {
		this.partsListView = partsListView;
	}

	/**
	 * @uml.property  name="partsListView1"
	 * @uml.associationEnd  inverse="photo1:view.PartsListView"
	 */
	private PartsListView partsListView1;

	/**
	 * Getter of the property <tt>partsListView1</tt>
	 * @return  Returns the partsListView1.
	 * @uml.property  name="partsListView1"
	 */
	public PartsListView getPartsListView1() {
		return partsListView1;
	}

	/**
	 * Setter of the property <tt>partsListView1</tt>
	 * @param partsListView1  The partsListView1 to set.
	 * @uml.property  name="partsListView1"
	 */
	public void setPartsListView1(PartsListView partsListView1) {
		this.partsListView1 = partsListView1;
	}

	/**
	 * @uml.property  name="photoReview"
	 * @uml.associationEnd  inverse="photo:view.PhotoReview"
	 */
	private PhotoReview photoReview;

	/**
	 * Getter of the property <tt>photoReview</tt>
	 * @return  Returns the photoReview.
	 * @uml.property  name="photoReview"
	 */
	public PhotoReview getPhotoReview() {
		return photoReview;
	}

	/**
	 * Setter of the property <tt>photoReview</tt>
	 * @param photoReview  The photoReview to set.
	 * @uml.property  name="photoReview"
	 */
	public void setPhotoReview(PhotoReview photoReview) {
		this.photoReview = photoReview;
	}

	/**
	 * @uml.property  name="searchPhoto"
	 * @uml.associationEnd  inverse="photo:view.SearchPhoto"
	 */
	private SearchPhoto searchPhoto;

	/**
	 * Getter of the property <tt>searchPhoto</tt>
	 * @return  Returns the searchPhoto.
	 * @uml.property  name="searchPhoto"
	 */
	public SearchPhoto getSearchPhoto() {
		return searchPhoto;
	}

	/**
	 * Setter of the property <tt>searchPhoto</tt>
	 * @param searchPhoto  The searchPhoto to set.
	 * @uml.property  name="searchPhoto"
	 */
	public void setSearchPhoto(SearchPhoto searchPhoto) {
		this.searchPhoto = searchPhoto;
	}

	/**
	 * @uml.property  name="photoController"
	 * @uml.associationEnd  inverse="photo1:controller.PhotoController"
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
