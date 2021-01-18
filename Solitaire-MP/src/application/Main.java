package application;

import java.io.File;
import java.util.HashMap;

import application.controller.GameController;
import application.controller.SettingsController;
import application.controller.SuperController;
import application.model.SolitaireSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

/**
 * The main class for the application. This loads every FXML and stores them in
 * a HashMap in order to be able to add an arbitrary number of them. Similarly,
 * it stores every controller in a HashMap.
 * 
 * SolitaireSettings is the class that holds all settings for the application.
 * It is initialized here, then a reference is passed to all controllers that
 * need it.
 * 
 * Structurally, this application consists of a main pane, which is a BorderPane
 * that holds a menubar at the top. Switching between different views of the
 * application is done by loading different panes into the center of the main
 * BorderPane, allowing the menu to stay consistent between differente parts of
 * the application
 */
public class Main extends Application {
	// Store an arbitrary number of panes and controllers
	private HashMap<String, Object> paneMap;
	private HashMap<String, SuperController> controllerMap;
	private SolitaireSettings settingsObject;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Initialize the hashmap
			paneMap = new HashMap<String, Object>();
			controllerMap = new HashMap<String, SuperController>();
			settingsObject = new SolitaireSettings();

			// Create a loader for the main fxml
			FXMLLoader rootLoader = new FXMLLoader();
			rootLoader.setLocation(getClass().getResource("view/Main.fxml"));

			// Load it and put its controller in the controllerMap
			BorderPane rootPane = (BorderPane) rootLoader.load();
			paneMap.put("mainScreen", rootPane);
			controllerMap.put("mainController", rootLoader.getController());
			controllerMap.get("mainController").setAppPaneMap(paneMap);
			Scene rootScene = new Scene(rootPane);

			// Create a loader for the title screen
			FXMLLoader titleLoader = new FXMLLoader();
			titleLoader.setLocation(getClass().getResource("view/Title.fxml"));

			// Load it and store both it and its controller
			paneMap.put("titleScreen", (GridPane) titleLoader.load());
			controllerMap.put("titleController", titleLoader.getController());

			// Create a loader for the settings
			FXMLLoader settingsLoader = new FXMLLoader();
			settingsLoader.setLocation(getClass().getResource("view/Settings.fxml"));

			// Load and store as above
			paneMap.put("settingsScreen", (GridPane) settingsLoader.load());
			controllerMap.put("settingsController", settingsLoader.getController());

			FXMLLoader gameLoader = new FXMLLoader();
			gameLoader.setLocation(getClass().getResource("view/Game.fxml"));
			paneMap.put("gameScreen", (Pane) gameLoader.load());
			controllerMap.put("gameController", gameLoader.getController());

			// Allow each controller to access every other controller
			for (SuperController currentControl : controllerMap.values()) {
				currentControl.setAppControllerMap(controllerMap);
			}

			// Pass a reference to the settings for the entire application
			((SettingsController) controllerMap.get("settingsController")).setAppSettingsObject(settingsObject);
			((GameController) controllerMap.get("gameController")).setAppSettingsObject(settingsObject);

			rootPane.setCenter((Node) paneMap.get("titleScreen"));
			rootScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Solitaire");
			primaryStage.setScene(rootScene);
			primaryStage.show();

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Getter for the paneMap that holds all the Panes used in the application
	 * 
	 * @return A HashMap<String, Object> that holds references to all loaded panes
	 */
	public HashMap<String, Object> getPaneMap() {
		return paneMap;
	}

	/**
	 * Getter for the controllerMap that holds all the controllers used in the
	 * application
	 * 
	 * @return A HashMap<String, SuperController> that holds references to all
	 *         loaded controllers
	 */
	public HashMap<String, SuperController> getControllerMap() {
		return controllerMap;
	}
}
