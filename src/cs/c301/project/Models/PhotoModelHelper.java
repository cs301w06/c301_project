package cs.c301.project.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Model for the application. Mediates interaction between other classes and data.
 * Saves and loads data from the disk.
 * <p>
 * Javadoc by esteckle
 * 
 * @author yhu3
 *
 */
public class PhotoModelHelper {

	public static String databaseName = "photos.db";
	
	public static final String groupsTable = "groups";
	public static final String groupsTableID = "id";
	public static final String groupsTableName = "name";
	
	public static final String tagsTable = "tags";
	public static final String tagsTableID = "id";
	public static final String tagsTableName = "name";
	
	public static final String doctorTagsTable = "doctortags";
	public static final String doctorTagsTableID = "id";
	public static final String doctorTagsTableName = "name";
	
	public static final String photosTable = "photos";
	public static final String photosTableID = "id";
	public static final String photosTablePhoto = "photo";
	public static final String photosTableThumbnail = "thumbnail";
	public static final String photosTableAnnotation = "annotation";
	public static final String photosTableGroup = "groupName";
	public static final String photosTableTags = "tags";
	public static final String photosTableDoctorTags = "doctor";
	public static final String photosTableDate = "date";
	
	private static final int databaseVersion = 1;
	
	private static final String table1 = "CREATE TABLE " + groupsTable + " (" + groupsTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + groupsTableName + " TEXT NOT NULL);";
	private static final String table2 = "CREATE TABLE " + tagsTable + " (" + tagsTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + tagsTableName + " TEXT NOT NULL);";
	private static final String table3 = "CREATE TABLE " + photosTable + " (" + photosTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + photosTablePhoto + " BLOB, " + photosTableThumbnail + " BLOB, " + photosTableAnnotation + " TEXT, " + photosTableGroup + " TEXT, " + photosTableTags + " TEXT, " + photosTableDoctorTags + " TEXT, " + photosTableDate + " TEXT);";
	private static final String table4 = "CREATE TABLE " + doctorTagsTable + " (" + doctorTagsTableID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + doctorTagsTableName + " TEXT NOT NULL);";

	private static PhotoModelHelperInstance userDatabase;
	public Context context;
	
	public PhotoModelHelper(Context context) {
		this.context = context;
	}
	
	public void setUser(String username) {
		databaseName = new String(username + ".db");
		userDatabase = new PhotoModelHelperInstance(context);
	}
	
	public SQLiteDatabase getWritableDatabase() {
		return userDatabase.getWritableDatabase();
	}
	
	public class PhotoModelHelperInstance extends SQLiteOpenHelper {
		public PhotoModelHelperInstance(Context context) {
			super(context, databaseName, null, databaseVersion);
		}
		
		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(table1);
			database.execSQL(table2);
			database.execSQL(table3);
			database.execSQL(table4);
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			database.execSQL("DROP TABLE IF EXISTS " + groupsTable);
			database.execSQL("DROP TABLE IF EXISTS " + tagsTable);
			database.execSQL("DROP TABLE IF EXISTS " + photosTable);
			database.execSQL("DROP TABLE IF EXISTS " + doctorTagsTable);
			
			onCreate(database);
		}
	}
}