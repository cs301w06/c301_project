package cs.c301.project.Models;

import java.util.Date;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cs.c301.project.PhotoApplication;
import cs.c301.project.Utilities.SimpleCrypto;

public class LoginModel {
	private LoginModelHelper loginModelHelper;
	private SQLiteDatabase loginDatabase;
	private String currentUser;
	
	public LoginModel(Context context) {
		currentUser = null;
		loginModelHelper = new LoginModelHelper(context);
		open();
	}
	
	public void open() throws SQLException {
		loginDatabase = loginModelHelper.getWritableDatabase();
	}
	
	public void close() {
		loginDatabase.close();
	}
	
	public String getCurrentUser() {
		return currentUser;
	}
	
	public Vector<String> getPatients() {
		Vector<String> patients = new Vector<String>(0, 1);
		
		Cursor cursor = loginDatabase.rawQuery("SELECT * FROM " + loginModelHelper.usersTable, null);
		
		boolean loop = true;
		
		while (loop) {
			if (cursor.moveToNext()) {
				patients.add(cursor.getString(cursor.getColumnIndex(loginModelHelper.usersTableName)));
			} else {
				loop = false;
			}
		}
		
		cursor.close();
		
		patients.remove(0); //remove the doctor
		
		return patients;
	}
	
	public boolean login(String username, String password) {
		try {
			Cursor cursor = loginDatabase.rawQuery("SELECT * FROM " + loginModelHelper.usersTable + " WHERE " + loginModelHelper.usersTableName +  " = ?", new String[] {username.trim()});
			
			if (cursor.moveToNext()) {
				String databasePassword =  cursor.getString(cursor.getColumnIndex(loginModelHelper.usersTablePassword));
				
				if (SimpleCrypto.decrypt("LolAndroidKey", databasePassword).equals(password) || PhotoApplication.isDoctor()) {
					currentUser = username;
					cursor.close();
					return true;
				}
			}
			
			cursor.close();
		}
		
		catch (Exception e) {}
		
		return false;
	}
	
	public boolean create(String username, String password) {
		try {
			Cursor cursor = loginDatabase.rawQuery("SELECT * FROM " + loginModelHelper.usersTable + " WHERE " + loginModelHelper.usersTableName +  " = ?", new String[] {username.trim()});
			
			if (cursor.moveToNext()) {
				cursor.close();
				return false;
			}
			
			cursor.close();
			
			ContentValues entry = new ContentValues();
			entry.put(loginModelHelper.usersTableName, username);
			entry.put(LoginModelHelper.usersTablePassword, SimpleCrypto.encrypt("LolAndroidKey", password));
			entry.put(loginModelHelper.usersTableFirstName, SimpleCrypto.encrypt("LolAndroidKey", "Temp First Name"));
			entry.put(loginModelHelper.usersTableLastName, SimpleCrypto.encrypt("LolAndroidKey", "Temp Last Name"));
			
			Date date = new Date();
			
			entry.put(loginModelHelper.usersTableCreationDate, SimpleCrypto.encrypt("LolAndroidKey", date.toString()));
			
			long row = loginDatabase.insert(LoginModelHelper.usersTable, null, entry);
			
			if (row != -1) {
				currentUser = username;
				return true;
			}
		}
		
		catch (Exception e) {}
		
		return false;
	}
	
	public boolean delete(String username) {
		int row = loginDatabase.delete(loginModelHelper.usersTable, loginModelHelper.usersTableName + " = ?", new String[] {username});
		
		if (row != 0)
			return true;
		
		return false;
	}
}
