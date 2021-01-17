package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller for the Pane created in Title.fxml. Most functionality here is
 * duplicated in the MainController, so event handlers here are mostly used to
 * call the event handlers for the MainController
 */
public class TitleController extends SuperController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button NewGamePush;

	@FXML
	private Button ExitPush;

	@FXML
	private Button HSPush;

	@FXML
	private Button SettingsPush;

	@FXML
	private void callHighScores(ActionEvent event) {
		((MainController) this.getAppControllerMap().get("mainController")).highScoresEventHandler(event);
	}

	@FXML
	private void callOpenPreferences(ActionEvent event) {
		((MainController) this.getAppControllerMap().get("mainController")).openPreferencesEventHandler(event);
	}

	@FXML
	private void callStartNewGame(ActionEvent event) throws IOException {
		// Load the mainController from the hashmap, then call the startNewGame method,
		// passing the event
		((MainController) this.getAppControllerMap().get("mainController")).startNewGame(event);
	}

	@FXML
	private void loadExit(ActionEvent event){
		((MainController) this.getAppControllerMap().get("mainController")).exitEventHandler(event);
	
	}

	@FXML
	private void initialize() {

	}

}
