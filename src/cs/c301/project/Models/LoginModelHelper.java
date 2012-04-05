package cs.c301.project.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LoginModelHelper extends SQLiteOpenHelper {
//	private Vector<PhotoModelListener> listeners;
	
	public static final String databaseName = "admin.db";
	
	public static final String usersTable = "users";
	public static final String usersTableID = "id";
	public static final String usersTableName = "name";
	public static final String usersTablePassword = "password";
	
	private static final int databaseVersion = 1;
	
	private static final String table1 = "CREATE TABLE " + usersTable + " (" + usersTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + usersTableName + " TEXT NOT NULL, " + usersTablePassword + " TEXT NOT NULL);";

	public LoginModelHelper(Context context) {
		super(context, databaseName, null, databaseVersion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(table1);
		
		ContentValues entry = new ContentValues();
		entry.put(usersTableName, "doctor");
		entry.put(usersTablePassword, "doctor");
		database.insert(usersTable, null, entry);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + usersTable);
		onCreate(database);
	}
}