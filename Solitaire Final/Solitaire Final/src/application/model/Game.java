package application.model;

import java.util.Stack;

/**
 * Solitaire Game Representation
 * @author willn
 *
 */
public class Game {
	private Stock stock;
	private Waste waste;
	private Foundation[] foundations;
	private Tableau[] tableaus;
	//private Stack<Game> moveHistory;
	
	private int Score;
	
	/**
	 * Constructor for Game class
	 */
	public Game() {
		init();
		setupGame();
	}
	
	/**
	 * Initializes Game variables
	 */
	public void init() {
		Deck deck = new Deck();
		stock = new Stock(deck.getDeck());
		waste = new Waste();
		foundations = new Foundation[4];
		tableaus = new Tableau[7];
		//moveHistory = new Stack<Game>();
	}
	
	/**
	 * Setups the game of solitaire
	 */
	public void setupGame() {
		//Setup Tableau
		for(int i = 0; i < 7; i++) {
			Stack<Card> stack = new Stack<Card>();
			for(int j = 0; j < i + 1; j++) {
				stack.push(stock.deal());
			}
			Tableau temp = new Tableau();
			temp.acceptSetup(stack);
			tableaus[i] = temp;
		}
		
		//Setup Foundation
		for(int i = 0; i < 4; i++) {
			foundations[i] = new Foundation();
		}
		
		//Setup Score
		Score = 0;
	}
	
	/**
	 * Gets score
	 * @return
	 */
	public int getScore() {
		return Score;
	}
	
	/**
	 * Gets stock
	 * @return stock
	 */
	public Stock getStock() {
		return stock;
	}
	
	/**
	 * Gets waste
	 * @return waste
	 */
	public Waste getWaste() {
		return waste;
	}
	
	/**
	 * Gets foundation index i
	 * @param i index of foundation to get
	 * @return foundations[i]
	 */
	public Foundation getFoundation(int i) {
		return foundations[i];
	}
	
	/**
	 * Gets tableau index i
	 * @param i index of tableau to get
	 * @return tableaus[i]
	 */
	public Tableau getTableau(int i) {
		return tableaus[i];
	}
	
	/**
	 * String Representation of a game of solitaire
	 */
	public String toString() {
		String gameString = new String();
		//Stock
		gameString += "STOCK:\t" + stock.toString() + "\n";
		
		//Waste
		gameString += "WASTE:\t" + waste.toString() + "\n";
		
		//Foundations
		for(int i = 0; i < 4; i++) {
			gameString += "FOUNDATION " + i + ":\t" 
					+ foundations[i].toString() + "\n";
		}
		
		//Tableaus
		for(int i = 0; i < 7; i++) {
			gameString += "TABLEAU " + i + ":\t" 
					+ tableaus[i].toString() + "\n";
		}
		
		gameString += "\n\n";
		return gameString;
	}
}
