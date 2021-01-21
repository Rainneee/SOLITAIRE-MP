package application.controller;

import application.Main;
import javafx.fxml.FXML;

/**
 * 
 * @author Romaine
 */

public class MainMenuController {
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	public void startGame(){
		main.getPlayer();
	}
	
	@FXML
	public void seeHighScores(){
		main.openHighScores();
	}
	
	public void exit() {
		main.exit();
	}

}
