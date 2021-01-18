package application.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class that models a game of solitaire to use as a model for the game
 * presented to the user. It uses a Deck object that has been shuffled to
 * populate Deques used as stacks to represent literal stacks of cards.
 */
public class Game {
	private Deck gameDeck;
	private List<Deque<Card>> foundations;
	private Deque<Card> draw;
	private Deque<Card> tempCardStack;
	private Deque<Card> drawDiscard;
	private IntegerProperty drawType;
	private static Integer[] drawNumber = { 1, 3 };
	private List<Deque<Card>> playArea;
	
	public boolean didrecycle = false;

	/**
	 * Constructor with no arguments. When it is called, it creates a new Deck.
	 * Then, each of the stacks are initialized, the drawType property is created,
	 * and the default draw number is set to 3. The startNewGame method is then
	 * called in order to populate each of the stacks with the proper number of
	 * cards
	 */
	public Game() {
		// Create a new Deck
		setGameDeck(new Deck());
		// Initialize the "foundations", or the 4 spaces to put each solved suit, by
		// created a new Deque for each position
		this.foundations = new ArrayList<Deque<Card>>();
		for (int i = 0; i < 4; i++) {
			this.foundations.add(new ArrayDeque<Card>());
		}

		// Add a new Deque for the draw pile
		this.draw = new ArrayDeque<Card>();
		this.drawDiscard = new ArrayDeque<Card>();
		// Add a new Deque to hold cards that are being moved
		this.tempCardStack = new ArrayDeque<Card>();

		// Initialize the 7 card Deques for the play area
		this.playArea = new ArrayList<Deque<Card>>(7);
		for (int i = 0; i < 7; i++) {
			this.playArea.add(i, new ArrayDeque<Card>(i + 1));
		}
		this.drawType = new SimpleIntegerProperty();
		this.setDrawType(3);
		this.startNewGame();
	}

	/**
	 * Constructor used to repeat a game. When it is called, it creates a new Deck
	 * that is a copy of the passed oldDeck. Then, each of the stacks are
	 * initialized, the drawType property is created, and the default draw number is
	 * set to 3. The startNewGame method is then called in order to populate each of
	 * the stacks with the proper number of cards
	 * 
	 * @param oldDeck The deck to be used as to populate the game
	 */
	public Game(Deck oldDeck) {
		// Create a new Deck
		setGameDeck(new Deck(oldDeck));
		// Initialize the "foundations", or the 4 spaces to put each solved suit, by
		// created a new Deque for each position
		this.foundations = new ArrayList<Deque<Card>>();
		for (int i = 0; i < 4; i++) {
			this.foundations.add(new ArrayDeque<Card>());
		}

		// Add a new Deque for the draw pile
		this.draw = new ArrayDeque<Card>();
		this.drawDiscard = new ArrayDeque<Card>();
		// Add a new Deque to hold cards that are being moved
		this.tempCardStack = new ArrayDeque<Card>();

		// Initialize the 7 card Deques for the play area
		this.playArea = new ArrayList<Deque<Card>>(7);
		for (int i = 0; i < 7; i++) {
			this.playArea.add(i, new ArrayDeque<Card>(i + 1));
		}
		this.drawType = new SimpleIntegerProperty();
		this.setDrawType(3);
		this.startNewGame();
	}

	/**
	 * This method needs to be called in order to fill each stack with the proper
	 * number of cards in the correct order. It 'deals' cards from the deck in much
	 * the same way as would be done if playing a normal game of solitaire
	 * 
	 */
	public void startNewGame() {
		for (int i = 0; i < 7; i++) {
			for (int j = i; j < 7; j++) {
				int lastElement = gameDeck.size() - 1;
				this.playArea.get(j).add(this.gameDeck.get(lastElement));
				this.gameDeck.remove(lastElement);
				if (j == i) {
					this.playArea.get(j).peek().setIsFaceUp(true);
				}
			}
		}
		this.draw.addAll(gameDeck);
	}

	/**
	 * Function that draws the next card from the top of the draw pile. If the draw
	 * pile is not empty, then it takes the proper number of cards from the top. It
	 * stops drawing cards when the draw stack is empty. If the draw stack is empty,
	 * then it moves all cards back over to the draw stack
	 * 
	 */
	public void drawNextCard() {
		// If the draw pile is empty, then move all cards from the discard stack to the
		// draw stack
		if (this.draw.isEmpty()) {
			for (Card currentCard : this.drawDiscard) {
				currentCard.setIsFaceUp(false);
				this.draw.push(currentCard);
			}
			this.drawDiscard.clear();
			didrecycle = true;
			return;
		}

		// Otherwise, move up to the draw number of cards from the draw stack to the
		// draw discard stack
		for (int i = 0; i < Game.drawNumber[this.getDrawType()] && !this.draw.isEmpty(); i++) {
			this.draw.peek().setIsFaceUp(true);
			this.drawDiscard.push(this.draw.pop());
		}
		didrecycle = false;
	}

	/**
	 * Move a card from any stack to one of the playArea stacks. The main difference
	 * between this function and moveCardToFoundation is which card checking
	 * function is called. If it is valid, then all cards above the card to be moved
	 * are moved with it. This method does not necessarily move a card if the move
	 * is not valid
	 * 
	 * @param cardToMove   The card that is being moved
	 * @param sourceColumn A reference to the stack that the cardToMove is coming
	 *                     from
	 * @param destColumn   A reference to the stack that the cardToMove is trying to
	 *                     be moved to
	 * @return 
	 */
	public boolean moveCardToStack(Card cardToMove, Deque<Card> source, Deque<Card> dest) {
		if (source == dest) {
			return false;
		}
		// Can't move a card if it's not face up
		if (!cardToMove.getIsFaceUp() || !cardToMove.isValidTableCard(dest.peek())) {
			return false;
		}

		while (!source.peek().equals(cardToMove)) {
			this.tempCardStack.push(source.pop());
		}
		dest.push(source.pop());
		for (Card currentCard : this.tempCardStack) {
			dest.push(currentCard);
		}
		this.tempCardStack.clear();
		if (!source.isEmpty())
			source.peek().setIsFaceUp(true);
		
		didrecycle = false;
		return true;
	}

	/**
	 * Move a card from any stack to one of the foundation stacks. The main
	 * difference between this function and moveCardToStack is which card checking
	 * function is called. If it is valid, then all cards above the card to be moved
	 * are moved with it. This method does not necessarily move a card if the move
	 * is not valid
	 * 
	 * @param cardToMove   The card that is being moved
	 * @param sourceColumn A reference to the stack that the cardToMove is coming
	 *                     from
	 * @param destColumn   A reference to the stack that the cardToMove is trying to
	 *                     be moved to
	 * @return 
	 */
	public boolean moveCardToFoundation(Card cardToMove, Deque<Card> source, Deque<Card> dest) {
		if (source == dest) {
			return false;
		} else if (!cardToMove.getIsFaceUp() || !cardToMove.isValidFoundationCard(dest.peek())) {
			// Can't move a card if it's not face up
			return false;
		} else if (source.peek() != cardToMove) {
			return false;
		}
		
		dest.push(source.pop());
		if (!source.isEmpty())
			source.peek().setIsFaceUp(true);
		
		didrecycle = false;
		return true;
	}

	/**
	 * Checks to see if this Game can be considered complete. It is considered
	 * complete if every non-foundation stack is empty
	 * 
	 * @return True if the game is complete, false otherwise
	 */
	public Boolean checkGameComplete() {
		Boolean isGameDone = true;
		for (Deque<Card> currentPlayArea : this.playArea) {
			isGameDone = isGameDone && currentPlayArea.isEmpty();
		}

		isGameDone = isGameDone && this.draw.isEmpty();
		isGameDone = isGameDone && this.drawDiscard.isEmpty();
		isGameDone = isGameDone && this.tempCardStack.isEmpty();

		return isGameDone;

	}

	/**
	 * Getter for the current Game's deck
	 * 
	 * @return the gameDeck
	 */
	public Deck getGameDeck() {
		return gameDeck;
	}

	/**
	 * Setter for the current Game's deck
	 * 
	 * @param gameDeck A deck to be used as the gameDeck
	 */
	public void setGameDeck(Deck gameDeck) {
		this.gameDeck = gameDeck;
	}

	/**
	 * Getter for the draw stack
	 * 
	 * @return the Deque that represents the draw stack
	 */
	public Deque<Card> getDraw() {
		return draw;
	}

	/**
	 * Getter for the list of foundations
	 * 
	 * @return List of Deques that represent the foundations
	 */
	public List<Deque<Card>> getFoundations() {
		return foundations;
	}

	/**
	 * Getter for any particular foundation
	 * 
	 * @param index a number from 0 to 3 that references which foundation to get
	 * @return A Deque that represents the requested foundation stack
	 */
	public Deque<Card> getAFoundation(Integer index) {
		return foundations.get(index);
	}

	/**
	 * Getter for the drawDiscard Deque
	 * 
	 * @return the Deque representing the drawDiscard
	 */
	public Deque<Card> getDrawDiscard() {
		return drawDiscard;
	}

	/**
	 * Getter for the tempCardStack
	 * 
	 * @return the tempCardStack Deque
	 */
	public Deque<Card> getTempCardStack() {
		return tempCardStack;
	}

	/**
	 * Getter for the list of Deques that represent the playArea
	 * 
	 * @return the playArea
	 */
	public List<Deque<Card>> getPlayArea() {
		return playArea;
	}

	/**
	 * Getter for a particular stack in the playArea
	 * 
	 * @param index a number from 0 to 6 that references the playArea stack desired
	 * @return a Deque that represents the playArea stack requested
	 */
	public Deque<Card> getAPlayArea(Integer index) {
		return playArea.get(index);
	}

	/**
	 * Getter for the drawType, or number of cards to draw at a time
	 * 
	 * @return the drawType
	 */
	public Integer getDrawType() {
		return drawType.getValue();
	}

	/**
	 * Setter for the drawType, or number of cards to draw at a time
	 * 
	 * @param drawType How many cards to set the drawType to
	 */
	public void setDrawType(Integer drawNumber) {
		this.drawType.setValue(drawNumber);
	}

	/**
	 * Getter for the IntegerProperty that represents the drawType
	 * 
	 * @return the drawType as an integer property
	 */
	public IntegerProperty getDrawTypeProperty() {
		if (this.drawType == null) {
			this.drawType = new SimpleIntegerProperty();
		}
		return this.drawType;
	}

}
