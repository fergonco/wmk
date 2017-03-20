package org.fergonco.wmk.renderer;

import java.util.Map;

import org.fergonco.wmk.renderer.components.WMKComponentException;

public interface WMKComponent {

	String getName();

	void process(Map<String, Object> data, ProjectFolder projectFolder) throws WMKComponentException;

	String[] getDependencies();

}
