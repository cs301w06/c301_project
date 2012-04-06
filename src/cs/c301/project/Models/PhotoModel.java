package cs.c301.project.Models;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import cs.c301.project.Data.PhotoEntry;

/**
 * This is the model that holds the sqlite database handlers for our photos.
 * It supports fully encryption of photos as well as advanced features such as date search
 * @author yhu3
 */

@SuppressWarnings("static-access")
public class PhotoModel {
	private PhotoModelHelper photoModelHelper;
	private SQLiteDatabase photoDatabase;
	
	public PhotoModel(Context context) {
		photoModelHelper = new PhotoModelHelper(context);
	}
	
	public void setUser(String username) {
		if (!username.equals("doctor")) {
			photoModelHelper.setUser(username);
			
			try {
				photoDatabase.close();
			}
			
			catch (Exception e) {}
			
			open();
		}
	}
	
	public void open() throws SQLException {
		photoDatabase = photoModelHelper.getWritableDatabase();
	}
	
	public void close() {
		photoDatabase.close();
	}
	
	public PhotoEntry getPhotoByID(int id) {
		Cursor cursor = photoDatabase.rawQuery("SELECT * FROM " + photoModelHelper.photosTable + " WHERE " + photoModelHelper.photosTableID + " = ?", new String[] {"" + id});
		
		PhotoEntry entry = new PhotoEntry();
		
		if (cursor.moveToNext()) {
			
			int ID = cursor.getInt(cursor.getColumnIndex(photoModelHelper.photosTableID));
			byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(photoModelHelper.photosTablePhoto));
			byte[] thumbBlob = cursor.getBlob(cursor.getColumnIndex(photoModelHelper.photosTableThumbnail));
			String annotation = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableAnnotation));
			String group = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableGroup));
			String tags = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableTags));
			String doctorTags = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableDoctorTags));
			Date date = null;
			
			try {
				date = new Date(Long.parseLong((cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableDate)))));
			} 
			
			catch (Exception e) {}
			
			entry.setID(ID);
			entry.setAnnotation(annotation);
			entry.setGroup(group);
			entry.setTags(tags);
			entry.setDoctorTags(doctorTags);
			entry.setBitmapBytes(imageBlob);
			entry.setThumbnailBytes(thumbBlob);
			entry.setDate(date);
		} 

		cursor.close();
		
		return entry;
	}
	
	public boolean addGroup(String group) {
		Vector<String> existingGroups = getGroups();
		
		if (!existingGroups.contains(group)) {
			ContentValues entry = new ContentValues();
			entry.put(photoModelHelper.groupsTableName, group);
			long row = photoDatabase.insert(photoModelHelper.groupsTable, null, entry);
			
			if (row != -1)
				return true;
		}
		
		return false;
	}
	
	public boolean addTag(String tag) {
		Vector<String> existingTags = getTags();
		
		if (!existingTags.contains(tag)) {
			ContentValues entry = new ContentValues();
			entry.put(photoModelHelper.tagsTableName, tag);
			long row = photoDatabase.insert(photoModelHelper.tagsTable, null, entry);
			
			if (row != -1)
				return true;
		}
		
		return false;
	}
	
	public boolean addDoctorTag(String tag) {
		Vector<String> existingTags = getDoctorTags();
		
		if (!existingTags.contains(tag)) {
			ContentValues entry = new ContentValues();
			entry.put(photoModelHelper.doctorTagsTableName, tag);
			long row = photoDatabase.insert(photoModelHelper.doctorTagsTable, null, entry);
			
			if (row != -1)
				return true;
		}
		
		return false;
	}
	
	public boolean addPhoto(PhotoEntry photo) {
		ContentValues entry = new ContentValues();
		entry.put(photoModelHelper.photosTablePhoto, photo.getBitmapBytes());		
		entry.put(photoModelHelper.photosTableGroup, photo.getGroup());
		entry.put(photoModelHelper.photosTableTags, photo.getTagsForDatabase());
		entry.put(photoModelHelper.photosTableDoctorTags, photo.getDoctorTagsForDatabase());
		entry.put(photoModelHelper.photosTableDate, "" + photo.getDate().getTime());
		entry.put(photoModelHelper.photosTableAnnotation, photo.getAnnotation());
		entry.put(photoModelHelper.photosTableThumbnail, photo.getThumbnailBytes());
		
		long row = photoDatabase.insert(photoModelHelper.photosTable, null, entry);
		
		if (row != -1)
			return true;
		
		return false;
	}
	
	public boolean removeGroup(String group) {
		int row = photoDatabase.delete(photoModelHelper.groupsTable, photoModelHelper.groupsTableName + " = ?", new String[] {group});
		//need to refactor and remove photos with this group associated
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public boolean removeTag(String tag) {
		int row = photoDatabase.delete(photoModelHelper.tagsTable, photoModelHelper.tagsTableName + " = ?", new String[] {tag});
		//need to refactor and remove tags from photo entries
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public boolean removeDoctorTag(String tag) {
		int row = photoDatabase.delete(photoModelHelper.doctorTagsTable, photoModelHelper.doctorTagsTableName + " = ?", new String[] {tag});
		//need to refactor and remove tags from photo entries
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public boolean removePhoto(int id) {
		int row = photoDatabase.delete(photoModelHelper.photosTable, photoModelHelper.photosTableID + " = ?", new String[] {"" + id});
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public Vector<String> getGroups() {
		Vector<String> s = new Vector<String>();
		
		Cursor cursor = photoDatabase.query(true, photoModelHelper.groupsTable, new String[] {photoModelHelper.groupsTableID, photoModelHelper.groupsTableName}, null, null, null, null, null, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				s.add(cursor.getString(cursor.getColumnIndex(photoModelHelper.groupsTableName)));
			} else {
				loop = false;
			}
		}
		
		cursor.close();
		
		return s;
	}
	
	public Vector<String> getTags() {
		Vector<String> s = new Vector<String>();
		
		Cursor cursor = photoDatabase.query(true, photoModelHelper.tagsTable, new String[] {photoModelHelper.tagsTableID, photoModelHelper.tagsTableName}, null, null, null, null, null, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				s.add(cursor.getString(cursor.getColumnIndex(photoModelHelper.tagsTableName)));
			} else {
				loop = false;
			}
		}
		
		cursor.close();
		
		return s;
	}
	
	public Vector<String> getDoctorTags() {
		Vector<String> s = new Vector<String>();
		
		Cursor cursor = photoDatabase.query(true, photoModelHelper.doctorTagsTable, new String[] {photoModelHelper.doctorTagsTableID, photoModelHelper.doctorTagsTableName}, null, null, null, null, null, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				s.add(cursor.getString(cursor.getColumnIndex(photoModelHelper.tagsTableName)));
			} else {
				loop = false;
			}
		}
		
		cursor.close();
		
		return s;
	}
	
	public Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery) {
		Vector<PhotoEntry> photoEntries = new Vector<PhotoEntry>();
		String query = "";
		boolean searchAll = false;
		Cursor cursor = null;
		
		if ((groupsQuery.size() == 0 || groupsQuery == null) && (tagsQuery.size() == 0 || tagsQuery == null))
			searchAll = true;
		
		if (!searchAll) {
			Vector<String> selectionArgs = new Vector<String>();
			
			if (groupsQuery != null && groupsQuery.size() > 0) {
				if (groupsQuery.elementAt(0) != null) {
					query = photoModelHelper.photosTableGroup + " = ? ";
					selectionArgs.add(groupsQuery.elementAt(0));
					
					if (groupsQuery.size() > 1) {
						for (int i = 1; i < groupsQuery.size(); i++) {
							query += "AND " + photoModelHelper.photosTableGroup + " = ? ";
							selectionArgs.add(groupsQuery.elementAt(i));
						}
					}
				}
			}
			
			if (tagsQuery != null && tagsQuery.size() > 0) {
				if (tagsQuery.elementAt(0) != null) {
					if (query.equals(""))
						query = photoModelHelper.photosTableTags + " = ? ";
					else
						query += "AND " + photoModelHelper.photosTableTags + " = ? ";
					
					selectionArgs.add(tagsQuery.elementAt(0));
					
					if (tagsQuery.size() > 1) {
						for (int i = 1; i < tagsQuery.size(); i++) {
							query += "AND " + photoModelHelper.photosTableTags + " = ? ";
							selectionArgs.add(tagsQuery.elementAt(i));
						}
					}
				}
			}
			
			String[] arguments = new String[selectionArgs.size()];
			String logging = "";
			
			for (int j = 0; j < selectionArgs.size(); j++) {
				arguments[j] = selectionArgs.elementAt(j);
				logging += arguments[j] + " ";
			}
			
			Log.e("PhotoModel", query);
			Log.e("PhotoModel", logging);
			
			cursor = photoDatabase.rawQuery("SELECT * FROM " + photoModelHelper.photosTable + " WHERE " + query, arguments);
		} else {
			cursor = photoDatabase.rawQuery("SELECT * FROM " + photoModelHelper.photosTable, null);
		}
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				PhotoEntry entry = new PhotoEntry();
				int ID = cursor.getInt(cursor.getColumnIndex(photoModelHelper.photosTableID));
				byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(photoModelHelper.photosTablePhoto));
				byte[] thumbBlob = cursor.getBlob(cursor.getColumnIndex(photoModelHelper.photosTableThumbnail));
				String annotation = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableAnnotation));
				String group = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableGroup));
				String tags = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableTags));
				String doctorTags = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableDoctorTags));
				Date date = null;
				
				try {
					date = new Date(Long.parseLong((cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableDate)))));
				} 
				
				catch (Exception e) {}
				
				entry.setID(ID);
				entry.setAnnotation(annotation);
				entry.setGroup(group);
				entry.setTags(tags);
				entry.setDoctorTags(doctorTags);
				entry.setBitmapBytes(imageBlob);
				entry.setThumbnailBytes(thumbBlob);
				entry.setDate(date);
				
				photoEntries.add(entry);
			} else {
				loop = false;
			}
		}
		
		cursor.close();
		
		return photoEntries;
	}
	
	public Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery, Date startDate, Date endDate) {
		Vector<PhotoEntry> photos = getPhotosByValues(groupsQuery, tagsQuery);
		
		if (startDate != null && endDate != null && startDate.compareTo(endDate) <= 0) {
			Vector<Integer> toRemove = new Vector<Integer>(0, 1);
			
			for (int i = 0; i < photos.size(); i++) {
				PhotoEntry entry = photos.elementAt(i);
				
				if (entry.getDate().before(startDate) || entry.getDate().after(endDate))
					toRemove.add(i);
			}
			
			for (int j = toRemove.size() - 1; j >= 0; j--) {
				photos.remove(toRemove.elementAt(j));
			}
			
			photos.trimToSize();
		}
		
		return photos;
	}
	
	public boolean updatePhoto(PhotoEntry photoEntry) {
		ContentValues entry = new ContentValues();
		entry.put(photoModelHelper.photosTableGroup, photoEntry.getGroup());
		entry.put(photoModelHelper.photosTableTags, photoEntry.getTagsForDatabase());
		entry.put(photoModelHelper.photosTableDate, "" + photoEntry.getDate().getTime());
		entry.put(photoModelHelper.photosTableAnnotation, photoEntry.getAnnotation());
		entry.put(photoModelHelper.photosTableDoctorTags, photoEntry.getDoctorTagsForDatabase());
		
		int row = photoDatabase.update(photoModelHelper.photosTable, entry, photoModelHelper.photosTableID + " = ?", new String[] {"" + photoEntry.getID()});
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public Uri getTemporaryImage() {
		return Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + "tmp.jpg")); 
	}
}
