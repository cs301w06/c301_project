package cs.c301.project;

import android.app.Activity;
import android.os.Bundle;



public class CameraView extends Activity implements FView {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        
	}
	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  inverse="cameraView:model.Photo"
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

}
