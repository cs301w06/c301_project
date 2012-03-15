package cs.c301.project.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import android.graphics.Bitmap;

public class PhotoEntry implements Serializable {
	private int id; //id of picture
	private String filePath; //absolute path that includes name
	private Vector<String> tags; //associated tags
	private String group; //associated groups
	private Date date; //date of picture
	private Bitmap bitmap; //temporarily store the bitmap for adding a new photo
	
	public PhotoEntry(Bitmap bitmap, Vector<String> tags, String group) {
		this.bitmap = bitmap;
		this.tags = tags;
		this.group = group;
		date = new Date();
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void deleteBitmap() {
		bitmap = null;
	}
	
	public void setFilePath(String s) {
		filePath = s;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void addTag(String tag) {
		if (!tags.contains(tag)) {
			tags.add(tag);
		}
	}
	
	public void removeTag(String tag) {
		if (tags.contains(tag)) {
			tags.remove(tag);
		}
	}
	
	public void setGroup(String s) {
		group = s;
	}
	
	public String getGroup() {
		return group;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}