package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CongratsController {
	
	@FXML 
	private Label playerName, score;
	private Main main;
	
	public void message(Main main) {
		this.main = main;
		
		playerName.setText(Main.player); 
		score.setText(String.valueOf(MainGameController.currentGame.getScore()));
	}
	
	@FXML
	public void goToMainMenu() {
		main.openMainMenu();
	}

}
