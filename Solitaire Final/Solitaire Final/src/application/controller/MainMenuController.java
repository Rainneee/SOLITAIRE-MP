package application.controller;

import application.Main;
import javafx.fxml.FXML;

public class MainMenuController {
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	public void startGame(){
		main.openGame();
	}
	
	@FXML
	public void seeHighScores(){
		//main.
	}
	
	public void exit() {
		main.exit();
	}

}
