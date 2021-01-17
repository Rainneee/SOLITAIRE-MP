package application;
	
import application.model.Deck;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

//nagcommit ba??? yahhhh
//Ito ba main menu dapat?
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//launch(args);
		/**
		 * Test for Card
		 */
		//Card card = new Card(Rank.FOUR, Suit.SPADE);
		//System.out.println(card);
		
		
		/**
		 * Test for deck
		 */
		Deck deck = new Deck();
		System.out.println(deck);
	}
}
