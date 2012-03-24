package cs.c301.project.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * This class contains all the information which is attached to a specific photo, and all
 * methods which interact with that information.
 * <p>
 * Javadoc by esteckle
 * @author yhu3
 *
 */
public class PhotoEntry implements Serializable {
	private int id; //id of picture
	private String filePath; //absolute path that includes name
	private Vector<String> tags; //associated tags
	private String group; //associated groups
	private Date date; //date of picture
	private Bitmap bitmap; //temporarily store the bitmap for adding a new photo
	
	/**
	 * Constructor for the PhotoEntry object. Generates all metadata for a photo. 
	 * Attaches tags and group to the photo, and generates a date upon object creation.
	 * 
	 * @param bitmap Bitmap representation of a photo
	 * @param tags List of tags associated with specified photo
	 * @param group Group associated with specified photo
	 */
	public PhotoEntry(Bitmap bitmap, Vector<String> tags, String group) {
		this.bitmap = bitmap;
		this.tags = tags;
		this.group = group;
		date = new Date();
	}
	/**
	 * Getter for the photo bitmap itself
	 * 
	 * @return Bitmap of the photo
	 */
	public Bitmap getBitmap() {
		if (bitmap == null) {
			return BitmapFactory.decodeFile(getFilePath());
		}
		
		return bitmap;
	}
	
	/**
	 * Deletes the photo from the data by setting it null
	 */
	public void deleteBitmap() {
		bitmap = null;
	}
	
	/**
	 * Setter for the photo file path
	 * 
	 * @param s Given file path
	 */
	public void setFilePath(String s) {
		filePath = s;
	}
	
	/**
	 * Getter for the photo file path
	 * 
	 * @return File path as a string
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * Checks if the given tag already exists for the photo, and adds it to the tag
	 * list if it does not.
	 * 
	 * @param tag Specified tag to be attached to the photo
	 */
	public void addTag(String tag) {
		if (!tags.contains(tag)) {
			tags.add(tag);
		}
	}
	
	/**
	 * Checks to see if the given tag is attached to the photo, and removes it if it is.
	 * 
	 * @param tag Specified tag to be removed from the photo
	 */
	public void removeTag(String tag) {
		if (tags.contains(tag)) {
			tags.remove(tag);
		}
	}
	
	public void setTags(Vector<String> tags) {
		this.tags = tags;
	}
	
	public Vector<String> getTags() {
		return tags;
	}
	
	/**
	 * Sets the photo group to the given string.
	 * 
	 * @param s The group which the photo is being added to
	 */
	public void setGroup(String s) {
		group = s;
	}
	
	/**
	 * Getter for the photo group.
	 * 
	 * @return The group to which the photo belongs.
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Getter for the timestamp
	 * 
	 * @return Timestamp for when the photo was taken
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Sets the id for this entry of the PhotoEntry object.
	 * 
	 * @param id Automatically generated id
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Getter for the id of this PhotoEntry object.
	 * 
	 * @return PhotoEntry object ID
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Generates a unique name for a photo when it is saved.
	 * 
	 * @return Filename under which the photo is saved.
	 */
	public String getSaveName() {
		return date.getDate() + date.getMonth() + date.getYear() + date.getHours() + date.getMinutes() + date.getSeconds() + ".jpg";
	}
}