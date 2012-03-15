package cs.c301.project.Listeners;

import java.util.Vector;

import cs.c301.project.Data.PhotoEntry;

public interface PhotoModelListener {
	public void photosChanged(Vector<PhotoEntry> photos);
	public void tagsChanged(Vector<String> tags);
	public void groupsChanged(Vector<String> groups);
}
