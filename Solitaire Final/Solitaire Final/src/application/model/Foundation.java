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
