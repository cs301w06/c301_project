package cs.c301.project;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapArrayController {
	
	private String filepath;
	
	public BitmapArrayController(String path){
		filepath = path;
	}
	
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
    
    /*
     * Builds the photo gallery by creating an array of bmps
     * May currently be out of order, won't know until testing
     */
    public Bitmap[] imageGallery(String[] imagePaths){
    	Bitmap bmpArray[] = new Bitmap[imagePaths.length];
    	for(int i = 0; i < imagePaths.length; i++)
    		bmpArray[i] = BitmapFactory.decodeFile(imagePaths[i]);
    	return bmpArray;
    }
}
