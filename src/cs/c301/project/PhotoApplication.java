package cs.c301.project;

import android.app.Application;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;
import cs.c301.project.Models.PhotoModel;

public class PhotoApplication extends Application {

	public static PhotoModel model;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		model = new PhotoModel();
	}
	
	public void addPhotoModelListener(PhotoModelListener c) {
		model.addPhotoModelListener(c);
	}
	
	public void removePhotoModelListener(PhotoModelListener c) {
		model.removePhotoModelListener(c);
	}
	
	public void addPhoto(PhotoEntry entry) {
		model.addPhoto(entry);
	}
	
	public void removePhoto(int entry) {
		model.removePhoto(entry);
	}
	
	public void addTag(String tag) {
		model.addTag(tag.trim());
	}
	
	public void removeTag(String tag) {
		model.removeTag(tag.trim());
	}
	
	public void addGroup(String group) {
		model.addGroup(group.trim());
	}
	
	public void removeGroup(String group) {
		model.removeGroup(group.trim());
	}
}