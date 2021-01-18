package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainGameController {

	@FXML
	private Label playerName, scoreSheet;
	
	private Main main;

	public void setMain(Main main) {
		this.main = main;
		
		playerName.setText(Main.player); 
	}
	
	@FXML
	public void goToMessage() {
		main.openGameOver();
	}

}
