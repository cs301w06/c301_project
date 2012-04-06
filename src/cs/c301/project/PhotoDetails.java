package cs.c301.project;

import java.util.Vector;

import cs.c301.project.Data.PhotoEntry;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Photo details allows the user to view the photo with 
 * extra details like description, tags and date of the 
 * photo
 * 
 * @author wjtran
 *
 */
public class PhotoDetails extends Activity {
	
	private PhotoEntry photoEntry;
	private Vector<PhotoEntry> photos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		Bundle extra = getIntent().getExtras();
		
		final int photoId = (Integer) extra.get("photo");
		
		
		//String group = extra.getString("group");
		//String tags = extra.getString("tag");
		
		//Vector<String> groupV = new Vector<String>();
		//Vector<String> tagsV = new Vector<String>();
		
		
		//photos = PhotoApplication.getPhotosByValues(groupV, tagsV);
		
		photoEntry = PhotoApplication.getPhotoByID(photoId);
		
		Button tagButton = (Button) findViewById(R.id.det_tag_button);
		tagButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//TODO add tag
			}
			
		});
		
		Button compareButton = (Button) findViewById(R.id.det_compare);
		compareButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(PhotoDetails.this, PhotoCompare.class);
				intent.putExtra("photo", photoId);
				startActivity(intent);
			}
			
		});
		
		Button delButton = (Button) findViewById(R.id.det_del);
		delButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v)
			{

				
				new AlertDialog.Builder(PhotoDetails.this)
				.setTitle("Confirm Information")
				.setMessage("Are you sure you want to delete?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener()
				{
					
					public void onClick(DialogInterface dialog, int which)
					{
				
						// TODO Auto-generated method stub
						PhotoApplication.removePhoto(photoId);
						//Intent intent = new Intent(PhotoDetails.this, GroupList.class);
						//startActivity(intent);
						Toast.makeText(getApplicationContext(), "Photo has been deleted", Toast.LENGTH_SHORT).show();
						finish();
						
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener()
				{
					
					public void onClick(DialogInterface dialog, int which)
					{
				
						// TODO Auto-generated method stub
						
					}
				})
				.show();
				
				
				
				
				
				
			}
			
			
			
			
		});
		
		
		
		
		TextView descriptionText = (TextView) findViewById(R.id.description_text);
		descriptionText.setText(photoEntry.getAnnotation());
		
		TextView dateText = (TextView) findViewById(R.id.date_text);
		dateText.setText(photoEntry.getDate().toString());
		
		TextView tagText = (TextView) findViewById(R.id.tag_text);
		tagText.setText(photoEntry.getTagsForDatabase());
	}
	
	
	
	protected void onStart() {
		super.onStart();
		ImageView reviewPhoto = (ImageView) findViewById(R.id.det_photo);
		reviewPhoto.setImageBitmap(photoEntry.getBitmap());
	}
}
