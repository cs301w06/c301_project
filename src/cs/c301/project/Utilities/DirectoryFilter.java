package cs.c301.project.Utilities;

import java.io.File;
import java.io.FileFilter;
/**
 * A class to filter out whether a path is to a directory or a file.
 * <p>
 * Javadoc by esteckle
 * 
 * @author yhu3
 *
 */
public class DirectoryFilter implements FileFilter {
	/**
	 * Checks whether a path is to a directory or a file
	 * 
	 * @param pathname A pathname which we wish to verify if it is a directory or file
	 * @return True if the path is to a directory, false if it is not to a directory.
	 */
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}