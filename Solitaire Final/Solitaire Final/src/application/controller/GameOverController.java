package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverController {
	@FXML 
	private Label playerName, score;
	private Main main;
	
	public void message(Main main) {
		this.main = main;
		
		//playerName.setText(value); value = initiated player sa main
		//score.setText(""+ valueofscore) value of score = value record sa main;
		//main.  tagarecord ng score from main
		
	}
	
	@FXML
	public void goToMainMenu() {
		main.openMainMenu();
	}
}
