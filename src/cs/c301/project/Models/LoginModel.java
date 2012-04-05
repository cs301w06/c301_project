package cs.c301.project.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
	
	public boolean login(String username, String password) {
		Cursor cursor = loginDatabase.rawQuery("SELECT * FROM " + loginModelHelper.usersTable + " WHERE " + loginModelHelper.usersTableName +  " = ?", new String[] {username.trim()});
		
		if (cursor.moveToNext()) {
			String databasePassword =  cursor.getString(cursor.getColumnIndex(loginModelHelper.usersTablePassword));
			
			if (databasePassword.equals(password)) {
				currentUser = username;
				return true;
			}
		}
		
		return false;
	}
	
	public boolean create(String username, String password) {
		Cursor cursor = loginDatabase.rawQuery("SELECT * FROM " + loginModelHelper.usersTable + " WHERE " + loginModelHelper.usersTableName +  " = ?", new String[] {username.trim()});
		
		if (cursor.moveToNext()) {
			return false;
		}
		
		ContentValues entry = new ContentValues();
		entry.put(loginModelHelper.usersTableName, username);
		entry.put(LoginModelHelper.usersTablePassword, password);
		
		long row = loginDatabase.insert(LoginModelHelper.usersTable, null, entry);
		
		if (row != -1)
			return true;
		
		return false;
	}
	
	public boolean delete(String username) {
		int row = loginDatabase.delete(loginModelHelper.usersTable, loginModelHelper.usersTableName + " = ?", new String[] {username});
		
		if (row != 0)
			return true;
		
		return false;
	}
}
