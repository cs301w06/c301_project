package cs.c301.project.Listeners;

import java.util.Vector;

import cs.c301.project.Data.PhotoEntry;
/**
 * Interface for the model listeners. Contains declaration for methods
 * which control changes upon model updates.
 * <p>
 * Javadoc by esteckle
 * 
 * @author yhu3
 *
 */
public interface PhotoModelListener {
	public void photosChanged(Vector<PhotoEntry> photos);
	public void tagsChanged(Vector<String> tags);
	public void groupsChanged(Vector<String> groups);
}
