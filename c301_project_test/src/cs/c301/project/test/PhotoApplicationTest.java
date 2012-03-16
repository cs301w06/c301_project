package cs.c301.project.test;

import java.io.File;
import java.util.Vector;

import android.os.Environment;
import android.test.AndroidTestCase;
import cs.c301.project.PhotoApplication;
import cs.c301.project.Data.PhotoEntry;
import cs.c301.project.Models.PhotoModel;

public class PhotoApplicationTest extends AndroidTestCase {
	
	public PhotoModel model;
	public PhotoApplication application;
	
	public PhotoApplicationTest() {
		File file = Environment.getExternalStorageDirectory();
		model = new PhotoModel(file);
		
		application = new PhotoApplication();
	}
	
	public void testEnvironmentPath() {
		assertEquals(model.getFilePath(), application.getFilePath());
	}
	
	public void testPhotoManipulation() {
		Vector<String> tags = new Vector<String>();
		tags.add("test1");
		tags.add("test2");
		tags.add("test3");
		
		PhotoEntry entry = new PhotoEntry(null, tags, "groupisawesome");
		
		application.addPhoto(entry);
		
		PhotoModel model2 = application.model;
		
		assertEquals(1, model2.getPhotos().size());
		
		PhotoEntry entry2 = model2.getPhotos().elementAt(0);
		
		assertEquals(tags, entry2.getTags());
		assertEquals("groupisawesome", entry2.getGroup());
		
		application.removePhoto(entry2.getID());
		
		model2 = application.model;
		
		assertEquals(0, model2.getPhotos().size());
	}
}