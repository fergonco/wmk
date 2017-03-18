package org.fergonco.wmk.renderer.components;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.fergonco.wmk.renderer.ProjectFolder;
import org.fergonco.wmk.renderer.WMKComponent;

public class Requirejs implements WMKComponent {

	@Override
	public String getName() {
		return "requirejs";
	}

	@Override
	public void process(Map<String, Object> data, ProjectFolder projectFolder) throws WMKComponentException {

		ArrayList<HashMap<String, String>> modules = new ArrayList<>();
		String[] moduleFiles = projectFolder.find("*/modules/**/*.js");
		String moduleNameCSList = "";
		for (String moduleFile : moduleFiles) {
			HashMap<String, String> moduleData = new HashMap<>();
			String pluginName = moduleFile.substring(0, moduleFile.indexOf("/"));
			String moduleName = new File(moduleFile).getName();
			moduleName = moduleName.substring(0, moduleName.length() - 3);
			String moduleFullName = pluginName + "/" + moduleName;
			moduleData.put("name", moduleFullName);
			moduleData.put("path", moduleFile.substring(0, moduleFile.length() - 3));
			moduleNameCSList += "'" + moduleFullName + "',";
			modules.add(moduleData);
		}
		// remove last comma
		moduleNameCSList = moduleNameCSList.substring(0, moduleNameCSList.length() - 1);

		MustacheTemplate template = new MustacheTemplate("requirejs.template");
		template.put("libraryPath", "node_modules/requirejs/require.js");
		template.put("modules", modules);
		template.put("moduleNames", moduleNameCSList);
		@SuppressWarnings("unchecked")
		ArrayList<String> scripts = (ArrayList<String>) data.get("scripts");
		scripts.add(template.render());
	}

}
