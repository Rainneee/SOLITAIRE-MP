package application.controller;

import application.Main;
import application.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverController {
	@FXML 
	private Label playerName, score;
	private Main main;
	private Game game;
	
	public void message(Main main, Game game) {
		this.main = main;
		this.setGame(game);
		
		playerName.setText(Main.player); 
		score.setText(String.valueOf(MainGameController.currentGame.getScore()));
		game.recordScore(Main.player, MainGameController.currentGame.getScore());
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	@FXML
	public void goToMainMenu() {
		main.openMainMenu();
	}
	
	
}
