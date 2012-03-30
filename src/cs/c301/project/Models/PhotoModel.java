package cs.c301.project.Models;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import cs.c301.project.Data.PhotoEntry;

public class PhotoModel {
	private PhotoModelHelper photoModelHelper;
	private SQLiteDatabase photoDatabase;
	
	public PhotoModel(Context context) {
		photoModelHelper = new PhotoModelHelper(context);
		open();
	}
	
	public void open() throws SQLException {
		photoDatabase = photoModelHelper.getWritableDatabase();
	}
	
	public void close() {
		photoDatabase.close();
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
	
	public boolean addPhoto(PhotoEntry photo) {
		ContentValues entry = new ContentValues();
		ByteArrayOutputStream photoOutput = new ByteArrayOutputStream();
		photo.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, photoOutput);
		entry.put(photoModelHelper.photosTablePhoto, photoOutput.toByteArray());
		
		try {
			photoOutput.close();
		} 
		
		catch (IOException e) {}
		
		entry.put(photoModelHelper.photosTableGroup, photo.getGroup());
		entry.put(photoModelHelper.photosTableTags, photo.getTagsForDatabase());
		entry.put(photoModelHelper.photosTableDate, photo.getDate().toString());
		
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
	
	public Vector<PhotoEntry> getAllPhotos() {
		Vector<PhotoEntry> photoEntries = new Vector<PhotoEntry>();
		
		Cursor cursor = photoDatabase.rawQuery("SELECT * FROM " + photoModelHelper.photosTable, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				PhotoEntry entry = new PhotoEntry();
				int ID = cursor.getInt(cursor.getColumnIndex(photoModelHelper.photosTableID));
				byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(photoModelHelper.photosTablePhoto));
				Bitmap image = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
				String group = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableGroup));
				String tags = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableTags));
				Date date = new Date();
				
				try {
					date = DateFormat.getTimeInstance().parse(cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableDate)));
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

		cursor.close();
		
		return photoEntries;
	}
	
	public Vector<PhotoEntry> getPhotosByValues(Vector<String> groupsQuery, Vector<String> tagsQuery) {
		Vector<PhotoEntry> photoEntries = new Vector<PhotoEntry>();
		String query = "";
		Vector<String> selectionArgs = new Vector<String>();
		
		if (groupsQuery != null && groupsQuery.size() > 0) {
			if (groupsQuery.elementAt(0) != null) {
				query = photoModelHelper.photosTableGroup + " LIKE ? ";
				selectionArgs.add(groupsQuery.elementAt(0));
				
				if (groupsQuery.size() > 1) {
					for (int i = 1; i < groupsQuery.size(); i++) {
						query += "OR " + photoModelHelper.photosTableGroup + " LIKE ? ";
						selectionArgs.add(groupsQuery.elementAt(i));
					}
				}
			}
		}
		
		if (tagsQuery != null && tagsQuery.size() > 0) {
			if (tagsQuery.elementAt(0) != null) {
				if (query.equals(""))
					query = photoModelHelper.photosTableTags + " LIKE ? ";
				else
					query += "OR " + photoModelHelper.photosTableTags + " LIKE ? ";
				
				selectionArgs.add(tagsQuery.elementAt(0));
				
				if (tagsQuery.size() > 1) {
					for (int i = 1; i < tagsQuery.size(); i++) {
						query += "OR " + photoModelHelper.photosTableTags + " LIKE ? ";
						selectionArgs.add(tagsQuery.elementAt(i));
					}
				}
			}
		}
		
		if (query.equals(""))
			return getAllPhotos();
		
		String[] arguments = new String[selectionArgs.size()];
		String logging = "";
		
		for (int j = 0; j < selectionArgs.size(); j++) {
			arguments[j] = "%" + selectionArgs.elementAt(j) + "%";
			logging += arguments[j] + " ";
		}
		
		Log.e("PhotoModel", query);
		Log.e("PhotoModel", logging);
		
		Cursor cursor = photoDatabase.rawQuery("SELECT * FROM " + photoModelHelper.photosTable + " WHERE " + query, arguments);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				PhotoEntry entry = new PhotoEntry();
				int ID = cursor.getInt(cursor.getColumnIndex(photoModelHelper.photosTableID));
				byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(photoModelHelper.photosTablePhoto));
				Bitmap image = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
				String group = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableGroup));
				String tags = cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableTags));
				Date date = new Date();
				
				try {
					date = DateFormat.getTimeInstance().parse(cursor.getString(cursor.getColumnIndex(photoModelHelper.photosTableDate)));
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
		
		cursor.close();
		
		return photoEntries;
	}
	
	public boolean updatePhoto(PhotoEntry photoEntry) {
		ContentValues entry = new ContentValues();
		ByteArrayOutputStream photoOutput = new ByteArrayOutputStream();
		photoEntry.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, photoOutput);
		entry.put(photoModelHelper.photosTablePhoto, photoOutput.toByteArray());
		
		try {
			photoOutput.close();
		} 
		
		catch (IOException e) {}
		
		entry.put(photoModelHelper.photosTableGroup, photoEntry.getGroup());
		entry.put(photoModelHelper.photosTableTags, photoEntry.getTagsForDatabase());
		entry.put(photoModelHelper.photosTableDate, photoEntry.getDate().toString());
		
		int row = photoDatabase.update(photoModelHelper.photosTable, entry, photoModelHelper.photosTableID + " = ?", new String[] {"" + photoEntry.getID()});
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	public Uri getTemporaryImage() {
		return Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + "tmp.jpg")); 
	}
}
