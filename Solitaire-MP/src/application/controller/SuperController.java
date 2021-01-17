package application.controller;

import java.util.HashMap;

/**
 * An abstract class that all Controller classes are extended from. This is done
 * to allow them being stored in an HashMap<String, SuperController> object,
 * allowing them to all be referenced by each other
 * 
 */
public abstract class SuperController {

	protected HashMap<String, Object> appPaneMap;
	protected HashMap<String, SuperController> appControllerMap;

	/**
	 * Getter for the appPaneMap
	 * 
	 * @return the appPaneMap of this object
	 */
	public HashMap<String, Object> getAppPaneMap() {
		return appPaneMap;
	}

	/**
	 * Setter for the appPaneMap
	 * 
	 * @param appPaneMap A reference to the HashMap<String, Object> to use as this
	 *                   object's appPaneMap
	 */
	public void setAppPaneMap(HashMap<String, Object> appPaneMap) {
		this.appPaneMap = appPaneMap;
	}

	/**
	 * Getter for the appControllerMap
	 * 
	 * @return The appControllerMap for this object
	 */
	public HashMap<String, SuperController> getAppControllerMap() {
		return appControllerMap;
	}

	/**
	 * Setter for this object's appControllerMap object
	 * 
	 * @param controllerMap A reference to the HashMap<String, SuperController> to
	 *                      use as this object's appControllerMap
	 */
	public void setAppControllerMap(HashMap<String, SuperController> controllerMap) {
		this.appControllerMap = controllerMap;
	}
}
