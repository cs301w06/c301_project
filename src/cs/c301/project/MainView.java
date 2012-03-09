package cs.c301.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainView extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button button = (Button) findViewById(R.id.main_camera);
		button.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		Intent intent = new Intent(this, CameraView.class);
		startActivity(intent);
	}
	
	/**
	 * @uml.property  name="searchphoto"
	 * @uml.associationEnd  inverse="mainView:controller.searchphoto"
	 */
	private SearchPhoto searchphoto;

	/**
	 * Getter of the property <tt>searchphoto</tt>
	 * @return  Returns the searchphoto.
	 * @uml.property  name="searchphoto"
	 */
	public SearchPhoto getSearchphoto() {
		return searchphoto;
	}

	/**
	 * Setter of the property <tt>searchphoto</tt>
	 * @param searchphoto  The searchphoto to set.
	 * @uml.property  name="searchphoto"
	 */
	public void setSearchphoto(SearchPhoto searchphoto) {
		this.searchphoto = searchphoto;
	}



}
