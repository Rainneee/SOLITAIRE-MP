package application.model;

import java.util.Stack;

/**
 * Represents a Foundation in a Solitaire game
 * @author willn
 *
 */
public class Foundation {
	private Stack<Card> foundation;
	
	/**
	 * Constructor for Foundation class
	 */
	public Foundation() {
		foundation = new Stack<Card>();
	}
	
	/**
	 * Constructor for implementing clone
	 */
	public Foundation(Stack<Card> foundation) {
		if(foundation == null) {
			this.foundation = new Stack<Card>();
		}
		else {
			this.foundation = foundation;
		}
	}
	
	/**
	 * Checks if the foundation is empty
	 * @return
	 */
	public boolean isEmpty() {
		return foundation.isEmpty();
	}
	
	/**
	 * Checks if the foundation can legally accept another card into it
	 * @param otherCard reference for the card to be accepted
	 * @return
	 */
	public boolean isValidFoundationMove(Card otherCard) {
		if(foundation.isEmpty()) {
			return otherCard.getRank() == Rank.ACE;
		}
		else {
			Card thisCard = foundation.peek();
			if(thisCard.getSuit() != otherCard.getSuit()) {
				return false;
			}
			else {
				return thisCard.getRank().ordinal() == otherCard.getRank().ordinal() - 1;
			}
		}
	}
	
	/**
	 * Accepts a card class, checks if it is valid and append it to foundation if valid
	 * @param card to be accepted
	 */
	public void acceptCard(Card card) {
		if(isValidFoundationMove(card)) {
			foundation.push(card);
		}
	}
	
	/**
	 * Gets the top of the foundation
	 * @return
	 */
	public Card deal() {
		if(!isEmpty()) {
			return foundation.pop();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Peeks top of foundation
	 * @return
	 */
	public Card peek() {
		if(!isEmpty()) {
			return foundation.peek();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Gets foundation deck
	 * @return
	 */
	public Stack<Card> getFoundation() {
		return foundation;
	}
	
	/**
	 * Creates a clone of foundation
	 * @return
	 */
	public Foundation getClone() {
		Stack<Card> cloneDeck = new Stack<Card>();
		for(Card card : foundation) {
			cloneDeck.push(card.getClone());
		}
		return new Foundation(cloneDeck);
	}
	
	/**
	 * Chekcs if the foundation is complete
	 * @return
	 */
	public boolean isComplete() {
		return foundation.size() == 13;
	}
	
	/**
	 * String representation of a Foundation
	 */
	public String toString() {
		if(!foundation.isEmpty()) {
			return foundation.peek().toString();
		}
		else {
			return "empty";
		}
	}
}
