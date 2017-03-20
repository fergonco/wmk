package org.fergonco.wmk.renderer.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.fergonco.wmk.renderer.ProjectFolder;
import org.fergonco.wmk.renderer.WMKComponent;

public class Stylesheets implements WMKComponent {

	public static Stylesheets singleton = new Stylesheets();
	
	@Override
	public void process(Map<String, Object> data, ProjectFolder projectFolder) throws WMKComponentException {
		String[] css = projectFolder.find("*/css/*.css");
		@SuppressWarnings("unchecked")
		ArrayList<String> stylesheets = (ArrayList<String>) data.get("stylesheets");
		stylesheets.addAll(Arrays.asList(css));
	}

	@Override
	public String getName() {
		return "stylesheets";
	}

	@Override
	public String[] getDependencies() {
		return null;
	}

}
