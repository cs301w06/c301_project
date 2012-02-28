package controller;

import model.Photo;
import view.SearchPhoto;
import view.PhotoReview;
import view.PartsListView;
import view.PhotoSubView;


public class PhotoController {

		
		/**
		 */
		public void comparison(){
		}

			
			/**
			 */
			public void grabPhoto(){
			}


				
				/**
				 */
				public void searchPhoto(){
				}


					
					/**
					 */
					public void setPhoto(){
					}



					/**
					 * @uml.property  name="photo1"
					 * @uml.associationEnd  inverse="photoController:model.Photo"
					 */
					private Photo photo1;



					/**
					 * Getter of the property <tt>photo1</tt>
					 * @return  Returns the photo1.
					 * @uml.property  name="photo1"
					 */
					public Photo getPhoto1() {
						return photo1;
					}


					/**
					 * Setter of the property <tt>photo1</tt>
					 * @param photo1  The photo1 to set.
					 * @uml.property  name="photo1"
					 */
					public void setPhoto1(Photo photo1) {
						this.photo1 = photo1;
					}



					/**
					 * @uml.property  name="searchPhoto1"
					 * @uml.associationEnd  inverse="photoController:view.SearchPhoto"
					 */
					private SearchPhoto searchPhoto1;



					/**
					 * Getter of the property <tt>searchPhoto1</tt>
					 * @return  Returns the searchPhoto1.
					 * @uml.property  name="searchPhoto1"
					 */
					public SearchPhoto getSearchPhoto1() {
						return searchPhoto1;
					}


					/**
					 * Setter of the property <tt>searchPhoto1</tt>
					 * @param searchPhoto1  The searchPhoto1 to set.
					 * @uml.property  name="searchPhoto1"
					 */
					public void setSearchPhoto1(SearchPhoto searchPhoto1) {
						this.searchPhoto1 = searchPhoto1;
					}



					/**
					 * @uml.property  name="photoReview"
					 * @uml.associationEnd  inverse="photoController:view.PhotoReview"
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
					 * @uml.property  name="partsListView"
					 * @uml.associationEnd  inverse="photoController:view.PartsListView"
					 */
					private PartsListView partsListView;



					/**
					 * Getter of the property <tt>partsListView</tt>
					 * @return  Returns the partsListView.
					 * @uml.property  name="partsListView"
					 */
					public PartsListView getPartsListView() {
						return partsListView;
					}


					/**
					 * Setter of the property <tt>partsListView</tt>
					 * @param partsListView  The partsListView to set.
					 * @uml.property  name="partsListView"
					 */
					public void setPartsListView(PartsListView partsListView) {
						this.partsListView = partsListView;
					}



					/**
					 * @uml.property  name="photoSubView"
					 * @uml.associationEnd  inverse="photoController:view.PhotoSubView"
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

}
