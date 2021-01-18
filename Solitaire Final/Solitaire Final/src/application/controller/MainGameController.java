package application.controller;

import java.util.Stack;

import application.Main;
import application.model.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainGameController {

	@FXML
	private Label playerName, scoreSheet;
	
	@FXML
	private StackPane stock, waste;
	
	@FXML
	private StackPane foundation1, foundation2, foundation3, foundation4;
	
	@FXML
	private StackPane tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7;
	
	@FXML
	private AnchorPane gamePane;
	
	@FXML
	private Canvas gameCanvas;
	
	private Main main;
	private Game currentGame;
	private Double cardHeight;
	private Double cardWidth;
	private StackPane[] foundations;
	private StackPane[] tableaus;
	private Stack<Game> moveHistory;

	public void setMain(Main main) {
		this.main = main;
		
		playerName.setText(Main.player); 
		init();
		draw();
	}
	
	@FXML
	public void quitGame() {
		main.openGameOver();
	}
	
	public void init() {
		currentGame = new Game();
		moveHistory = new Stack<Game>();
		
		cardHeight = (double) 122;
		cardWidth = (double) 100;
		
		//Initialize foundations 
		foundations = new StackPane[4];
		foundations[0] = foundation1;
		foundations[1] = foundation2;
		foundations[2] = foundation3;
		foundations[3] = foundation4;
		
		//Initialize tableaus
		tableaus = new StackPane[7];
		tableaus[0] = tableau1;
		tableaus[1] = tableau2;
		tableaus[2] = tableau3;
		tableaus[3] = tableau4;
		tableaus[4] = tableau5;
		tableaus[5] = tableau6;
		tableaus[6] = tableau7;
	}
	
	public void draw() {
		clearBoard();
		//Draw Stock
		drawStock();
		
		//Draw Waste
		drawWaste();
		
		//Draw Foundation
		drawFoundations();
			
		//Draw Tableau
		drawTableaus();
		
		//Update score sheet
		scoreSheet.setText(String.valueOf(currentGame.getScore()));
	}
	
	public void clearBoard() {
		stock.getChildren().clear();
		waste.getChildren().clear();
		for(int i = 0; i < 4; i++) {
			foundations[i].getChildren().clear();
		}
		for(int i = 0; i < 7; i++) {
			tableaus[i].getChildren().clear();
		}
	}
	
	public void drawStock() {
		Stock gameStock = currentGame.getStock();
		if(!gameStock.isEmpty()) {
			Pane cardPane = getNewPane();
			Card stockCard = gameStock.peek();
			cardPane.setStyle("-fx-background-image: url('" + stockCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
			stock.getChildren().add(cardPane);
		}
	}
	
	public void drawWaste() {
		Waste gameWaste = currentGame.getWaste();
		if(!gameWaste.isEmpty()) {
			Pane cardPane = getNewPane();
			Card wasteCard = gameWaste.peek();
			cardPane.setStyle("-fx-background-image: url('" + wasteCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
			waste.getChildren().add(cardPane);
		}
	}
	
	public void drawFoundations() {
		for(int i = 0; i < 4; i++) {
			Foundation currentFoundation = currentGame.getFoundation(i);
			for(Card card : currentFoundation.getFoundation()) {
				Pane cardPane = getNewPane();
				cardPane.setStyle("-fx-background-image: url('" + card.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
				foundations[i].getChildren().add(cardPane);
			}
		}
	}
	
	public void drawTableaus() {
		for(int i = 0; i < 7; i++) {
			int offset = 0;
			Tableau currentTableau = currentGame.getTableau(i);
			for(Card card : currentTableau.getTableau()) {
				Pane cardPane = getNewPane();
				cardPane.setStyle("-fx-background-image: url('" + card.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
				
				tableaus[i].setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableaus[i].getChildren().add(cardPane);
				offset += 20;
			}
		}
	}
	
	public Pane getNewPane() {
		Pane pane = new Pane();
		pane.setMinSize(100,150);
		pane.setPrefSize(100,150);
		pane.setMaxSize(100,150);
		return pane;
	}
	
	public void move(boolean success) {
		if(!success) {
			undoMove();
		}
		draw();
	}
	
	public void undoMove() {
		this.currentGame = moveHistory.pop();
	}
	
	@FXML
	public void stockClick() {
		move(handleStockClick());
	}
	
	public boolean handleStockClick() {
		moveHistory.push(this.currentGame);
		
		Stock currentStock = currentGame.getStock();
		if(currentStock.isEmpty()) {
			return recycleWaste();
		}
		else {
			currentGame.getWaste().receiveFromStock(currentStock.deal());
			return true;
		}
	}
	
	public boolean recycleWaste() {
		Waste currentWaste = currentGame.getWaste();
		if(currentWaste.isEmpty()) {
			return false;
		}
		else {
			Stack<Card> tempStack = new Stack<Card>();
			while(!currentWaste.isEmpty()) {
				tempStack.push(currentWaste.deal());
			}
			currentGame.getStock().setStock(tempStack);
			return true;
		}
	}
}
