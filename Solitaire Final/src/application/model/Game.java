package application.model;

import java.util.Stack;

import application.Main;

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
	public int Score;
	
	/**
	 * Constructor for Game class
	 */
	public Game() {
		init();
		setupGame();
	}
	
	/**
	 * Constructor class for implementation of cloning
	 * @param game
	 */
	public Game(Stock stock, Waste waste, Foundation[] foundations, 
			Tableau[] tableaus, int Score) {
		init();
		this.stock = stock.getClone();
		this.waste = waste.getClone();
		for(int i = 0; i < 4; i++) {
			this.foundations[i] = foundations[i].getClone();
		}
		for(int i = 0; i < 7; i++) {
			this.tableaus[i] = tableaus[i].getClone();
		}
		this.Score = Score;
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
	 * Gets the instance of game itself for cloning
	 * @return this
	 */
	public Game getGame() {
		return this;
	}
	
	/**
	 * Updates score
	 * @param points to be added
	 */
	public void updateScore(int points) {
		Score += points;
		if(Score < 0) {
			Score = 0;
		}
	}
	/**
	 * 
	 * @author Romaine
	 */

	public void recordScore(String playerName, int Score)
	{
		int i = 0;
		
		HighScorer record = new HighScorer(playerName, Score);
		
		while(i < 10)
		{
			if(Main.topTen[i] == null)
			{
				Main.topTen[i] = record;
				break;
			}
			if(Main.topTen[i].getScore() < Score)
			{
				HighScorer swap = Main.topTen[i];
				Main.topTen[i] = record;
				record = swap;
			}	
			i++;
		}
	}
	
	/**
	 * Recycles waste
	 * @return true if success
	 */
	public boolean recycleWaste() {
		if(waste.isEmpty()) {
			return false;
		}
		else {
			updateScore(-100);
			Stack<Card> tempStack = new Stack<Card>();
			while(!waste.isEmpty()) {
				tempStack.push(waste.deal());
			}
			stock.setStock(tempStack);
			return true;
		}
	}
	
	/**
	 * Deals from stock to waste
	 * @return true if success
	 */
	public boolean stockToWaste() {
		waste.receiveFromStock(stock.deal());
		return true;
	}
	
	/**
	 * Creates clone of game class
	 * @return
	 */
	public Game getClone() {
		Stock cloneStock = stock.getClone();
		Waste cloneWaste = waste.getClone();
		Foundation[] cloneFoundation = new Foundation[4];
		for(int i = 0; i < 4; i++) {
			cloneFoundation[i] = foundations[i].getClone();
		}
		Tableau[] cloneTableau = new Tableau[7];
		for(int i = 0; i < 7; i++) {
			cloneTableau[i] = tableaus[i].getClone();
		}
		
		return new Game(cloneStock, cloneWaste, cloneFoundation, 
				cloneTableau, Score);
	}
	
	/**
	 * Checks if the game is won by the player
	 * @return
	 */
	public boolean isGameComplete() {
		return foundations[0].isComplete() && foundations[1].isComplete()
				&& foundations[2].isComplete() && foundations[3].isComplete();
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
