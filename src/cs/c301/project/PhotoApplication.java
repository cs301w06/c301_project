package cs.c301.project;


import java.util.Vector;

import android.app.Application;
import android.os.Environment;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;
import cs.c301.project.Models.PhotoModel;

/**
 * Getters and setters for the photos generated, each 
 * photo will be able to delete, add, and update the
 * tags, groups and the file path
 */
public class PhotoApplication extends Application {

	private static PhotoModel model;
	
	/**
	 * Creates a new photo model 
	 */
	public PhotoApplication() {
		model = new PhotoModel(Environment.getExternalStorageDirectory());
	}
	
	/**
	 * Get the photo model file path
	 * 
	 * @return path of the photo model
	 */
	public static String getFilePath() {
		return model.getFilePath();
	}
	
	/**
	 * Use listener to see if photo is added
	 * 
	 * @param c photo model listener
	 */
	public static void addPhotoModelListener(PhotoModelListener c) {
		model.addPhotoModelListener(c);
	}
	
	/**
	 * Use listener to see if photo is removed
	 * 
	 * @param c photo model listener
	 */
	public static void removePhotoModelListener(PhotoModelListener c) {
		model.removePhotoModelListener(c);
	}
	
	/**
	 * Add a new photo 
	 * 
	 * @param entry photo information
	 */
	public static void addPhoto(PhotoEntry entry) {
		model.addPhoto(entry);
	}
	
	/**
	 * Remove a new photo 
	 * 
	 * @param entry photo information
	 */
	public static void removePhoto(int entry) {
		model.removePhoto(entry);
	}
	
	/**
	 * Add a tag from photo
	 * 
	 * @param tag String of words to be associated to photo
	 */
	public static boolean addTag(String tag) {
		return model.addTag(tag.trim());
	}
	
	/**
	 * Remove a tag from photo
	 * 
	 * @param tag String of words to be associated to photo
	 */
	public static void removeTag(String tag) {
		model.removeTag(tag.trim());
	}
	
	/**
	 * Add group from photo
	 * 
	 * @param group Body part that photo is grouped to 
	 */
	public static boolean addGroup(String group) {
		return model.addGroup(group);
	}
	
	/**
	 * Remove group from photo
	 * 
	 * @param group Body part that photo is grouped to 
	 */
	public static void removeGroup(String group) {
		model.removeGroup(group.trim());
	}
	
	/**
	 * Update photo information
	 * 
	 * @param entry photo information
	 */
	public static void updatePhoto(PhotoEntry entry) {
		model.updatePhoto(entry);
	}
	
	/**
	 * Get temporary image
	 * 
	 * @return the temporary image
	 */
	public static PhotoEntry getTemporaryImage() {
		return model.getTemporaryImage();
	}
	
	public static Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery) {
		return model.getPhotosByValues(groupsQuery, tagsQuery);
	}
}