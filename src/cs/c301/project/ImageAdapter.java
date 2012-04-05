package cs.c301.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * The image adapter which puts images into our grid view.
 * Code base from GridView tutorial on Android SDK page.
 * 
 * @author esteckle
 *
 */
public class ImageAdapter extends BaseAdapter {
	 private Context mContext;
	 private Bitmap[] mThumbBmp; // Array for containing the photos we will display

	    /**
	     * Creates the ImageAdapter object.
	     * 
	     * @param c The context view for our adapter
	     * @param bmpArray An array containing our images
	     */
	    public ImageAdapter(Context c, Bitmap[] bmpArray) {
	    	mThumbBmp = bmpArray;
	        mContext = c;
	    }

	    /**
	     * Gets the number of images stored in the bitmap array
	     * 
	     * @return Returns the number of elements in the bitmap array
	     */
	    public int getCount() {
	        return mThumbBmp.length;
	    }

	    /**
	     * Place holder for item getter, currently returns null
	     * 
	     * @param position Element position
	     * @return Returns null
	     */
	    public Object getItem(int position) {
	        return null;
	    }

	    /**
	     * Place holder for item id getter
	     * 
	     * @param position Element position
	     * @return Returns 0
	     */
	    public long getItemId(int position) {
	        return 0;
	    }

	    /**
	     * Creates out image view and inserts it into our grid view
	     * 
	     * @param position
	     * @param convertView
	     * @param parent
	     * @return Returns the imageView inserted into our grid view
	     */
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

	        } else {
	            imageView = (ImageView) convertView;
	        }
	        imageView.setImageBitmap(mThumbBmp[position]);
	        
	        imageView.setBackgroundResource(R.drawable.photo_border);
	        return imageView;
	    }	    
}
