package application.model;

import java.util.Stack;

/**
 * Represents a Tableau in a game of solitaire
 * @author willn
 *
 */
public class Tableau {
	private Stack<Card> tableau;
	
	/**
	 * Constructs Tableau class
	 */
	public Tableau() {
		tableau = new Stack<Card>();
	}
	
	/**
	 * Constructor for clone implementation
	 */
	public Tableau(Stack<Card> tableau) {
		if(tableau == null) {
			this.tableau = new Stack<Card>();
		}
		else {
			this.tableau = tableau;
		}
	}
	
	/**
	 * Checks if tableau is empty
	 * @return
	 */
	public boolean isEmpty() {
		return tableau.isEmpty();
	}
	
	/**
	 * Checks if the tableau can legally accept the other card
	 * @param otherCard reference for the card to be accepted 
	 * @return
	 */
	public boolean isValidTableauMove(Card otherCard) {
		if(isEmpty()) {
			return otherCard.getRank() == Rank.KING;
		}
		if(tableau.peek().isRed() == otherCard.isRed()) {
			return false;
		}
		if(tableau.peek().getRank().ordinal() 
				!= otherCard.getRank().ordinal() + 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Accepts a stack of card without checking for validation
	 * Assumes it is a setup move
	 * @param stack
	 */
	public void acceptSetup(Stack<Card> stack) {
		while(!stack.isEmpty()) {
			tableau.push(stack.pop());
		}
		turnFaceUpPeek();
	}
	
	/**
	 * Accepts a card to be added in the tableau if valid
	 * @param card
	 */
	public void acceptCard(Card card) {
		if(card == null) {
			return;
		}
		if(isValidTableauMove(card)) {
			tableau.push(card);
		}
	}
	
	/**
	 * Accepts a stack of card to be added in the tableau if valid
	 * @param otherTableau
	 */
	public void acceptCard(Stack<Card> otherTableau) {
		if(otherTableau.isEmpty()) {
			return;
		}
		if(isValidTableauMove(otherTableau.peek())) {
			while(!otherTableau.isEmpty()) {
				tableau.push(otherTableau.pop());
			}
		}
	}
	
	/**
	 * Gets a stack of card from index y to the top of tableau
	 * @param y index to take the card from onwards
	 * @return stack of cards
	 */
	public Stack<Card> getPile(int y) {
		if(y < tableau.size()) {
			Card card = tableau.get(y);
		}
		Stack<Card> stack = new Stack<Card>();
		for(int i = tableau.size() - 1; i >= y; i--) {
			stack.push(tableau.pop());
		}
		turnFaceUpPeek();
		return stack;
	}
	
	/**
	 * Gets tableau
	 * @return tableau
	 */
	public Stack<Card> getTableau() {
		return tableau;
	}
	
	/**
	 * Turns the top card of the tableau face up
	 */
	public void turnFaceUpPeek() {
		if(!tableau.isEmpty()) {
			tableau.peek().turnFaceup();
		}
	}
	
	public Tableau getClone() {
		Stack<Card> cloneDeck = new Stack<Card>();
		for(Card card : tableau) {
			cloneDeck.push(card.getClone());
		}
		return new Tableau(cloneDeck);
	}
	
	/**
	 * String representation of a tableau
	 */
	public String toString() {
		if(isEmpty()) {
			return "empty";
		}
		
		String tableauString = new String();
		for(Card card : tableau) {
			tableauString += card.toString() + "\t";
		}
		return tableauString;
	}
}
