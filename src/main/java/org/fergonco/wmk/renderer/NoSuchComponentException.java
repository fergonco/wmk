package org.fergonco.wmk.renderer;

public class NoSuchComponentException extends Exception {

	public NoSuchComponentException(String componentName) {
		super(componentName);
	}

}
