package cs.c301.project.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import cs.c301.project.PhotoApplication;
import cs.c301.project.Utilities.SimpleCrypto;

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
	private Vector<String> tags; //associated tags
	private String group; //associated groups
	private Date date; //date of picture
	private byte[] encryptedImage; //temporarily store the bitmap for adding a new photo
	private Vector<String> doctor; //tags for the doc
	private String annotation; //description of the photo
	private byte[] encryptedThumbnail; //for sub view efficiency
	
	/**
	 * Constructor for the PhotoEntry object. Generates all metadata for a photo. 
	 * Attaches tags and group to the photo, and generates a date upon object creation.
	 * 
	 * @param bitmap Bitmap representation of a photo
	 * @param tags List of tags associated with specified photo
	 * @param group Group associated with specified photo
	 */
	public PhotoEntry() {
		tags = new Vector<String>(0, 1);
		doctor = new Vector<String>(0, 1);
		group = "";
		annotation = "";
		date = new Date();
	}
	
	/**
	 * Getter for the photo bitmap itself
	 * 
	 * @return Bitmap of the photo
	 */
	public Bitmap getBitmap() {
		Bitmap bitmap = null;
		
		byte[] decryptedBlob;
		
		try {
			decryptedBlob = SimpleCrypto.decrypt(SimpleCrypto.getRawKey(PhotoApplication.ENCRYPTION_KEY.getBytes()), encryptedImage);
			bitmap = BitmapFactory.decodeByteArray(decryptedBlob, 0, decryptedBlob.length);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return bitmap;
	}
	
	/**
	 * Deletes the photo from the data by setting it null
	 */
	public void setBitmap(Bitmap image) {
		try {
		Bitmap thumbnail = Bitmap.createScaledBitmap(image, 100, 100, true);
		ByteArrayOutputStream photoOutput = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, photoOutput);
		byte[] bytes = photoOutput.toByteArray();
		photoOutput.flush();
		thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, photoOutput);
		byte[] thumbbytes = photoOutput.toByteArray();
		photoOutput.flush();
		
		encryptedImage = SimpleCrypto.encrypt(SimpleCrypto.getRawKey(PhotoApplication.ENCRYPTION_KEY.getBytes()), bytes);
		encryptedThumbnail = SimpleCrypto.encrypt(SimpleCrypto.getRawKey(PhotoApplication.ENCRYPTION_KEY.getBytes()), thumbbytes);
		
		photoOutput.close();
		} 
		
		catch (Exception e) {}
	}
	
	public byte[] getThumbnailBytes() {
		return encryptedThumbnail;
	}
	
	public void setThumbnailBytes(byte[] thumbnail) {
		encryptedThumbnail = thumbnail;
	}
	
	public Bitmap getThumbnail() {
		Bitmap bitmap = null;
		
		byte[] decryptedBlob;
		
		try {
			decryptedBlob = SimpleCrypto.decrypt(SimpleCrypto.getRawKey(PhotoApplication.ENCRYPTION_KEY.getBytes()), encryptedThumbnail);
			bitmap = BitmapFactory.decodeByteArray(decryptedBlob, 0, decryptedBlob.length);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return bitmap;
	}
	
	public byte[] getBitmapBytes() {
		return encryptedImage;
	}
	
	public void setBitmapBytes(byte[] bytes) {
		encryptedImage = bytes;
	}
	
	public void addDoctorTag(String tag) {
		if (!doctor.contains(tag)) {
			doctor.add(tag);
		}
	}
	
	public void removeDoctorTag(String tag) {
		if (doctor.contains(tag)) {
			doctor.remove(tag);
		}
	}
	
	public void setDoctorTags(String tags) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(tags, ",");
			
			while (tokenizer.hasMoreTokens()) {
				addDoctorTag(tokenizer.nextToken());
			}
		}
		
		catch (Exception e) {}
	}
	
	public String getDoctorTagsForDatabase() {
		String tagConstruct = "";
		
		for (int i = 0; i < doctor.size(); i++) {
			tagConstruct += doctor.elementAt(i) + ",";
		}
		
		if (!tagConstruct.equals(""))
		return tagConstruct.substring(0, tagConstruct.length() - 1);
		
		return tagConstruct;
	}
	
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	public String getAnnotation() {
		return annotation;
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
	
	public void setTags(String tags) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(tags, ",");
			
			while (tokenizer.hasMoreTokens()) {
				addTag(tokenizer.nextToken());
			}
		}
		
		catch (Exception e) {}
	}
	
	public String getTagsForDatabase() {
		String tagConstruct = "";
		
		for (int i = 0; i < tags.size(); i++) {
			tagConstruct += tags.elementAt(i) + ",";
		}
		
		if (!tagConstruct.equals(""))
		return tagConstruct.substring(0, tagConstruct.length() - 1);
		
		return tagConstruct;
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
	
	public void setDate(Date date) {
		this.date = date;
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
}