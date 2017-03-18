package org.fergonco.wmk.renderer.components;

public class WMKComponentException extends Exception {

	public WMKComponentException() {
		super();
	}

	public WMKComponentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WMKComponentException(String message, Throwable cause) {
		super(message, cause);
	}

	public WMKComponentException(String message) {
		super(message);
	}

	public WMKComponentException(Throwable cause) {
		super(cause);
	}

}
