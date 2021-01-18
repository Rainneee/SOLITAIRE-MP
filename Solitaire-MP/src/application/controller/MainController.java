package application.controller;

import javafx.fxml.FXML;
import java.io.IOException;
import java.util.Stack;

import application.model.Deck;
import application.model.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.event.Event;

/**
 * The Main controller for the whole application. Since the view defined in
 * Main.fxml is always showing (in order to keep the menubar showing), this
 * class is responsible for dealing with the other controller classes
 */
public class MainController extends SuperController {
	private Deck oldDeck;

	private Stack<Pane> oldCenterPane;
	@FXML
	private BorderPane mainPane;
	@FXML
	private MenuItem newGameMenuItem;
	@FXML
	private MenuItem saveMenuItem;
	@FXML
	private MenuItem saveAsMenuItem;
	@FXML
	private MenuItem openMenuItem;
	@FXML
	private MenuItem preferencesMenuItem;
	@FXML
	private MenuItem goHomeItem;
	@FXML
	private MenuItem exitMenuItem;
	@FXML
	private MenuItem undoMenuItem;
	@FXML
	private MenuItem redoMenuItem;
	@FXML
	private MenuItem restartMenuitem;
	@FXML
	private MenuItem howToPlayMenuItem;
	@FXML
	private MenuItem aboutMenuItem;

	// Event Listener on MenuItem[#newGameMenuItem].onAction
	@FXML
	public void startNewGame(ActionEvent event) throws IOException {
		// Get the game controller
		GameController currentGameControl = (GameController) appControllerMap.get("gameController");

		// Create a deck, and save it (allows for resetting later
		this.oldDeck = new Deck();

		// Create a new game with a copy of the oldDeck, draw the game in the
		// gameController
		currentGameControl.setCurrentGame(new Game(new Deck(this.oldDeck)));
		currentGameControl.updateValues();
		currentGameControl.drawCards();
		mainPane.setCenter((Pane) appPaneMap.get("gameScreen"));
	}

	// Event Listener on MenuItem[#saveMenuItem].onAction
	@FXML
	public void saveEventHandler(ActionEvent event) {
	}

	// Event Listener on MenuItem[#saveAsMenuItem].onAction
	@FXML
	public void saveAsEventHandler(ActionEvent event) {
	}

	// Event Listener on MenuItem[#openMenuItem].onAction
	@FXML
	public void openGameEventHandler(ActionEvent event) {
	}

	// Event Listener on MenuItem[#preferencesMenuItem].onAction
	@FXML
	public void openPreferencesEventHandler(ActionEvent event) {

		this.saveOldPane();
		// Get the main pane and set the center to be the settings screen
		((SettingsController) appControllerMap.get("settingsController")).saveOldSettings();
		mainPane.setCenter((GridPane) appPaneMap.get("settingsScreen"));
	}

	@FXML
	public void returnHomeEventHandler(ActionEvent event) {

		this.saveOldPane();

		// Get the main pane and set the center to be the title screen

		mainPane.setCenter((Pane) appPaneMap.get("titleScreen"));
	}

	// Event Listener on MenuItem[#aboutMenuItem].onAction
	@FXML
	public void exitEventHandler(ActionEvent event) {
		this.saveOldPane();
		Platform.exit();
	}

	/**
	 * Save the last pane that was set as the center pane. This allows 'back'
	 * buttons to return to the last view that was being shown. The pane is stored
	 * as a stack, allowing users to keep going back through multiple views
	 */
	public void saveOldPane() {
		this.oldCenterPane.push((Pane) this.mainPane.getCenter());
	}

	/**
	 * Restore the pane that was previously being show as the center of the main
	 * pane. The pane is stored as a stack, allowing users to keep going back
	 * through multiple views
	 */
	public void restoreCenterPane() {
		// Make sure we're not popping from an empty stack
		if (!oldCenterPane.isEmpty()) {
			this.mainPane.setCenter(oldCenterPane.pop());
		} else {
			System.out.println("Pane stack is empty, not able to return to the previous pane");
		}
	}

	/**
	 * Constructor that initializes the oldCenterPane stack with a new Stack<Pane>
	 * to store the previously visited screens
	 */
	public MainController() {
		oldCenterPane = new Stack<Pane>();
	}

	public void highScoresEventHandler(ActionEvent event) {
		// for highscore ga
		
	}

}
