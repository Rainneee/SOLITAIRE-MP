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
	private AnchorPane stock, waste, foundation1, foundation2, foundation3, foundation4;
	
	@FXML
	private StackPane tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7;
	
	@FXML
	private Button recycleWaste;
	
	@FXML
	private AnchorPane gamePane;
	
	@FXML
	private Canvas gameCanvas;
	
	private Main main;
	private Game currentGame;
	private Double cardHeight;
	private Double cardWidth;

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
		
		cardHeight = (double) 122;
		cardWidth = (double) 100;
		
		
	}
	
	public void draw() {
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
	
	public void drawStock() {
		Stock gameStock = currentGame.getStock();
		if(!gameStock.isEmpty()) {
			Card stockCard = gameStock.peek();
			recycleWaste.setStyle("-fx-background-image: url('" + stockCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
		else {
			recycleWaste.setStyle("-fx-background-image: none;"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 0");
		}
	}
	
	public void drawWaste() {
		Waste gameWaste = currentGame.getWaste();
		if(!gameWaste.isEmpty()) {
			Card wasteCard = gameWaste.peek();
			waste.setStyle("-fx-background-image: url('" + wasteCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
		else {
			waste.setStyle("-fx-background-image: none;"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 0");
		}
	}
	
	public void drawFoundations() {
		//foundation1
		Foundation currentFoundation = currentGame.getFoundation(0);
		if(!currentFoundation.isEmpty()) {
			Card foundationCard = currentFoundation.peek();
			foundation1.setStyle("-fx-background-image: url('" + foundationCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
		else {
			foundation1.setStyle("-fx-background-image: none;"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
		
		//foundation2
		currentFoundation = currentGame.getFoundation(1);
		if(!currentFoundation.isEmpty()) {
			Card foundationCard = currentFoundation.peek();
			foundation2.setStyle("-fx-background-image: url('" + foundationCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
		else {
			foundation2.setStyle("-fx-background-image: none;"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
				
		//foundation3
		currentFoundation = currentGame.getFoundation(2);
		if(!currentFoundation.isEmpty()) {
			Card foundationCard = currentFoundation.peek();
			foundation3.setStyle("-fx-background-image: url('" + foundationCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
		else {
			foundation3.setStyle("-fx-background-image: none;"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}		
		
		//foundation4
		currentFoundation = currentGame.getFoundation(3);
		if(!currentFoundation.isEmpty()) {
			Card foundationCard = currentFoundation.peek();
			foundation4.setStyle("-fx-background-image: url('" + foundationCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
		else {
			foundation4.setStyle("-fx-background-image: none;"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
		}
	}
	
	public void drawTableaus() {
		//tableau1
		Tableau currentTableau = currentGame.getTableau(0);
		if(!currentTableau.isEmpty()) {
			int offset = 0;
			for(Card tableauCard : currentTableau.getTableau()) {
				Pane cardPane = new Pane();
				cardPane.setMinSize(100,150);
				cardPane.setPrefSize(100,150);
				cardPane.setMaxSize(100,150);
				cardPane.setStyle("-fx-background-image: url('" + tableauCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");

				tableau1.setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableau1.getChildren().add(cardPane);
				offset += 20;
			}
		}
		else {
			tableau1.getChildren().clear();
		}
		
		//tableau2
		currentTableau = currentGame.getTableau(1);
		if(!currentTableau.isEmpty()) {
			int offset = 0;
			for(Card tableauCard : currentTableau.getTableau()) {
				Pane cardPane = new Pane();
				cardPane.setMinSize(100,150);
				cardPane.setPrefSize(100,150);
				cardPane.setMaxSize(100,150);
				cardPane.setStyle("-fx-background-image: url('" + tableauCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
				
				tableau2.setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableau2.getChildren().add(cardPane);
				offset += 20;
			}
		}
		else {
			tableau2.getChildren().clear();
		}
		
		//tableau3
		currentTableau = currentGame.getTableau(2);
		if(!currentTableau.isEmpty()) {
			int offset = 0;
			for(Card tableauCard : currentTableau.getTableau()) {
				Pane cardPane = new Pane();
				cardPane.setMinSize(100,150);
				cardPane.setPrefSize(100,150);
				cardPane.setMaxSize(100,150);
				cardPane.setStyle("-fx-background-image: url('" + tableauCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
				
				tableau3.setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableau3.getChildren().add(cardPane);
				offset += 20;
			}
		}
		else {
			tableau3.getChildren().clear();
		}
		
		//tableau4
		currentTableau = currentGame.getTableau(3);
		if(!currentTableau.isEmpty()) {
			int offset = 0;
			for(Card tableauCard : currentTableau.getTableau()) {
				Pane cardPane = new Pane();
				cardPane.setMinSize(100,150);
				cardPane.setPrefSize(100,150);
				cardPane.setMaxSize(100,150);
				cardPane.setStyle("-fx-background-image: url('" + tableauCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
				
				tableau4.setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableau4.getChildren().add(cardPane);
				offset += 20;
			}
		}
		else {
			tableau4.getChildren().clear();
		}
		
		//tableau5
		currentTableau = currentGame.getTableau(4);
		if(!currentTableau.isEmpty()) {
			int offset = 0;
			for(Card tableauCard : currentTableau.getTableau()) {
				Pane cardPane = new Pane();
				cardPane.setMinSize(100,150);
				cardPane.setPrefSize(100,150);
				cardPane.setMaxSize(100,150);
				cardPane.setStyle("-fx-background-image: url('" + tableauCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
				
				tableau5.setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableau5.getChildren().add(cardPane);
				offset += 20;
			}
		}
		else {
			tableau5.getChildren().clear();
		}
		
		//tableau6
		currentTableau = currentGame.getTableau(5);
		if(!currentTableau.isEmpty()) {
			int offset = 0;
			for(Card tableauCard : currentTableau.getTableau()) {
				Pane cardPane = new Pane();
				cardPane.setMinSize(100,150);
				cardPane.setPrefSize(100,150);
				cardPane.setMaxSize(100,150);
				cardPane.setStyle("-fx-background-image: url('" + tableauCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
				
				tableau6.setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableau6.getChildren().add(cardPane);
				offset += 20;
			}
		}
		else {
			tableau6.getChildren().clear();
		}
		
		//tableau7
		currentTableau = currentGame.getTableau(6);
		if(!currentTableau.isEmpty()) {
			int offset = 0;
			for(Card tableauCard : currentTableau.getTableau()) {
				Pane cardPane = new Pane();
				cardPane.setMinSize(100,150);
				cardPane.setPrefSize(100,150);
				cardPane.setMaxSize(100,150);
				cardPane.setStyle("-fx-background-image: url('" + tableauCard.getURL() + "');"
					+ "-fx-background-size: 100px 150px; -fx-opacity: 1");
				
				tableau7.setAlignment(cardPane, Pos.TOP_CENTER);
				cardPane.setTranslateY(offset);
				tableau7.getChildren().add(cardPane);
				offset += 20;
			}
		}
		else {
			tableau7.getChildren().clear();
		}
	}
	
	
}
