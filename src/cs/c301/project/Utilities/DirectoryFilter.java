package cs.c301.project.Utilities;

import java.io.File;
import java.io.FileFilter;

public class DirectoryFilter implements FileFilter {
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}