package cs.c301.project.Models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class PhotoModel extends SQLiteOpenHelper {
//	private Vector<PhotoModelListener> listeners;
	
	private static final String databaseName = "photosDB";
	
	private static final String groupsTable = "Groups";
	private static final String groupsTableID = "ID";
	private static final String groupsTableName = "Name";
	
	private static final String tagsTable = "Tags";
	private static final String tagsTableID = "ID";
	private static final String tagsTableName = "Name";
	
	private static final String photosTable = "Photos";
	private static final String photosTableID = "ID";
	private static final String photosTablePhoto = "Photo";
	private static final String photosTableGroup = "Group";
	private static final String photosTableTags = "Tags";
	private static final String photosTableDate = "Date";
	
	private static final int databaseVersion = 1;
	
	private SQLiteDatabase photoDatabase;
	
	public PhotoModel(Context context) {
		super(context, databaseName, null, databaseVersion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("CREATE TABLE " + groupsTable + " (" + groupsTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + groupsTableName + " TEXT NOT NULL UNIQUE)");
		
		database.execSQL("CREATE TABLE " + tagsTable + " (" + tagsTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + tagsTableName + " TEXT NOT NULL UNIQUE)");
		
		database.execSQL("CREATE TABLE " + photosTable + " (" + photosTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
		photosTablePhoto + " BLOB, " + photosTableGroup + " TEXT, " + photosTableTags + " TEXT, " + photosTableDate + " TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + groupsTable);
		database.execSQL("DROP TABLE IF EXISTS " + tagsTable);
		database.execSQL("DROP TABLE IF EXISTS " + photosTable);
		onCreate(database);
	}
	
	public PhotoModel open() throws SQLException {
		photoDatabase = getWritableDatabase();
		
		return this;
	}
	
	public boolean addGroup(String group) {
		ContentValues entry = new ContentValues();
		entry.put(groupsTableName, group);
		long row = photoDatabase.insert(groupsTable, null, entry);
		
		if (row != -1)
			return true;
		
		return false;
	}
	
	public boolean addTag(String tag) {
		ContentValues entry = new ContentValues();
		entry.put(tagsTableName, tag);
		long row = photoDatabase.insert(tagsTable, null, entry);
		
		if (row != -1)
			return true;
		
		return false;
	}
	
	public boolean addPhoto(PhotoEntry photo) {
		ContentValues entry = new ContentValues();
		ByteArrayOutputStream photoOutput = new ByteArrayOutputStream();
		photo.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, photoOutput);
		entry.put(photosTablePhoto, photoOutput.toByteArray());
		
		try {
			photoOutput.close();
		} 
		
		catch (IOException e) {}
		
		entry.put(photosTableGroup, photo.getGroup());
		entry.put(photosTableTags, photo.getTagsForDatabase());
		
		Date date = new Date();
		
		entry.put(photosTableDate, date.toString());
		
		long row = photoDatabase.insert(photosTable, null, entry);
		
		if (row != -1)
			return true;
		
		return false;
	}
	
	public boolean removeGroup(String group) {
		int row = photoDatabase.delete(groupsTable, groupsTableName + " = ?", new String[] {group});
		//need to refactor and remove photos with this group associated
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public boolean removeTag(String tag) {
		int row = photoDatabase.delete(tagsTable, tagsTableName + " = ?", new String[] {tag});
		//need to refactor and remove tags from photo entries
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public boolean removePhoto(int id) {
		int row = photoDatabase.delete(photosTable, photosTableID + " = ?", new String[] {"" + id});
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public Vector<String> getGroups() {
		Vector<String> s = new Vector<String>();
		
		Cursor cursor = photoDatabase.query(true, groupsTable, new String[] {groupsTableID, groupsTableName}, null, null, null, null, null, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				s.add(cursor.getString(cursor.getColumnIndex(groupsTableName)));
			} else {
				loop = false;
			}
		}
		
		return s;
	}
	
	public Vector<String> getTags() {
		Vector<String> s = new Vector<String>();
		
		Cursor cursor = photoDatabase.query(true, tagsTable, new String[] {tagsTableID, tagsTableName}, null, null, null, null, null, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				s.add(cursor.getString(cursor.getColumnIndex(tagsTableName)));
			} else {
				loop = false;
			}
		}
		
		return s;
	}
	
	public Vector<PhotoEntry> getAllPhotos() {
		Vector<PhotoEntry> photoEntries = new Vector<PhotoEntry>();
		
		Cursor cursor = photoDatabase.query(true, photosTable, new String[] {photosTableID, photosTablePhoto, photosTableGroup, photosTableTags, photosTableDate}, null, null, null, null, null, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				PhotoEntry entry = new PhotoEntry();
				int ID = cursor.getInt(cursor.getColumnIndex(photosTableID));
				byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(photosTablePhoto));
				Bitmap image = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
				String group = cursor.getString(cursor.getColumnIndex(photosTableGroup));
				String tags = cursor.getString(cursor.getColumnIndex(photosTableTags));
				Date date = new Date();
				
				try {
					date = DateFormat.getTimeInstance().parse(cursor.getString(cursor.getColumnIndex(photosTableDate)));
				} 
				
				catch (ParseException e) {}
				
				entry.setID(ID);
				entry.setGroup(group);
				entry.setTags(tags);
				entry.setBitmap(image);
				entry.setDate(date);
				
				photoEntries.add(entry);
			} else {
				loop = false;
			}
		}
		
		return photoEntries;
	}
	
	public Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery) {
		Vector<PhotoEntry> photoEntries = new Vector<PhotoEntry>();
		String query = "";
		Vector<String> selectionArgs = new Vector<String>();
		
		if (groupsQuery != null && groupsQuery.size() > 0) {
			query = photosTableGroup + " LIKE '% ? %' ";
			selectionArgs.add(groupsQuery.elementAt(0));
			
			if (groupsQuery.size() > 1) {
				for (int i = 1; i < groupsQuery.size(); i++) {
					query += photosTableGroup + " LIKE '% ? %' ";
					selectionArgs.add(groupsQuery.elementAt(i));
				}
			}
		}
		
		if (tagsQuery != null && tagsQuery.size() > 0) {
			if (query.equals(""))
				query = photosTableTags + " LIKE '% ? %' ";
			else
				query += "AND " + photosTableTags + " LIKE '% ? %' ";
			
			selectionArgs.add(tagsQuery.elementAt(0));
			
			if (tagsQuery.size() > 1) {
				for (int i = 1; i < tagsQuery.size(); i++) {
					query += photosTableTags + " LIKE '% ? %' ";
					selectionArgs.add(tagsQuery.elementAt(i));
				}
			}
		}
		
		if (query.equals(""))
			return getAllPhotos();
		
		String[] arguments = new String[selectionArgs.size()];
		
		for (int j = 0; j < selectionArgs.size(); j++) {
			arguments[j] = selectionArgs.elementAt(j);
		}
		
		Cursor cursor = photoDatabase.query(true, photosTable, new String[] {photosTableID, photosTablePhoto, photosTableGroup, photosTableTags, photosTableDate}, query, arguments, null, null, null, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				PhotoEntry entry = new PhotoEntry();
				int ID = cursor.getInt(cursor.getColumnIndex(photosTableID));
				byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(photosTablePhoto));
				Bitmap image = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
				String group = cursor.getString(cursor.getColumnIndex(photosTableGroup));
				String tags = cursor.getString(cursor.getColumnIndex(photosTableTags));
				Date date = new Date();
				
				try {
					date = DateFormat.getTimeInstance().parse(cursor.getString(cursor.getColumnIndex(photosTableDate)));
				} 
				
				catch (ParseException e) {}
				
				entry.setID(ID);
				entry.setGroup(group);
				entry.setTags(tags);
				entry.setBitmap(image);
				entry.setDate(date);
				
				photoEntries.add(entry);
			} else {
				loop = false;
			}
		}
		
		return photoEntries;
	}
	
	public boolean updatePhoto(PhotoEntry photoEntry) {
		ContentValues entry = new ContentValues();
		ByteArrayOutputStream photoOutput = new ByteArrayOutputStream();
		photoEntry.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, photoOutput);
		entry.put(photosTablePhoto, photoOutput.toByteArray());
		
		try {
			photoOutput.close();
		} 
		
		catch (IOException e) {}
		
		entry.put(photosTableGroup, photoEntry.getGroup());
		entry.put(photosTableTags, photoEntry.getTagsForDatabase());
		entry.put(photosTableDate, photoEntry.getDate().toString());
		
		int row = photoDatabase.update(photosTable, entry, photosTableID + " = ?", new String[] {"" + photoEntry.getID()});
		
		if (row != 0)
			return true;
		
		return false;
	}
//	
//	/**
//	 * Constructor for the model. Creates new, empty vectors for containing
//	 * our photos, tags, groups, and model listeners.
//	 * 
//	 * @param f The environment path file
//	 */
//	@SuppressWarnings("unchecked")
//	public PhotoModel() {
//		listeners = new Vector<PhotoModelListener>(0, 1);
//		data = new Vector<PhotoEntry>(0, 1);
//		tags = new Vector<String>(0, 1);
//		groups = new Vector<String>(0, 1);
//		tracker = 0;
//		
//		try {
//			//TODO: check if the image still exists or not; purge otherwise
//			File file = new File("data.save");
//			
//			if (file.exists()) {
//				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
//				data = (Vector<PhotoEntry>)input.readObject();
//				input.close();
//				
//				for (int i = 0; i < data.size(); i++) {
//					data.elementAt(i).setID(i);
//				}
//				
//				tracker = data.size() - 1;
//			}
//		}
//		
//		catch (Exception e) {}
//		
//		try {
//			File file = new File("tags.save");
//			
//			if (file.exists()) {
//				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
//				tags = (Vector<String>)input.readObject();
//				input.close();
//			}
//		}
//		
//		catch (Exception e) {}
//		
//		try {
//			File file = new File("groups.save");
//			
//			if (file.exists()) {
//				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
//				groups = (Vector<String>)input.readObject();
//				input.close();
//			}
//		}
//		
//		catch (Exception e) {}
//	}
//	
//	/**
//	 * Gets the last photo we took with the camera. Checks if our tmp folder exists
//	 * and finds the image within. Returns the file as a photo entry to be added to the
//	 * data elsewhere.
//	 * 
//	 * @return Temporary image saved by the camera
//	 */
//	public PhotoEntry getTemporaryImage() {
//		PhotoEntry entry = new PhotoEntry(null, null, "tmp");
//		
//		//check if tmp folder exists
//		File test = new File(filePath + File.separator + "tmp");
//		if (!test.exists())
//			test.mkdir();
//		
//		entry.setFilePath(filePath + File.separator + "tmp" + File.separator + entry.getSaveName());
//		
//		return entry;
//	}
//	
//	/**
//	 * Getter for the vector containing our list of groups.
//	 * 
//	 * @return Vector of the folders we can store photos in
//	 */
//	public Vector<String> getGroups() {
//		return groups;
//	}
//	
//	/**
//	 * Checks to see if the specified group already exists. If it doesn't
//	 * the group is added and a call for the listeners to be updated is made.
//	 * 
//	 * @param group Name of the group we wish to add
//	 */
//	public boolean addGroup(String group) {
//		if (!groups.contains(group)) {
//			groups.add(group);
//			
//			updateModelListeners();
//			return true;
//		}
//		
//		return false;
//	}
//	
//	/**
//	 * Checks to see if the specified group exists within our group list. If the group
//	 * is there, we remove the group from all data that references it and delete the group.
//	 * 
//	 * @param group Name of the group we wish to delete
//	 */
//	public void removeGroup(String group) {
//		if (groups.contains(group)) {
//			for (int i = 0; i < data.size(); i++) {
//				data.elementAt(i).setGroup("");
//			}
//			
//			groups.remove(group);
//			
//			updateModelListeners();
//		}
//	}
//	
//	/**
//	 * Getter for the vector containing our tags
//	 * 
//	 * @return Gets the vector of tags attached to a photo
//	 */
//	public Vector<String> getTags() {
//		return tags;
//	}
//	
//	/**
//	 * Add a specified tag to our tag list. First checks the tag list
//	 * to see if the tag already exists. If it doesn't the tag is added and
//	 * the update listeners method is called.
//	 * 
//	 * @param tag The tag which we wish to add to a photo
//	 */
//	public boolean addTag(String tag) {
//		if (!tags.contains(tag)) {
//			tags.add(tag);
//			
//			updateModelListeners();
//			
//			return true;
//		}
//		
//		return false;
//	}
//	
//	/**
//	 * Checks to see if the specified tag exists in our list. If it does,
//	 * the tag is removed from all data that references it, and then the
//	 * tag itself is removed from the tag list. A call is sent to update model
//	 * listeners.
//	 * 
//	 * @param tag The tag which we wish to remove from the photo
//	 */
//	public void removeTag(String tag) {
//		if (tags.contains(tag)) {
//			for (int i = 0; i < data.size(); i++) {
//				data.elementAt(i).removeTag(tag);
//			}
//			
//			tags.remove(tag);
//			
//			updateModelListeners();
//		}
//	}
//	
//	/**
//	 * Getter for the photo vector
//	 * 
//	 * @return Returns our photos in the form a vector
//	 */
//	public Vector<PhotoEntry> getPhotos() {
//		return data;
//	}
//	
//	/**
//	 * Getter for the file path
//	 * 
//	 * @return Returns file path as a string
//	 */
//	public String getFilePath() {
//		return filePath;
//	}
//	
//	/**
//	 * If the photo directory does not exist the directory is created. If the group is not
//	 * "tmp" then the method must grab the file path and set the photo's path. The photo is compressed
//	 *  into a jpeg. Finally, the photo is added to the data list. If the photo already exists in the tmp
//	 *  folder, the photo is added directly to the data. Afterwards, the call is made to update model listeners.
//	 * 
//	 * @param photo The photo we wish to add
//	 */
//	public void addPhoto(PhotoEntry photo) {
//		photo.setID(tracker);
//		
//		//make folder exist
//		File test = new File(filePath + File.separator + photo.getGroup());
//		if (!test.exists())
//			test.mkdir();
//		
//		if (!photo.getGroup().equals("tmp")) {
//			photo.setFilePath(filePath + File.separator + photo.getGroup() + File.separator + photo.getSaveName());
//			
//			try {
//				File file = new File(photo.getFilePath());
//				OutputStream output = new FileOutputStream(file);
//	    		photo.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, output);
//	    		output.close();
//	    		photo.deleteBitmap();
//			}
//			
//			catch (Exception e) {}
//		}
//		
//		data.add(photo);
//		tracker++;
//		
//		updateModelListeners();
//	}
//	
//	/**
//	 * Current place holder for photo updates
//	 * 
//	 * @param entry The specific photo we wish to update
//	 */
//	//TODO: this function will update tags, update groups, update the storage location, and update the image itself all based on the parameters in PhotoEntry, id is used to match
//	public void updatePhoto(PhotoEntry entry) {
//		int element = -1;
//		
//		for (int i = 0; i < data.size(); i++) {
//			if (data.elementAt(i).getID() == entry.getID())
//				element = i;
//		}
//		
//		if (element != -1) {
//			PhotoEntry old = data.elementAt(element);
//			
//			if (!entry.getGroup().equals(old.getGroup())) {
//				old.setGroup(entry.getGroup());
//			}
//			
//			boolean tagsSame = true;
//			
//			Vector<String> oldTags = old.getTags();
//			Vector<String> newTags = entry.getTags();
//			
//			if (oldTags.size() != newTags.size())
//				tagsSame = false;
//			else {
//				for (int i = 0; i < oldTags.size(); i++) {
//					if (!oldTags.elementAt(i).equals(newTags.elementAt(i)))
//						tagsSame = false;
//				}
//			}
//			
//			if (!tagsSame) {
//				old.setTags(newTags);
//			}
//			
//			if (!entry.getFilePath().equals(old.getFilePath())) {
//				File file = new File(old.getFilePath());
//				
//				if (file.exists()) {
//					Bitmap image = BitmapFactory.decodeFile(old.getFilePath());
//					file = new File(entry.getFilePath());
//					
//					if (entry.getBitmap() != null)
//						image = entry.getBitmap();
//					
//					try {
//						OutputStream output = new FileOutputStream(file);
//			    		image.compress(Bitmap.CompressFormat.JPEG, 100, output);
//			    		output.close();
//					}
//					
//					catch (Exception e) {}
//					
//					
//				}
//				
//				old.setFilePath(entry.getFilePath());
//			}
//			
//			data.set(element, old);
//		}
//	}
//	
//	/**
//	 * Finds a photo specified by an id in the data list. If the photo is found
//	 * in the list, it is removed and the listeners are updated.
//	 * 
//	 * @param id The unique id of the photo we wish to remove
//	 */
//	public void removePhoto(int id) {
//		int toRemove = -1;
//		
//		for (int i = 0; i < data.size(); i++) {
//			if (data.elementAt(i).getID() == id) {
//				toRemove = i;
//			}
//		}
//		
//		if (toRemove != -1) {
//			File file = new File(data.elementAt(toRemove).getFilePath());
//			
//			if (file.exists())
//				file.delete();
//			
//			data.remove(toRemove);
//			
//			updateModelListeners();
//		}
//	}
//	
//	public Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery) {
//		Vector<PhotoEntry> entries = new Vector<PhotoEntry>();
//		
//		for (int i = 0; i < groupsQuery.size(); i++) {
//			for (int j = 0; j < data.size(); j++) {
//				if (data.elementAt(j).getGroup().equalsIgnoreCase(groupsQuery.elementAt(i))) {
//					if (!entries.contains(data.elementAt(j))) {
//						entries.add(data.elementAt(j));
//					}
//				}
//			}
//		}
//		
//		for (int i = 0; i < tagsQuery.size(); i++) {
//			for (int j = 0; j < data.size(); j++) {
//				Vector<String> elementTags = data.elementAt(j).getTags();
//				
//				if (elementTags.contains(tagsQuery.elementAt(i))) {
//					if (!entries.contains(data.elementAt(j))) {
//						entries.add(data.elementAt(j));
//					}
//				}
//			}
//		}
//
//		return entries;
//	}
//	
//	/**
//	 * Adds a specific class to the model listener. Check to see if the listener already
//	 * exists within the listener list, and if it does not then add it.
//	 * 
//	 * @param listener Class which we want to listen to the model for changes
//	 */
//	public void addPhotoModelListener(PhotoModelListener listener) {
//		if (!listeners.contains(listener)) {
//			listeners.add(listener);
//			updateModelListener(listener);
//		}
//	}
//	
//	/**
//	 * Removes a specific class from the model listeners. Check to see if the listener
//	 * exists in the listener list, and remove it if it does.
//	 * 
//	 * @param listener Class which we no longer want to listen for changes
//	 * @return true if the listener is removed, false if the listener does not exist
//	 */
//	public boolean removePhotoModelListener(PhotoModelListener listener) {
//		if (listeners.contains(listener)) {
//			listeners.remove(listener);
//			return true;
//		}
//		
//		return false;
//	}
//	
//	/**
//	 * Method which updates a specific listener. Updates any changes to data, tags,
//	 * and groups.
//	 * 
//	 * @param listener Class which we wish to update upon changes to the model
//	 */
//	public void updateModelListener(PhotoModelListener listener) {
//		try {
//			listener.photosChanged(data);
//			listener.tagsChanged(tags);
//			listener.groupsChanged(groups);
//		}
//		
//		catch (Exception e) {}
//	}
//	
//	/**
//	 * Default for updating the model listeners. Updates all listeners when
//	 * no listener is specified.
//	 */
//	public void updateModelListeners() {
//		for (int i = 0; i < listeners.size(); i++) {
//			updateModelListener(listeners.elementAt(i));
//		}
//		
//		saveData();
//	}
//	
//	/**
//	 * Saves all "data", "tags", and "groups" to the disk.
//	 */
//	private void saveData() {
//		try {
//			File file = new File("data.save");
//			
//			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
//			output.writeObject(data);
//			output.flush();
//			output.close();
//		}
//		
//		catch (Exception e) {}
//		
//		try {
//			File file = new File("tags.save");
//			
//			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
//			output.writeObject(tags);
//			output.flush();
//			output.close();
//		}
//		
//		catch (Exception e) {}
//		
//		try {
//			File file = new File("groups.save");
//			
//			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
//			output.writeObject(groups);
//			output.flush();
//			output.close();
//		}
//		
//		catch (Exception e) {}
//	}
}