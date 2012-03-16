package cs.c301.project;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * BitmapArrayController is the class which takes a folder file path and will create an 
 * array of bitmap images from all image files contained within the folder. It contains
 * a method getPaths which finds the absolute paths of every image within the folder.
 * The method imageGallery takes an array of paths and converts all the images into
 * bitmaps, storing them in an array.
 * 
 * @author esteckle
 *
 */
public class BitmapArrayController {
	
	private String filepath;
	
	/**
	 * Constructor for the class. It accepts a folder path when it is constructed,
	 * and stores it within the object.
	 * 
	 * @param path The path for the folder containing our images
	 */
	public BitmapArrayController(String path){
		filepath = path;
	}
	
	/**
	 * With the folder file path contained in the object, this method will
	 * find all images contained within the folder, and save the file paths for
	 * each image into a string array.
	 * 
	 * @return A string array of the paths to each image in the folder 
	 */
	public String[] getPaths(){
    	File folder = new File(filepath);
    	File[] imageFiles = folder.listFiles();
    	String[] imagePaths = new String[imageFiles.length];
    	
        for(int i = 0; i < imageFiles.length; i++)
        {
          File image = imageFiles[i];
          imagePaths[i] = image.getAbsolutePath();
        }
        
        return imagePaths;
    }
    
	/**
	 * Given an array of file paths, this method decodes the image files specified
	 * by the paths into bitmaps, and then stores the bitmaps into an array.
	 * 
	 * @param imagePaths String array containing all the paths for our images
	 * @return A bitmap array of all the images in the folder
	 */
    public Bitmap[] imageGallery(String[] imagePaths){
    	Bitmap bmpArray[] = new Bitmap[imagePaths.length];
    	
    	for(int i = 0; i < imagePaths.length; i++)
    		bmpArray[i] = BitmapFactory.decodeFile(imagePaths[i]);
    	return bmpArray;
    }
}
