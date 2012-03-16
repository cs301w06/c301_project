package cs.c301.project;

import android.app.Application;
import android.os.Environment;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;
import cs.c301.project.Models.PhotoModel;

public class PhotoApplication extends Application {

	public static PhotoModel model;
	
	public PhotoApplication() {
		model = new PhotoModel(Environment.getExternalStorageDirectory());
	}
	
	public static String getFilePath() {
		return model.getFilePath();
	}
	
	public static void addPhotoModelListener(PhotoModelListener c) {
		model.addPhotoModelListener(c);
	}
	
	public static void removePhotoModelListener(PhotoModelListener c) {
		model.removePhotoModelListener(c);
	}
	
	public static void addPhoto(PhotoEntry entry) {
		model.addPhoto(entry);
	}
	
	public static void removePhoto(int entry) {
		model.removePhoto(entry);
	}
	
	public static void addTag(String tag) {
		model.addTag(tag.trim());
	}
	
	public static void removeTag(String tag) {
		model.removeTag(tag.trim());
	}
	
	public static void addGroup(String group) {
		model.addGroup(group.trim());
	}
	
	public static void removeGroup(String group) {
		model.removeGroup(group.trim());
	}
	
	public static void updatePhoto(PhotoEntry entry) {
		model.updatePhoto(entry);
	}
	
	public static PhotoEntry getTemporaryImage() {
		return model.getTemporaryImage();
	}
	
	public static PhotoEntry getLatestImage() {
		return model.getLatestImage();
	}
}