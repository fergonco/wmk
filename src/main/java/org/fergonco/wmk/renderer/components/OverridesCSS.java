package org.fergonco.wmk.renderer.components;

import java.util.ArrayList;
import java.util.Map;

import org.fergonco.wmk.renderer.ProjectFolder;
import org.fergonco.wmk.renderer.WMKComponent;

public class OverridesCSS implements WMKComponent {

	@Override
	public void process(Map<String, Object> data, ProjectFolder projectFolder) throws WMKComponentException {
		@SuppressWarnings("unchecked")
		ArrayList<String> stylesheets = (ArrayList<String>) data.get("stylesheets");
		stylesheets.add("overrides.css");
	}

	@Override
	public String getName() {
		return "overridesCSS";
	}

	@Override
	public String[] getDependencies() {
		return null;
	}

}
