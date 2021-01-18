package application;
	
import java.util.Stack;

import application.controller.MainGameController;
import application.controller.MainMenuController;
import application.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		openMainMenu();
	}
	
	private void openMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
			AnchorPane mainMenuFXML = (AnchorPane) loader.load();

			Scene scene = new Scene(mainMenuFXML);
			primaryStage.setTitle("Solitaire");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			MainMenuController menuController = loader.getController();
			menuController.setMain(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openGame() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainGame.fxml"));
			AnchorPane mainGameFXML = (AnchorPane) loader.load();

			Scene scene = new Scene(mainGameFXML);
			primaryStage.setTitle("Solitaire");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			MainGameController mainGameController = loader.getController();
			mainGameController.setMain(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		primaryStage.close();
	}
	
	public static void main(String[] args) {
		launch(args);

	}
	
}
