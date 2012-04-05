package cs.c301.project;


import java.util.Vector;

import android.app.Application;
import android.net.Uri;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Models.LoginModel;
import cs.c301.project.Models.PhotoModel;

/**
 * Getters and setters for the photos generated, each 
 * photo will be able to delete, add, and update the
 * tags, groups and the file path
 */
public class PhotoApplication extends Application {

	private static LoginModel loginModel;
	private static PhotoModel model;
	private static String databaseName;
	private static boolean isDoctor;

	/**
	 * Creates a new photo model 
	 * @return 
	 */
	
	@Override
	public void onCreate() {
		super.onCreate();

		isDoctor = false;
		loginModel = new LoginModel(this);
		model = new PhotoModel(this);
	}
	
	public static PhotoEntry getPhotoByID(int id) {
		return model.getPhotoByID(id);
	}
	
	public static boolean login(String username, String password) {
		boolean attempt = loginModel.login(username, password);
		
		if (attempt)
			initializePhotoDatabase();
		
		return attempt;
	}
	
	
	public static boolean newAccount(String username, String password) {
		boolean attempt = loginModel.create(username, password);
		
		if (attempt)
			initializePhotoDatabase();
		
		return attempt;
	}
	
	public static boolean isDoctor() {
		return isDoctor;
	}
	
	public static void toggleDoctor() {
		isDoctor = !isDoctor;
	}
	
	private static void initializePhotoDatabase() {
		model.setUser(loginModel.getCurrentUser());
	}
	
	/**
	 * Add a new photo 
	 * 
	 * @param entry photo information
	 */
	public static boolean addPhoto(PhotoEntry entry) {
		return model.addPhoto(entry);
	}
	
	public static Vector<String> getPatients() {
		return loginModel.getPatients();
	}
	
	/**
	 * Remove a new photo 
	 * 
	 * @param entry photo information
	 */
	public static boolean removePhoto(int id) {
		return model.removePhoto(id);
	}
	
	/**
	 * Add a tag from photo
	 * 
	 * @param tag String of words to be associated to photo
	 */
	public static boolean addTag(String tag) {
		return model.addTag(tag.trim());
	}
	
	public static boolean addDoctorTag(String tag) {
		return model.addDoctorTag(tag.trim());
	}
	
	/**
	 * Remove a tag from photo
	 * 
	 * @param tag String of words to be associated to photo
	 */
	public static boolean removeTag(String tag) {
		return model.removeTag(tag.trim());
	}
	
	public static boolean removeDoctorTag(String tag) {
		return model.removeDoctorTag(tag.trim());
	}
	
	/**
	 * Add group from photo
	 * 
	 * @param group Body part that photo is grouped to 
	 */
	public static boolean addGroup(String group) {
		return model.addGroup(group.trim());
	}
	
	/**
	 * Remove group from photo
	 * 
	 * @param group Body part that photo is grouped to 
	 */
	public static boolean removeGroup(String group) {
		return model.removeGroup(group.trim());
	}
	
	/**
	 * Update photo information
	 * 
	 * @param entry photo information
	 */
	public static boolean updatePhoto(PhotoEntry entry) {
		return model.updatePhoto(entry);
	}
	
	public static Vector<String> getTags() {
		return model.getTags();
	}
	
	public static Vector<String> getDoctorTags() {
		return model.getDoctorTags();
	}
	
	public static Vector<String> getGroups() {
		return model.getGroups();
	}
	
	public static Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery) {
		return model.getPhotosByValues(groupsQuery, tagsQuery);
	}

	public static Vector<PhotoEntry> getAllPhotos() {
		return model.getAllPhotos();
	}
	
	public static Uri getTemporaryImage() {
		return model.getTemporaryImage();
	}
}