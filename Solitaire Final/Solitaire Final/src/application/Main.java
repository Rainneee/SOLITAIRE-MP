package application;
	
import application.controller.CongratsController;
import application.controller.GameOverController;
import application.controller.HighScoresController;
import application.controller.MainGameController;
import application.controller.MainMenuController;
import application.controller.PlayerController;
import application.model.HighScorer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	private Stage primaryStage;
	public static String player = "Player";
	public static HighScorer[] topTen = new HighScorer[10];
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		openMainMenu();
	}
	
	public void openMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainMenu.fxml"));
			AnchorPane mainMenuFXML = (AnchorPane) loader.load();

			Scene scene = new Scene(mainMenuFXML);
			primaryStage.setTitle("Solitaire");
			primaryStage.setResizable(false);

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
			primaryStage.setResizable(false);

			primaryStage.setScene(scene);
			primaryStage.show();
			
			MainGameController mainGameController = loader.getController();
			mainGameController.setMain(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void openHighScores() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/HighScores.fxml"));
			AnchorPane highScoresFXML = (AnchorPane) loader.load();
	
			Scene scene = new Scene(highScoresFXML);
			primaryStage.setTitle("High Scores");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			HighScoresController highScoresController = loader.getController();
			highScoresController.setTable(topTen);
			highScoresController.setMain(this);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getPlayer(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Player.fxml"));
			AnchorPane playerNameFXML = (AnchorPane) loader.load();
	
			Scene scene = new Scene(playerNameFXML);
			primaryStage.setTitle("Solitaire");
			primaryStage.setResizable(false);

			primaryStage.setScene(scene);
			primaryStage.show();
			
			PlayerController mainGameController = loader.getController();
			mainGameController.setMain(this);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openCongrats() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Congrats.fxml"));
			AnchorPane congratsTextFXML = (AnchorPane) loader.load();
	
			Scene scene = new Scene(congratsTextFXML);
			primaryStage.setTitle("Solitaire");
			primaryStage.setResizable(false);

			primaryStage.setScene(scene);
			primaryStage.show();
			
			CongratsController mainGameController = loader.getController();
			mainGameController.message(this,  MainGameController.currentGame);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openGameOver() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GameOver.fxml"));
			AnchorPane gameOverFXML = (AnchorPane) loader.load();
	
			Scene scene = new Scene(gameOverFXML);
			primaryStage.setTitle("Solitaire");
			primaryStage.setResizable(false);

			primaryStage.setScene(scene);
			primaryStage.show();
			
			GameOverController mainGameController = loader.getController();
			mainGameController.message(this, MainGameController.currentGame);
		
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
