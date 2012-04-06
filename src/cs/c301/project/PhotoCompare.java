package cs.c301.project;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;
import cs.c301.project.Data.PhotoEntry;

/** 
 * PhotoCompare is the interface to allow user to compare the current
 * selected photo, with the other photos under the same group. This 
 * function is implemented from the detail view of a saved photo.
 * 
 * @parameter firstPhoto imageView for the selected photo
 * @parameter secondPhoto imageView for the comparing photo
 * @parameter photoThumbIds gallery for all the other photos
 * 
 * 
 * 
 * 
 * 
 */
public class PhotoCompare extends Activity implements ViewFactory, OnItemSelectedListener {

	private static int count;

	public int getCount() {

		return count;
	}

	public void setCount(int count) {

		PhotoCompare.count = count;
	}

	private PhotoEntry photoEntry;
	private ImageView firstPhoto;
	private ImageView secondPhoto;
	private TextView groupText;
	private Bitmap[] photoThumbIds ; 
	private Bitmap[] photoImageIds ;  

	private int flag = 2;

	/**
	 * On the start of the activity use the selected photo
	 * and compare to the next closest photo by default and then
	 * allow the user to select the second photo to compare to 
	 * 
	 * @param savedInstanceState all extras passed from the previous intents
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setCount(0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.photo_comp);

		Vector<String> group = new Vector<String>();
		Vector<PhotoEntry> photo = new Vector<PhotoEntry>();

		Bundle extra = getIntent().getExtras();

		final int photoId = (Integer) extra.get("photo");

		photoEntry = PhotoApplication.getPhotoByID(photoId);
		group.add(photoEntry.getGroup());
		
		groupText = (TextView) findViewById(R.id.comp_group);
		groupText.setText(photoEntry.getGroup().toString());
		
		photo = PhotoApplication.getPhotosByValues(group, null);

		photoThumbIds = new Bitmap[photo.size()];
		for (int i = 0; i < photo.size(); i++){

			photoThumbIds[i] = photo.elementAt(i).getThumbnail();
		}

		photoImageIds = new Bitmap[photo.size()];
		for (int i = 0; i < photo.size(); i++){

			photoThumbIds[i] = photo.elementAt(i).getBitmap();
		}

		firstPhoto = (ImageView) findViewById(R.id.Photo1);
		secondPhoto = (ImageView) findViewById(R.id.Photo2);

		Gallery compGallery = (Gallery) findViewById(R.id.compGallery);

		compGallery.setAdapter(new ImageAdapter(this));
		compGallery.setOnItemSelectedListener(this);
	}

	/**
	 * Image adapter that gets the position of the second photo
	 * that is selected by the user
	 *
	 */
	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {

			mContext = c;
		}

		public int getCount() {

			return photoThumbIds.length;
		}

		public Object getItem(int position)	{

			return position;
		}

		public long getItemId(int position) {

			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView tempImage = new ImageView(mContext);

			tempImage.setImageBitmap(photoThumbIds[position]);
			tempImage.setAdjustViewBounds(true);
			tempImage.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tempImage.setBackgroundResource(R.drawable.picture_frame);

			return tempImage;
		}
		private Context mContext;

	}


	public void onItemSelected(AdapterView<?> adapter, View v, int position,
			long id){
		System.out.println("Iam here!!!\n");
		System.out.print(position);
		System.out.println("\n");

		if(flag == 2){
			super.onStart();

			firstPhoto.setImageBitmap(photoEntry.getBitmap());
			
			secondPhoto.setImageBitmap(photoThumbIds[position]);
		}

	}


	public void onNothingSelected(AdapterView<?> arg0){


	}

	public View makeView()
	{
		ImageView tempImage = new ImageView(this);
		tempImage.setBackgroundColor(0xFF000000);
		tempImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
		tempImage.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		return tempImage;
	}

}
