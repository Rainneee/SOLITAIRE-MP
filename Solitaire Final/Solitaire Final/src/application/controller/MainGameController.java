package application.controller;

import application.Main;
import application.model.Game;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainGameController {

	private Main main;
	
	public void setMain(Main main){
		this.main = main;
	}
	
	@FXML
	private AnchorPane stock, waste, foundation1, foundation2, foundation3, foundation4;
	
	@FXML
	private StackPane tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7;
	
	@FXML
	private Button recycleWaste;
	
	@FXML
	private Label playerName, currentScore;
	
	
	
	
}
