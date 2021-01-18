package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainGameController {

	@FXML
	private Label playerName, scoreSheet;
	
	@FXML
	private AnchorPane stock, waste, foundation1, foundation2, foundation3, foundation4;
	
	@FXML
	private StackPane tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7;
	
	@FXML
	private Button recycleWaste;
	
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
