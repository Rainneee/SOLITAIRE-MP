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
import javafx.scene.input.MouseEvent;
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
	private Card cardPicked;
	public boolean isFirstClick = true;
	private Double x, y;
	private Stack<Card> sourceStack;

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
		sourceStack = new Stack<Card>();
		
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
		
		if(currentGame.isGameComplete()) {
			main.openCongrats();
		}
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
				int column = i;
				cardPane.setOnMouseClicked(event -> foundationClick(column));
				foundations[i].getChildren().add(cardPane);
			}
			
			if(currentFoundation.isEmpty()) {
				Pane emptyPane = getNewPane();
				int column = i;
				emptyPane.setOnMouseClicked(event -> foundationClick(column));
				foundations[i].getChildren().add(emptyPane);
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
				int row = offset;
				int column = i;
				cardPane.setOnMouseClicked(event -> tableauClick(column, row / 20));
				tableaus[i].setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableaus[i].getChildren().add(cardPane);
				offset += 20;
				
			}
			if(currentTableau.isEmpty()) {
				Pane emptyPane = getNewPane();
				int column = i;
				emptyPane.setOnMouseClicked(event -> emptyTableauClick(column));
				tableaus[i].setAlignment(emptyPane, Pos.TOP_CENTER);
				tableaus[i].getChildren().add(emptyPane);
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
		else {
			isFirstClick = true;
			cardPicked = null;
			sourceStack = null;
		}
		draw();
	}
	
	public void undoMove() {
		if(!moveHistory.isEmpty()) {
			this.currentGame = moveHistory.pop();
			isFirstClick = true;
			cardPicked = null;
			sourceStack = null;
			clearBoard();
			draw();
		}
	}
	
	@FXML
	public void stockClick() {
		move(handleStockClick());
	}
	
	public boolean handleStockClick() {
		if(!isFirstClick) {
			return false;
		}
		cloneGame();
		
		Stock currentStock = currentGame.getStock();
		if(currentStock.isEmpty()) {
			return currentGame.recycleWaste();
		}
		else {
			return currentGame.stockToWaste();
		}
	}
	
	@FXML
	public void wasteClick() {
		handleWasteClick();
	}
	
	public void handleWasteClick() {
		if(!isFirstClick) {
			undoMove();
			isFirstClick = true;
			cardPicked = null;
			sourceStack = null;
		}
		else {
			if(currentGame.getWaste().isEmpty()) {
				isFirstClick = true;
			}
			else {
				cloneGame();
				cardPicked = currentGame.getWaste().deal();
				isFirstClick = false;
			}			
		}
	}
	
	
	public void tableauClick(int column, int row) {
		handleTableauClick(column, row);
	}
	
	public void handleTableauClick(int column, int row) {
		Tableau currentTableau = currentGame.getTableau(column);
		if(isFirstClick) {
			cloneGame();
			sourceStack = currentTableau.getPile(row);
			if(sourceStack.isEmpty()) {
				isFirstClick = true;
				cardPicked = null;
				sourceStack = null;
			}
			else {
				isFirstClick = false;
				cardPicked = sourceStack.peek();
			}	
		}
		else {
			if(row != currentTableau.getTableau().size() - 1) {
				move(false);
			}
			else {
				System.out.println(cardPicked);
				if(!currentTableau.isValidTableauMove(cardPicked)) {
					move(false);
				}
				else {
					if(sourceStack == null) {
						currentTableau.acceptCard(cardPicked);
					}
					else {
						currentTableau.acceptCard(sourceStack);
					}
					move(true);
				}
			}
		}
	}
	
	
	public void emptyTableauClick(int column) {
		Tableau currentTableau = currentGame.getTableau(column);
		if(isFirstClick) {
			isFirstClick = true;
			cardPicked = null;
			sourceStack = null;
		}
		else {
			if(!currentTableau.isValidTableauMove(cardPicked)) {
				move(false);
			}
			else {
				if(sourceStack == null) {
					currentTableau.acceptCard(cardPicked);
				}
				else {
					currentTableau.acceptCard(sourceStack);
				}
				move(true);
			}
		}
	}
	
	public void foundationClick(int column) {
		Foundation currentFoundation = currentGame.getFoundation(column);
		if(isFirstClick) {
			cloneGame();
			if(currentFoundation.isEmpty()) {
				isFirstClick = true;
				cardPicked = null;
				sourceStack = null;
			}
			else {
				cardPicked = currentFoundation.deal();
				isFirstClick = false;
				sourceStack = null;
			}
		}
		else {
			if(sourceStack != null) {
				if(sourceStack.size() != 1) {
					move(false);
				}
				else {
					if(currentFoundation.isValidFoundationMove(cardPicked)) {
						currentFoundation.acceptCard(cardPicked);
						move(true);
					}
					else {
						move(false);
					}
				}
			}		
			else {
				if(currentFoundation.isValidFoundationMove(cardPicked)) {
					currentFoundation.acceptCard(cardPicked);
					move(true);
				}
				else {
					move(false);
				}
			}
		}
	}
	
	public void cloneGame() {
		Game cloneGame = currentGame.getClone();
		moveHistory.push(cloneGame);
	}
}
