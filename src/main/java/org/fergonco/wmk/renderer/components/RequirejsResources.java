package org.fergonco.wmk.renderer.components;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.fergonco.wmk.renderer.ProjectFolder;
import org.fergonco.wmk.renderer.WMKComponent;

import com.google.gson.Gson;

public class RequirejsResources implements WMKComponent {

	@Override
	public String getName() {
		return "requirejsresources";
	}

	@Override
	public void process(Map<String, Object> data, ProjectFolder projectFolder) throws WMKComponentException {
		ArrayList<HashMap<String, String>> resources = new ArrayList<>();
		String[] resourcePaths = projectFolder.find("*/modules/**/*.resource");

		try {
			for (String resourcePath : resourcePaths) {
				HashMap<String, String> resourceData = new HashMap<>();
				String pluginName = resourcePath.substring(0, resourcePath.indexOf("/"));
				File resourceFile = new File(resourcePath);
				String resourceName = resourceFile.getName();
				resourceName = resourceName.substring(0, resourceName.length() - ".resource".length());
				String moduleFullName = pluginName + "/" + resourceName;
				resourceData.put("name", moduleFullName);
				BufferedInputStream resourceInputStream = new BufferedInputStream(new FileInputStream(resourceFile));
				String resourceValue = IOUtils.toString(resourceInputStream, "utf-8");
				resourceData.put("value", new Gson().toJson(resourceValue));
				resourceInputStream.close();
				resources.add(resourceData);
			}
		} catch (IOException e) {
			throw new WMKComponentException("Cannot read resource", e);
		}

		MustacheTemplate template = new MustacheTemplate("resources.template");
		template.put("resources", resources);
		@SuppressWarnings("unchecked")
		ArrayList<String> scripts = (ArrayList<String>) data.get("scripts");
		scripts.add(template.render());
	}

}
