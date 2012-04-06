package cs.c301.project;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;
import cs.c301.project.Data.PhotoEntry;


/** 
 * PhotoCompare is mainly used as a place holder for now
 * there is no implementation for the time being
 *
 */
public class PhotoCompare extends Activity implements ViewFactory, OnItemSelectedListener {
	
	
	private static int count;

	public int getCount()
	{
		
		return count;
		
	}
	
	
	public void setCount(int count){
		
		PhotoCompare.count = count;
	}
	
	private ImageSwitcher firstPhoto;
	private ImageSwitcher secondPhoto;
	
	
	private Integer[] photoThumbIds =  { R.drawable.sample_0,  
            R.drawable.sample_1, R.drawable.sample_2,  
            R.drawable.sample_3, R.drawable.sample_4,  
            R.drawable.sample_5, R.drawable.sample_6,  
            R.drawable.sample_7 };  
	private Integer[] photoImageIds =  { R.drawable.sample_0,  
            R.drawable.sample_1, R.drawable.sample_2,  
            R.drawable.sample_3, R.drawable.sample_4,  
            R.drawable.sample_5, R.drawable.sample_6,  
            R.drawable.sample_7 };  
	
	private int flag = 2;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setCount(0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.photo_comp);

	    firstPhoto = (ImageSwitcher) findViewById(R.id.Photo1);
		secondPhoto = (ImageSwitcher) findViewById(R.id.Photo2);
		firstPhoto.setFactory(this);
		firstPhoto.setAnimation(AnimationUtils.loadAnimation(this, 
				android.R.anim.fade_in));
		firstPhoto.setAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));
		
		secondPhoto.setFactory(this);
		secondPhoto.setAnimation(AnimationUtils.loadAnimation(this, 
				android.R.anim.fade_in));
		secondPhoto.setAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));
		
		Gallery compGallery = (Gallery) findViewById(R.id.compGallery);
		
		compGallery.setAdapter(new ImageAdapter(this));
		compGallery.setOnItemSelectedListener(this);
		
		
//		PhotoApplication.addPhotoModelListener(this);
	}
	public class ImageAdapter extends BaseAdapter{
		public ImageAdapter(Context c){
			mContext = c;
			
		}

		public int getCount()
		{
			return photoThumbIds.length;
		}

		public Object getItem(int position)
		{
			return position;
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView tempImage = new ImageView(mContext);
			
			tempImage.setImageResource(photoThumbIds[position]);
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
		System.out.print(flag);
		System.out.println("\n");

		if(flag == 2){
			
			this.setCount(this.getCount()+1);
			
			if(this.getCount() % 2 == 1){
				
				firstPhoto.setImageResource(photoImageIds[position]);
				
			}else{
				
				System.out.println("123123123!!!\n");
				secondPhoto.setImageResource(photoImageIds[position]);
				flag = 3;
			}
			
		}
		secondPhoto.setImageResource(photoImageIds[position]);
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
