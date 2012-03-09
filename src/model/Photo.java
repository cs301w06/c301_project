package model;

import java.util.Collection;
import view.PartsListView;
import view.PhotoReview;
import view.SearchPhoto;
import controller.PhotoController;
import controller.setphoto;
import controller.searchphoto;
import controller.grabphoto;
import controller.comparephoto;
import view.PhotoSubView;
import view.CameraView;
import controller.TakePhoto;


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

	/**
	 * @uml.property  name="setphoto"
	 * @uml.associationEnd  inverse="photo:controller.setphoto"
	 */
	private setphoto setphoto;

	/**
	 * Getter of the property <tt>setphoto</tt>
	 * @return  Returns the setphoto.
	 * @uml.property  name="setphoto"
	 */
	public setphoto getSetphoto() {
		return setphoto;
	}

	/**
	 * Setter of the property <tt>setphoto</tt>
	 * @param setphoto  The setphoto to set.
	 * @uml.property  name="setphoto"
	 */
	public void setSetphoto(setphoto setphoto) {
		this.setphoto = setphoto;
	}

	/**
	 * @uml.property  name="searchphoto"
	 * @uml.associationEnd  inverse="photo:controller.searchphoto"
	 */
	private searchphoto searchphoto;

	/**
	 * Getter of the property <tt>searchphoto</tt>
	 * @return  Returns the searchphoto.
	 * @uml.property  name="searchphoto"
	 */
	public searchphoto getSearchphoto() {
		return searchphoto;
	}

	/**
	 * Setter of the property <tt>searchphoto</tt>
	 * @param searchphoto  The searchphoto to set.
	 * @uml.property  name="searchphoto"
	 */
	public void setSearchphoto(searchphoto searchphoto) {
		this.searchphoto = searchphoto;
	}

	/**
	 * @uml.property  name="grabphoto"
	 * @uml.associationEnd  inverse="photo:controller.grabphoto"
	 */
	private grabphoto grabphoto;

	/**
	 * Getter of the property <tt>grabphoto</tt>
	 * @return  Returns the grabphoto.
	 * @uml.property  name="grabphoto"
	 */
	public grabphoto getGrabphoto() {
		return grabphoto;
	}

	/**
	 * Setter of the property <tt>grabphoto</tt>
	 * @param grabphoto  The grabphoto to set.
	 * @uml.property  name="grabphoto"
	 */
	public void setGrabphoto(grabphoto grabphoto) {
		this.grabphoto = grabphoto;
	}

	/**
	 * @uml.property  name="comparephoto"
	 * @uml.associationEnd  inverse="photo:controller.comparephoto"
	 */
	private comparephoto comparephoto;

	/**
	 * Getter of the property <tt>comparephoto</tt>
	 * @return  Returns the comparephoto.
	 * @uml.property  name="comparephoto"
	 */
	public comparephoto getComparephoto() {
		return comparephoto;
	}

	/**
	 * Setter of the property <tt>comparephoto</tt>
	 * @param comparephoto  The comparephoto to set.
	 * @uml.property  name="comparephoto"
	 */
	public void setComparephoto(comparephoto comparephoto) {
		this.comparephoto = comparephoto;
	}

	/**
	 * @uml.property  name="photoSubView"
	 * @uml.associationEnd  inverse="photo:view.PhotoSubView"
	 */
	private PhotoSubView photoSubView;

	/**
	 * Getter of the property <tt>photoSubView</tt>
	 * @return  Returns the photoSubView.
	 * @uml.property  name="photoSubView"
	 */
	public PhotoSubView getPhotoSubView() {
		return photoSubView;
	}

	/**
	 * Setter of the property <tt>photoSubView</tt>
	 * @param photoSubView  The photoSubView to set.
	 * @uml.property  name="photoSubView"
	 */
	public void setPhotoSubView(PhotoSubView photoSubView) {
		this.photoSubView = photoSubView;
	}

	/**
	 * @uml.property  name="cameraView"
	 * @uml.associationEnd  inverse="photo:view.CameraView"
	 */
	private CameraView cameraView;

	/**
	 * Getter of the property <tt>cameraView</tt>
	 * @return  Returns the cameraView.
	 * @uml.property  name="cameraView"
	 */
	public CameraView getCameraView() {
		return cameraView;
	}

	/**
	 * Setter of the property <tt>cameraView</tt>
	 * @param cameraView  The cameraView to set.
	 * @uml.property  name="cameraView"
	 */
	public void setCameraView(CameraView cameraView) {
		this.cameraView = cameraView;
	}

	/**
	 * @uml.property  name="takePhoto"
	 * @uml.associationEnd  inverse="photo:controller.TakePhoto"
	 */
	private TakePhoto takePhoto;

	/**
	 * Getter of the property <tt>takePhoto</tt>
	 * @return  Returns the takePhoto.
	 * @uml.property  name="takePhoto"
	 */
	public TakePhoto getTakePhoto() {
		return takePhoto;
	}

	/**
	 * Setter of the property <tt>takePhoto</tt>
	 * @param takePhoto  The takePhoto to set.
	 * @uml.property  name="takePhoto"
	 */
	public void setTakePhoto(TakePhoto takePhoto) {
		this.takePhoto = takePhoto;
	}

}
