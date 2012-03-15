package cs.c301.project;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PhotoReview extends Activity implements FView {
	ListView partlist;
	Button keepButton;
	ProgressDialog p;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);

		/* Review of photo */
		final String folderTmp = "tmp";
		final String fileTemp = "temp";
		ImageView reviewPhoto = (ImageView) findViewById(R.id.review_photo);
		reviewPhoto.setImageDrawable(Drawable.createFromPath(Photo.getPhoto(folderTmp, fileTemp).getPath()));

		/* Comparasion photo */
		ImageView comparePhoto = (ImageView) findViewById(R.id.review_photoCompare);

		/* Discard button */
		Button discardButton = (Button) findViewById(R.id.review_disc);
		discardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

			}

		});

		/* Select group and keep button */
		keepButton = (Button) findViewById(R.id.review_keep);
		keepButton.setText("Select Group");
		keepButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
		        System.out.println("asd");
				Builder dialog = new AlertDialog.Builder(PhotoReview.this);
				ShowLoginDialog();
			}
			
			
			
			
		});
	}
	
	private void ShowLoginDialog()

    {
        System.out.println("123");

        Builder builder = new AlertDialog.Builder(PhotoReview.this);

        builder.setTitle("pickPart");

        LayoutInflater factory = LayoutInflater.from(PhotoReview.this);

        View dialogView  = factory.inflate(R.layout.dialog, null);

        partlist =(ListView)dialogView.findViewById(R.id.listpart);

        GetPart();

        builder.setView(dialogView);

        builder.show();

    }
	
	private void GetPart()

    {

        System.out.println("asd");

        ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String,String>>();

        HashMap<String, String> hmItem = new HashMap<String, String>();

        hmItem.put("part", "Face");

        listData.add(hmItem);

        hmItem = new HashMap<String, String>();

        hmItem.put("part", "Feet");

        listData.add(hmItem);

        hmItem = new HashMap<String, String>();

        hmItem.put("part", "Hair");

        listData.add(hmItem);

        hmItem = new HashMap<String, String>();

        hmItem.put("part", "Arm");

        listData.add(hmItem);
        
        SimpleAdapter sim = new SimpleAdapter(this, listData, android.R.layout.simple_list_item_1, new String[]{"part"}, new int[]{android.R.id.text1});

        partlist.setAdapter(sim);

    }
	
	

	/**
	 * @uml.property  name="photo"
	 * @uml.associationEnd  inverse="photoReview:model.Photo"
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

	/**
	 * @uml.property  name="photoController"
	 * @uml.associationEnd  inverse="photoReview:controller.PhotoController"
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
