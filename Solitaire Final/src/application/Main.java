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
		if(topTen[0] == null) {
			topTen[0] = new HighScorer("Will",100);
			topTen[1] = new HighScorer("Rice",95);
			topTen[2] = new HighScorer("Romaine",90);
			topTen[3] = new HighScorer("Biboy",85);
			topTen[4] = new HighScorer("Mikasa",80);
			topTen[5] = new HighScorer("Sheshe",70);
			topTen[6] = new HighScorer("Marlou",75);
			topTen[7] = new HighScorer("Aling Vickie",65);
			topTen[8] = new HighScorer("Mang Kanor",60);
			topTen[9] = new HighScorer("hatdog",50);
		}
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
	
	/**
	 * 
	 * @author Romaine
	 */

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
	/**
	 * 
	 * @author Romaine
	 */

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
	/**
	 * 
	 * @author Romaine
	 */

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
