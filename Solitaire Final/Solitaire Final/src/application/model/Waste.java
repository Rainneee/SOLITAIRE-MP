package application.model;

import java.util.Stack;

/**
 * Represents a Waste in a game of solitaire
 * @author willn
 *
 */
public class Waste {
	private Stack<Card> waste;
	
	/**
	 * Constructor for Waste class
	 */
	public Waste() {
		waste = new Stack<Card>();
	}
	
	/**
	 * Constructor for implementing cloning
	 * @param waste
	 */
	public Waste(Stack<Card> waste) {
		if(waste == null) {
			this.waste = new Stack<Card>();
		}
		else {
			this.waste = waste;
		}
	}
	
	/**
	 * Receives a card assuming it is from the stock
	 * @param card
	 */
	public void receiveFromStock(Card card) {
		card.turnFaceup();
		waste.push(card);
	}
	
	/**
	 * Checks if waste is empty
	 * @return
	 */
	public boolean isEmpty() {
		return waste.isEmpty();
	}
	
	/**
	 * Gets waste
	 * @return waste
	 */
	public Stack<Card> getWaste() {
		return waste;
	}
	
	/**
	 * Deals the top of the waste
	 * @return card that is on top of waste
	 */
	public Card deal() {
		if(!isEmpty()) {
			return waste.pop();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Peeks at the top of the waste
	 * @return
	 */
	
	public Card peek() {
		if(!isEmpty()) {
			return waste.peek();
		}
		else {
			return null;
		}
	}
	
	public Waste getClone() {
		Stack<Card> cloneDeck = new Stack<Card>();
		for(Card card : waste) {
			cloneDeck.push(card.getClone());
		}
		return new Waste(cloneDeck);
	}
	
	/**
	 * String representation of Waste Class
	 */
	public String toString() {
		if(isEmpty()) {
			return "empty";
		}
		else {
			return waste.peek().toString();
		}
	}
}
