package org.fergonco.wmk.renderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.fergonco.wmk.renderer.components.Favicon;
import org.fergonco.wmk.renderer.components.OverridesCSS;
import org.fergonco.wmk.renderer.components.Requirejs;
import org.fergonco.wmk.renderer.components.RequirejsConf;
import org.fergonco.wmk.renderer.components.RequirejsResources;
import org.fergonco.wmk.renderer.components.Stylesheets;
import org.fergonco.wmk.renderer.components.Title;
import org.fergonco.wmk.renderer.components.WMKComponentException;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class Renderer {

	private ArrayList<WMKComponent> components = new ArrayList<>();
	private HashMap<String, WMKComponent> nameComponent = new HashMap<>();

	public Renderer() {
		addComponent(new Title());
		addComponent(new Favicon());
		addComponent(Stylesheets.singleton);
		addComponent(new OverridesCSS());
		addComponent(new RequirejsConf());
		addComponent(new RequirejsResources());
		addComponent(new Requirejs());
	}

	private void addComponent(WMKComponent component) {
		components.add(component);
		nameComponent.put(component.getName(), component);
	}

	public void render(File root, OutputStream out, String... componentNames)
			throws NoSuchComponentException, IOException, WMKComponentException {
		Set<String> selectedComponents = completeWithDependencies(componentNames);
		ProjectFolder folder = new ProjectFolder(root);
		HashMap<String, Object> templateData = new HashMap<>();
		templateData.put("scripts", new ArrayList<String>());
		templateData.put("stylesheets", new ArrayList<String>());
		for (WMKComponent component : components) {
			if (selectedComponents.contains(component.getName())) {
				component.process(templateData, folder);
				selectedComponents.remove(component.getName());
			}
		}
		if (selectedComponents.size() > 0) {
			throw new NoSuchComponentException(selectedComponents.iterator().next());
		}
		Writer writer = new OutputStreamWriter(out);
		MustacheFactory mf = new DefaultMustacheFactory();
		Reader template = new InputStreamReader(this.getClass().getResourceAsStream("default.html"),
				Charset.forName("utf-8"));
		Mustache mustache = mf.compile(template, "unknown parameter");
		mustache.execute(writer, templateData);
		writer.flush();
		template.close();
	}

	private Set<String> completeWithDependencies(String[] componentNames) throws NoSuchComponentException {
		HashSet<String> ret = new HashSet<>();
		while (componentNames.length > 0) {
			ArrayList<String> newComponentNames = new ArrayList<>();
			for (String componentName : componentNames) {
				ret.add(componentName);
				WMKComponent component = nameComponent.get(componentName);
				if (component == null) {
					throw new NoSuchComponentException(componentName);
				} else {
					String[] names = component.getDependencies();
					if (names != null) {
						for (String dependencyName : names) {
							ret.add(dependencyName);
							newComponentNames.add(dependencyName);
						}
					}
				}
			}
			componentNames = newComponentNames.toArray(new String[newComponentNames.size()]);			
		}

		return ret;
	}

	public static void main(String[] args) throws Exception {
		ArrayList<String> components = new ArrayList<>();
		for (String argument : args) {
			if (argument.startsWith("-C")) {
				String component = argument.substring(2);
				components.add(component);
			}
		}

		Renderer renderer = new Renderer();
		FileOutputStream out = new FileOutputStream("index.html");
		renderer.render(new File("."), out, components.toArray(new String[components.size()]));
		out.close();
	}
}
