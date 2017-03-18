package org.fergonco.wmk.renderer.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheTemplate {

	private String template;
	private HashMap<String, Object> data = new HashMap<>();

	public MustacheTemplate(String resourceName) {
		try {
			InputStream templateInputStream = this.getClass().getResourceAsStream(resourceName);
			template = IOUtils.toString(templateInputStream, "utf8");
			templateInputStream.close();
		} catch (IOException e) {
			// Not possible unless some very bad things are happening
			throw new RuntimeException(e);
		}
	}

	public String render() {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(new StringReader(template), "unknown parameter");
		StringWriter output = new StringWriter();
		mustache.execute(output, data);
		output.flush();
		
		return output.getBuffer().toString();
	}

	public void put(String propertyName, Object propertyValue) {
		data.put(propertyName, propertyValue);
	}

}
