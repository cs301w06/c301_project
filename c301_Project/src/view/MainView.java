package view;

import controller.searchphoto;


public class MainView implements FView {

	/**
	 * @uml.property  name="searchphoto"
	 * @uml.associationEnd  inverse="mainView:controller.searchphoto"
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

}
