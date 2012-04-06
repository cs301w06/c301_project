package cs.c301.project.Models;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cs.c301.project.Utilities.SimpleCrypto;

/**
 * Login database helper to handle database upgrades and creates.
 * @author yhu3
 */

public class LoginModelHelper extends SQLiteOpenHelper {	
	public static final String databaseName = "admin.db";
	
	public static final String usersTable = "users";
	public static final String usersTableID = "id";
	public static final String usersTableName = "name";
	public static final String usersTablePassword = "password";
	public static final String usersTableFirstName = "firstname";
	public static final String usersTableLastName = "lastname";
	public static final String usersTableCreationDate = "creationdate";
	
	private static final int databaseVersion = 1;
	
	private static final String table1 = "CREATE TABLE " + usersTable + " (" + usersTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + usersTableName + " TEXT NOT NULL, " + usersTablePassword + " TEXT NOT NULL, " + usersTableFirstName + " TEXT NOT NULL, " + usersTableLastName + " TEXT NOT NULL, " + usersTableCreationDate + " TEXT NOT NULL);";

	public LoginModelHelper(Context context) {
		super(context, databaseName, null, databaseVersion);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(table1);
		
		ContentValues entry = new ContentValues();
		entry.put(usersTableName, "doctor");
		
		try {
			entry.put(usersTablePassword, SimpleCrypto.encrypt("LolAndroidKey", "doctor"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		entry.put(usersTableFirstName, "");
		entry.put(usersTableLastName, "");
		
		Date date = new Date();
		
		entry.put(usersTableCreationDate, date.toString());
		
		database.insert(usersTable, null, entry);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + usersTable);
		onCreate(database);
	}
}