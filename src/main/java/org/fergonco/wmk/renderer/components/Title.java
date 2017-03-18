package org.fergonco.wmk.renderer.components;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.fergonco.wmk.renderer.ProjectFolder;
import org.fergonco.wmk.renderer.WMKComponent;

public class Title implements WMKComponent {

	@Override
	public void process(Map<String, Object> data, ProjectFolder projectFolder)
			throws WMKComponentException {
		Properties properties;
		try {
			properties = projectFolder.getProperties("wmk.properties");
		} catch (IOException e) {
			throw new WMKComponentException("cannot read title from wmk.properties", e);
		}
		data.put("title", properties.getProperty("title"));
	}

	@Override
	public String getName() {
		return "title";
	}

}
