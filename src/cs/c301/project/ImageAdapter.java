/*
 * We may want to rename this or integrate it somewhere else, this is just to get the ball rolling
 * Adapter code largely taken from gridView android tutorial
 */

package cs.c301.project;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	 private Context mContext;
	 
	 	// Empty array for containing the photos we will display
	 	// Will probably want to move elsewhere, here as a place holder
	    private Bitmap[] mThumbBmp;

	    public ImageAdapter(Context c, Bitmap[] bmpArray) {
	    	mThumbBmp = bmpArray;
	        mContext = c;
	    }

	    public int getCount() {
	        return mThumbBmp.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    // **Adapted from the gridView tutorial, may have issues
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        //getPaths(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + folderName + File.separator);
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            //imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        imageView.setImageBitmap(mThumbBmp[position]);
	        
	       //Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp/temp.jpg");
	       //imageView.setImageBitmap(bm);
	        return imageView;
	    }
	    
	    /*
	     * Given a folder path, finds the paths for all photos within the file and stores them
	     */
	    
}
