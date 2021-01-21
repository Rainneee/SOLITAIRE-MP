package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Romaine
 */

public class PlayerController {
	private Main main;
	
	@FXML
	private TextField playerName;
	
	public void setMain(Main main)
	{
		this.main = main;
	}
	
	@FXML
	public void enter() {
		//reference here yung player na initialized sa Main
		Main.player = (playerName.getText().compareTo("") == 0)? "Player" : playerName.getText();
		main.openGame();
		//dito naman yung calling ng method ng game from main
		//main.openCongrats(); // try lang if gumagana
	}
}
