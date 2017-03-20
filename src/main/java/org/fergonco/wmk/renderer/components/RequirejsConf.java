package org.fergonco.wmk.renderer.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.fergonco.wmk.renderer.ProjectFolder;
import org.fergonco.wmk.renderer.WMKComponent;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class RequirejsConf implements WMKComponent {

	public static RequirejsConf singleton = new RequirejsConf();

	@Override
	public String getName() {
		return "requirejsconf";
	}

	@Override
	public void process(Map<String, Object> data, ProjectFolder projectFolder) throws WMKComponentException {

		ArrayList<HashMap<String, Object>> configurations = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonObject configuration;
		try {
			configuration = (JsonObject) parser.parse(new FileReader(new File("conf.json")));
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			throw new WMKComponentException("Cannot read configuration", e);
		}
		Set<Entry<String, JsonElement>> configurationEntries = configuration.entrySet();
		for (Entry<String, JsonElement> entry : configurationEntries) {
			HashMap<String, Object> testMap = new HashMap<>();
			testMap.put("name", entry.getKey());
			testMap.put("value", entry.getValue().toString());
			configurations.add(testMap);
		}

		MustacheTemplate template = new MustacheTemplate("requirejsconf.template");
		template.put("moduleConfigurations", configurations);
		@SuppressWarnings("unchecked")
		ArrayList<String> scripts = (ArrayList<String>) data.get("scripts");
		scripts.add(template.render());
	}

	@Override
	public String[] getDependencies() {
		return new String[] { Requirejs.singleton.getName() };
	}

}
