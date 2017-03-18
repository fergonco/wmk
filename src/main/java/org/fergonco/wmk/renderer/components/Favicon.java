package org.fergonco.wmk.renderer.components;

import java.util.HashMap;
import java.util.Map;

import org.fergonco.wmk.renderer.ProjectFolder;
import org.fergonco.wmk.renderer.WMKComponent;

public class Favicon implements WMKComponent {

	@Override
	public void process(Map<String, Object> data, ProjectFolder projectFolder) throws WMKComponentException {
		HashMap<String, String> faviconInfo = new HashMap<>();
		String[] attempts = new String[] { "png", "jpg" };
		for (String attempt : attempts) {
			String[] icon = projectFolder.find("favicon." + attempt);
			if (icon.length > 0) {
				faviconInfo.put("path", icon[0]);
				faviconInfo.put("imageType", "image/" + attempt);
				break;
			}
		}
		if (faviconInfo.size() > 0) {
			data.put("favicon", faviconInfo);
		}
	}

	@Override
	public String getName() {
		return "favicon";
	}

}
