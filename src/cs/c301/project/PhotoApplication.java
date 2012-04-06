package cs.c301.project;

import java.util.Date;
import java.util.Vector;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
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
	private static boolean isDoctor;
	public static final String ENCRYPTION_KEY = "LolAndroidKey";
	
	/**
	 * Creates a new photo model 
	 * @return 
	 */
	/** Creates a new photo model */
	@Override
	public void onCreate() {
		super.onCreate();

		isDoctor = false;
		loginModel = new LoginModel(this);
		model = new PhotoModel(this);
	}
	
	/** Grab all photos by their id */
	public static PhotoEntry getPhotoByID(int id) {
		try {
			return model.getPhotoByID(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("PhotoApplication", e.getMessage());
		}
		
		return null;
	}
	
	/** 
	 * Intitializes the database depending on the user account used
	 * given by the user
	 * 
	 *  @param username	account name of the user
	 *  @param password	password for the given account 
	 *  @return true if successful log in
	 */
	public static boolean login(String username, String password) {
		boolean attempt = loginModel.login(username, password);
		
		if (attempt)
			initializePhotoDatabase();
		
		return attempt;
	}
	
	/**
	 * Creates a new account if the account does not exist 
	 * 
	 * @param username account name of the user
	 * @param password password for the given account
	 * @return true if successful in adding account
	 */
	public static boolean newAccount(String username, String password) {
		boolean attempt = loginModel.create(username, password);
		
		if (attempt)
			initializePhotoDatabase();
		
		return attempt;
	}
	
	/**
	 * Checks if the account is a doctor
	 * 
	 * @return if the account is a doctor
	 */
	public static boolean isDoctor() {
		return isDoctor;
	}
	
	/**
	 * Makes the account a doctor account
	 */
	public static void toggleDoctor() {
		isDoctor = !isDoctor;
	}
	
	/**
	 * Initializes the database of the given user account
	 */
	private static void initializePhotoDatabase() {
		model.setUser(loginModel.getCurrentUser());
	}
	
	/**
	 * Add a new photo 
	 * 
	 * @param entry photo information
	 * @throws Exception 
	 */
	public static boolean addPhoto(PhotoEntry entry) {
		try {
			return model.addPhoto(entry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("PhotoApplication", e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Allows the doctor account to grab all patients database
	 * @return all patients allocated to the doctor
	 */
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
	
	/**
	 * Allows the doctor to remove tags 
	 * 
	 * @param tag removes all tags used by the doctor
	 * @return removes the given tag
	 */
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
	
	/**
	 * Grab the tags from the photo model
	 * 
	 * @return tags from the photo model
	 */
	public static Vector<String> getTags() {
		return model.getTags();
	}
	
	/**
	 * Grab doctor tags from the photo model
	 * 
	 * @return doctor tags from the photo model
	 */
	public static Vector<String> getDoctorTags() {
		return model.getDoctorTags();
	}
	
	/**
	 * Grab vecotrs of groups from the photo model 
	 * 
	 * @return groups from the photo model
	 */
	public static Vector<String> getGroups() {
		return model.getGroups();
	}
	
	/**
	 * Finds the photos with given group and tag string values
	 * 
	 * @param groupsQuery a group string that shows photos with that attached string
	 * @param tagsQuery a tag string that shows photos with that attached string
	 * @return all photos by the given photos
	 */
	public static Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery) {
		return model.getPhotosByValues(groupsQuery, tagsQuery);
	}
	
	public static Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery, Date startDate, Date endDate) {
		return model.getPhotosByValues(groupsQuery, tagsQuery, startDate, endDate);
	}
	
	/**
	 * Grabs the temporary image uri 
	 * 
	 * @return the temporary image file
	 */
	public static Uri getTemporaryImage() {
		return model.getTemporaryImage();
	}
}
