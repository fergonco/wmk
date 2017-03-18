package org.fergonco.wmk.renderer;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.Test;

import junit.framework.TestCase;

public class RendererTest extends TestCase {

	@Test
	public void testRenderer() throws Exception {
		Renderer renderer = new Renderer();
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		renderer.render(new File("src/test/resources/project1"), result, "title", "favicon", "stylesheets",
				"requirejs");
		System.out.println(result.toString("utf8"));
		fail();
	}

	@Test
	public void testCommandLine() throws Exception {
		Renderer.main(new String[] {});
		fail();
	}

	@Test
	public void testGeoladris() {
		fail();
	}
}
