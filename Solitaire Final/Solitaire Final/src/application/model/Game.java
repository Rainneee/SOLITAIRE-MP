package application.model;

import java.util.Stack;
import java.util.*;
import java.io.*;

import application.Main;
import java.util.Comparator;

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
	
	public void recordScore(String playerName, int Score)
	{
		int i = 0;
		
		HighScorer record = new HighScorer(playerName, Score);
		
		while(i < 10)
		{
			if(Main.high[i] == null)
			{
				Main.high[i] = record;
				HighscoreManager hm = new HighscoreManager();
		        hm.addScore(playerName,Score);
				break;
			}
			if(Main.high[i].getScore() < Score)
			{
				HighScorer swap = Main.high[i];
				Main.high[i] = record;
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
	
	public class Score {
	    private int score;
	    private String naam;

	    public int getScore() {
	        return score;
	    }

	    public String getNaam() {
	        return naam;
	    }

	    public Score(String naam, int score) {
	        this.score = score;
	        this.naam = naam;
	    }
	}
	public class HighscoreManager {
		private ArrayList<Score> scores;
		private static final String HIGHSCORE_FILE="highscores.dat";
		
		ObjectOutputStream outputstream=null;
		ObjectInputStream inputstream=null;
		
		public HighscoreManager() {
			scores= new ArrayList<Score>();
		}
		public ArrayList<Score> getScores() {
	        loadScoreFile();
	        sort();
	        return scores;
	    }
		private void sort() {
	        ScoreComparator comparator = new ScoreComparator();
	        Collections.sort(scores, comparator);
		}
		public void addScore(String name, int score) {
	        loadScoreFile();
	        scores.add(new Score(name, score));
	        updateScoreFile();
		}
		public void loadScoreFile() {
	        try {
	            inputstream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
	            scores = (ArrayList<Score>) inputstream.readObject();
	        } catch (FileNotFoundException e) {
	            System.out.println("[Laad] FNF Error: " + e.getMessage());
	        } catch (IOException e) {
	            System.out.println("[Laad] IO Error: " + e.getMessage());
	        } catch (ClassNotFoundException e) {
	            System.out.println("[Laad] CNF Error: " + e.getMessage());
	        } finally {
	            try {
	                if (outputstream != null) {
	                    outputstream.flush();
	                    outputstream.close();
	                }
	            } catch (IOException e) {
	                System.out.println("[Laad] IO Error: " + e.getMessage());
	            }
	        }
		}
		public void updateScoreFile() {
	        try {
	            outputstream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
	            outputstream.writeObject(scores);
	        } catch (FileNotFoundException e) {
	            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
	        } catch (IOException e) {
	            System.out.println("[Update] IO Error: " + e.getMessage());
	        } finally {
	            try {
	                if (outputstream != null) {
	                    outputstream.flush();
	                    outputstream.close();
	                }
	            } catch (IOException e) {
	                System.out.println("[Update] Error: " + e.getMessage());
	            }
	        }
		}
		
	}
	
	public class ScoreComparator implements Comparator<Score> {
        public int compare(Score score1, Score score2) {

            int sc1 = score1.getScore();
            int sc2 = score2.getScore();

            if (sc1 > sc2){
                return -1;
            }else if (sc1 < sc2){
                return +1;
            }else{
                return 0;
            }
        }
	}

	

}
