package view;

import controller.comparephoto;


public class ComparePhotoResult implements FView {

	/**
	 * @uml.property  name="comparephoto"
	 * @uml.associationEnd  inverse="comparePhotoResult:controller.comparephoto"
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

}
