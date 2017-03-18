package org.fergonco.wmk.renderer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.tools.ant.DirectoryScanner;

public class ProjectFolder {

	private File root;

	public ProjectFolder(File root) {
		this.root = root;
	}

	public Properties getProperties(String fileName) throws FileNotFoundException, IOException {
		Properties ret = new Properties();
		ret.load(new BufferedInputStream(new FileInputStream(new File(root, fileName))));
		return ret;
	}

	public String[] find(String searchPath) {
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setIncludes(new String[] { searchPath });
		scanner.setBasedir(root);
		scanner.setCaseSensitive(true);
		scanner.scan();
		return scanner.getIncludedFiles();
	}

}
