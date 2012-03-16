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

/**
 * Model for the application. Mediates interaction between other classes and data.
 * Saves and loads data from the disk.
 * <p>
 * Javadoc by esteckle
 * 
 * @author yhu3
 *
 */
@SuppressWarnings("serial")
public class PhotoModel implements Serializable {
	private Vector<PhotoModelListener> listeners;
	private Vector<String> tags; //stores tags data
	private Vector<String> groups; //stores groups data
	private Vector<PhotoEntry> data; //stores photo data
	private int tracker; //for a new photo id
	private String filePath; //external sd card address passed down
	
	/**
	 * Constructor for the model. Creates new, empty vectors for containing
	 * our photos, tags, groups, and model listeners.
	 * 
	 * @param f The environment path file
	 */
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
	
	/**
	 * Gets the last photo we took with the camera. Checks if our tmp folder exists
	 * and finds the image within. Returns the file as a photo entry to be added to the
	 * data elsewhere.
	 * 
	 * @return Temporary image saved by the camera
	 */
	public PhotoEntry getTemporaryImage() {
		PhotoEntry entry = new PhotoEntry(null, null, "tmp");
		
		//check if tmp folder exists
		File test = new File(filePath + File.separator + "tmp");
		if (!test.exists())
			test.mkdir();
		
		entry.setFilePath(filePath + File.separator + "tmp" + File.separator + entry.getSaveName());
		
		return entry;
	}
	
	/**
	 * Getter for the vector containing our list of groups.
	 * 
	 * @return Vector of the folders we can store photos in
	 */
	public Vector<String> getGroups() {
		return groups;
	}
	
	/**
	 * Checks to see if the specified group already exists. If it doesn't
	 * the group is added and a call for the listeners to be updated is made.
	 * 
	 * @param group Name of the group we wish to add
	 */
	public void addGroup(String group) {
		if (!groups.contains(group)) {
			groups.add(group);
			
			updateModelListeners();
		}
	}
	
	/**
	 * Checks to see if the specified group exists within our group list. If the group
	 * is there, we remove the group from all data that references it and delete the group.
	 * 
	 * @param group Name of the group we wish to delete
	 */
	public void removeGroup(String group) {
		if (groups.contains(group)) {
			for (int i = 0; i < data.size(); i++) {
				data.elementAt(i).setGroup("");
			}
			
			groups.remove(group);
			
			updateModelListeners();
		}
	}
	
	/**
	 * Getter for the vector containing our tags
	 * 
	 * @return Gets the vector of tags attached to a photo
	 */
	public Vector<String> getTags() {
		return tags;
	}
	
	/**
	 * Add a specified tag to our tag list. First checks the tag list
	 * to see if the tag already exists. If it doesn't the tag is added and
	 * the update listeners method is called.
	 * 
	 * @param tag The tag which we wish to add to a photo
	 */
	public void addTag(String tag) {
		if (!tags.contains(tag)) {
			tags.add(tag);
			
			updateModelListeners();
		}
	}
	
	/**
	 * Checks to see if the specified tag exists in our list. If it does,
	 * the tag is removed from all data that references it, and then the
	 * tag itself is removed from the tag list. A call is sent to update model
	 * listeners.
	 * 
	 * @param tag The tag which we wish to remove from the photo
	 */
	public void removeTag(String tag) {
		if (tags.contains(tag)) {
			for (int i = 0; i < data.size(); i++) {
				data.elementAt(i).removeTag(tag);
			}
			
			tags.remove(tag);
			
			updateModelListeners();
		}
	}
	
	/**
	 * Getter for the photo vector
	 * 
	 * @return Returns our photos in the form a vector
	 */
	public Vector<PhotoEntry> getPhotos() {
		return data;
	}
	
	/**
	 * Getter for the file path
	 * 
	 * @return Returns file path as a string
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * If the photo directory does not exist the directory is created. If the group is not
	 * "tmp" then the method must grab the file path and set the photo's path. The photo is compressed
	 *  into a jpeg. Finally, the photo is added to the data list. If the photo already exists in the tmp
	 *  folder, the photo is added directly to the data. Afterwards, the call is made to update model listeners.
	 * 
	 * @param photo The photo we wish to add
	 */
	public void addPhoto(PhotoEntry photo) {
		photo.setID(tracker);
		
		//make folder exist
		File test = new File(filePath + File.separator + photo.getGroup());
		if (!test.exists())
			test.mkdir();
		
		if (!photo.getGroup().equals("tmp")) {
			photo.setFilePath(filePath + File.separator + photo.getGroup() + File.separator + photo.getSaveName());
			
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
	
	/**
	 * Current place holder for photo updates
	 * 
	 * @param entry The specific photo we wish to update
	 */
	//TODO: this function will update tags, update groups, update the storage location, and update the image itself all based on whether they are null or not, id is used to match
	public void updatePhoto(PhotoEntry entry) {
		
	}
	
	/**
	 * Removes a photo from our data and updates the model listeners. 
	 * 
	 * @param id The unique id of the photo we wish to remove
	 */
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
	
	/**
	 * Adds a specific class to the model listener.
	 * 
	 * @param listener Class which we want to listen to the model for changes
	 */
	public void addPhotoModelListener(PhotoModelListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
			updateModelListener(listener);
		}
	}
	
	/**
	 * Removes a specific class from the model listeners.
	 * 
	 * @param listener Class which we no longer want to listen for changes
	 * @return Returns true if the listener is removed, returns false if the listener does not exist
	 */
	public boolean removePhotoModelListener(PhotoModelListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method which updates a specific listener. Updates any changes to data, tags,
	 * and groups.
	 * 
	 * @param listener Class which we wish to update upon changes to the model
	 */
	public void updateModelListener(PhotoModelListener listener) {
		try {
			listener.photosChanged(data);
			listener.tagsChanged(tags);
			listener.groupsChanged(groups);
		}
		
		catch (Exception e) {}
	}
	
	/**
	 * Default for updating the model listeners. Updates all listeners when
	 * no listener is specified.
	 */
	public void updateModelListeners() {
		for (int i = 0; i < listeners.size(); i++) {
			updateModelListener(listeners.elementAt(i));
		}
		
		saveData();
	}
	
	/**
	 * Saves all "data", "tags", and "groups" to the disk.
	 */
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