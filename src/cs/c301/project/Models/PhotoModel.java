package cs.c301.project.Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Vector;

import android.graphics.Bitmap;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Listeners.PhotoModelListener;

@SuppressWarnings("serial")
public class PhotoModel implements Serializable {
	private Vector<PhotoModelListener> listeners;
	private Vector<String> tags; //stores tags data
	private Vector<String> groups; //stores groups data
	private Vector<PhotoEntry> data; //stores photo data
	private int tracker; //for a new photo id
	private String filePath; //external sd card address passed down
	
	@SuppressWarnings("unchecked")
	public PhotoModel(File f) {
		filePath = f.getAbsolutePath();
		listeners = new Vector<PhotoModelListener>(0, 1);
		data = new Vector<PhotoEntry>(0, 1);
		tags = new Vector<String>(0, 1);
		groups = new Vector<String>(0, 1);
		tracker = 0;
		
		try {
			//TODO: check if the image still exists or not; purge otherwise
			File file = new File("data");
			
			if (file.exists()) {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				data = (Vector<PhotoEntry>)input.readObject();
				input.close();
				
				for (int i = 0; i < data.size(); i++) {
					data.elementAt(i).setID(i);
				}
				
				tracker = data.size() - 1;
			}
		}
		
		catch (Exception e) {}
		
		try {
			File file = new File("tags");
			
			if (file.exists()) {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				tags = (Vector<String>)input.readObject();
				input.close();
			}
		}
		
		catch (Exception e) {}
		
		try {
			File file = new File("groups");
			
			if (file.exists()) {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				groups = (Vector<String>)input.readObject();
				input.close();
			}
		}
		
		catch (Exception e) {}
	}
	
//	public PhotoEntry getTemporaryImage() {
//		PhotoEntry entry = new PhotoEntry(null, null, "tmp");
//		entry.setFilePath(filePath + File.separator + entry.getGroup() + File.separator + entry.getDate().toString() + ".jpg");
//		entry.setID(tracker);
//		tracker++;
//		
//		return entry;
//	}
	
	public Vector<String> getGroups() {
		return groups;
	}
	
	public void addGroup(String group) {
		if (!groups.contains(group)) {
			groups.add(group);
			
			updateModelListeners();
		}
	}
	
	public void removeGroup(String group) {
		if (groups.contains(group)) {
			for (int i = 0; i < data.size(); i++) {
				data.elementAt(i).setGroup("");
			}
			
			groups.remove(group);
			
			updateModelListeners();
		}
	}
	
	public Vector<String> getTags() {
		return tags;
	}
	
	public void addTag(String tag) {
		if (!tags.contains(tag)) {
			tags.add(tag);
			
			updateModelListeners();
		}
	}
	
	public void removeTag(String tag) {
		if (tags.contains(tag)) {
			for (int i = 0; i < data.size(); i++) {
				data.elementAt(i).removeTag(tag);
			}
			
			tags.remove(tag);
			
			updateModelListeners();
		}
	}
	
	public Vector<PhotoEntry> getPhotos() {
		return data;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void addPhoto(PhotoEntry photo) {
		photo.setID(tracker);
		
		//make folder exist
		File test = new File(filePath + File.separator + photo.getGroup());
		if (!test.exists())
			test.mkdir();
		
		if (!photo.getGroup().equals("tmp")) {
			photo.setFilePath(filePath + File.separator + photo.getGroup() + File.separator + photo.getDate().toString() + ".jpg");
			
			try {
				File file = new File(photo.getFilePath());
				OutputStream output = new FileOutputStream(file);
	    		photo.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, output);
	    		output.close();
	    		photo.deleteBitmap();
			}
			
			catch (Exception e) {}
		}
		
		data.add(photo);
		tracker++;
		
		updateModelListeners();
	}
	
	//TODO: this function will update tags, update groups, update the storage location, and update the image itself all based on whether they are null or not, id is used to match
	public void updatePhoto(PhotoEntry entry) {
		
	}
	
	public void removePhoto(int id) {
		int toRemove = -1;
		
		for (int i = 0; i < data.size(); i++) {
			if (data.elementAt(i).getID() == id) {
				toRemove = i;
			}
		}
		
		if (toRemove != -1) {
			File file = new File(data.elementAt(toRemove).getFilePath());
			
			if (file.exists())
				file.delete();
			
			data.remove(toRemove);
			
			updateModelListeners();
		}
	}
	
	public void addPhotoModelListener(PhotoModelListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
			updateModelListener(listener);
		}
	}
	
	public boolean removePhotoModelListener(PhotoModelListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
			return true;
		}
		
		return false;
	}
	
	public void updateModelListener(PhotoModelListener listener) {
		try {
			listener.photosChanged(data);
			listener.tagsChanged(tags);
			listener.groupsChanged(groups);
		}
		
		catch (Exception e) {}
	}
	
	public void updateModelListeners() {
		for (int i = 0; i < listeners.size(); i++) {
			updateModelListener(listeners.elementAt(i));
		}
		
		saveData();
	}
	
	private void saveData() {
		try {
			File file = new File("data");
			
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(data);
			output.flush();
			output.close();
		}
		
		catch (Exception e) {}
		
		try {
			File file = new File("tags");
			
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(tags);
			output.flush();
			output.close();
		}
		
		catch (Exception e) {}
		
		try {
			File file = new File("groups");
			
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(groups);
			output.flush();
			output.close();
		}
		
		catch (Exception e) {}
	}
}