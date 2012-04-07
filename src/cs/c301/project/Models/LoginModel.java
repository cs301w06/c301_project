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

/**
 * This is the model that holds the sqlite database handlers for our accounts.
 * It supports fully encryption of data as well as advanced features such as doctor view
 * @author yhu3
 */

public class LoginModel {
	private LoginModelHelper loginModelHelper;
	private SQLiteDatabase loginDatabase;
	private String currentUser;
	
	/** 
	 * Initializes the login helper
	 * 
	 *  @param context The program's context
	 */
	public LoginModel(Context context) {
		currentUser = null;
		loginModelHelper = new LoginModelHelper(context);
		open();
	}
	
	private void open() throws SQLException {
		loginDatabase = loginModelHelper.getWritableDatabase();
	}
	
	private void close() {
		loginDatabase.close();
	}
	
	/** 
	 * Returns the current username
	 * @return the current user
	 */
	public String getCurrentUser() {
		return currentUser;
	}
	
	/** 
	 *  Returns the list of patients for the doctor 
	 *  @return Vector<String> containing patient names
	 */
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
	
	/** 
	 * Login method, returns true if login is successful
	 * 
	 *  @param username	account name of the user
	 *  @param password	password for the given account 
	 *  @return true if successful log in
	 */
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
	
	/** 
	 * Creates accounts, returns true if the account is not taken and is successfully created.
	 * 
	 *  @param username	account name of the user
	 *  @param password	password for the given account 
	 *  @return true if successful creation
	 */
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
	
	/** 
	 * Deletes accounts.
	 * 
	 *  @param username	account name of the user
	 *  @return true if successful deletion
	 */
	public boolean delete(String username) {
		int row = loginDatabase.delete(loginModelHelper.usersTable, loginModelHelper.usersTableName + " = ?", new String[] {username});
		
		if (row != 0)
			return true;
		
		return false;
	}
	
	/** 
	 * Changes passwords for accounts.
	 * 
	 *  @param username	account name of the user
	 *  @param password	password for the given account 
	 *  @return true if successful change
	 */
	public boolean changePassword(String username, String password) {
		try {
			Cursor cursor = loginDatabase.rawQuery("SELECT * FROM " + loginModelHelper.usersTable + " WHERE " + loginModelHelper.usersTableName +  " = ?", new String[] {username});
			
			int id = -1;
			
			if (cursor.moveToNext()) {
				id = cursor.getInt(cursor.getColumnIndex(loginModelHelper.usersTableID));
				cursor.close();
			} else {
				return false;
			}
			
			ContentValues entry = new ContentValues();
			entry.put(LoginModelHelper.usersTablePassword, SimpleCrypto.encrypt(PhotoApplication.ENCRYPTION_KEY, password));
			
			int row = loginDatabase.update(loginModelHelper.usersTable, entry, loginModelHelper.usersTableID + " = ?", new String[] {"" + id});
			
			if (row != -1) {
				return true;
			}
		}
		
		catch (Exception e) {}
		
		return false;
	}
}
