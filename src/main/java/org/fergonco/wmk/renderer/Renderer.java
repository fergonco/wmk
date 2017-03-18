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
import java.util.List;

import org.fergonco.wmk.renderer.components.Favicon;
import org.fergonco.wmk.renderer.components.OverridesCSS;
import org.fergonco.wmk.renderer.components.Requirejs;
import org.fergonco.wmk.renderer.components.RequirejsConf;
import org.fergonco.wmk.renderer.components.Stylesheets;
import org.fergonco.wmk.renderer.components.Title;
import org.fergonco.wmk.renderer.components.WMKComponentException;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class Renderer {

	private ArrayList<WMKComponent> components = new ArrayList();

	public Renderer() {
		addComponent(new Title());
		addComponent(new Favicon());
		addComponent(new Stylesheets());
		addComponent(new OverridesCSS());
		addComponent(new RequirejsConf());
		addComponent(new Requirejs());
	}

	private void addComponent(WMKComponent component) {
		components.add(component);
	}

	public void render(File root, OutputStream out, String... componentNames)
			throws NoSuchComponentException, IOException, WMKComponentException {
		ProjectFolder folder = new ProjectFolder(root);
		HashMap<String, Object> templateData = new HashMap<>();
		templateData.put("scripts", new ArrayList<String>());
		templateData.put("stylesheets", new ArrayList<String>());
		List<String> selectedComponents = Arrays.asList(componentNames);
		for (WMKComponent component : components) {
			if (selectedComponents.contains(component.getName())) {
				component.process(templateData, folder);
			}
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
