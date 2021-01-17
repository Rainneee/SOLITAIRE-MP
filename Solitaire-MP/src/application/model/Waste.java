package application.model;

import java.util.Stack;

/**
 * Represents a Waste in Solitaire
 * @author willn
 *
 */
public class Waste {
	private Stack<Card> waste = new Stack<Card>();
	
	/**
	 * Constructs waste
	 */
	Waste() {
		this.waste = null;
	}
	
	/**
	 * Receives a card from stock and adds it to the waste stack faceup
	 * @param card received from stock
	 */
	public void receiveCard(Card card) {
		if(!card.isFaceup()) {
			card.toggleFaceup();
		}
		waste.push(card);
	}
	
	/**
	 * Deals the top of the waste
	 * @return Card on top of the waste
	 */
	public Card deal() {
		if(!waste.isEmpty()) {
			return waste.pop();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Peeks the top card on waste without dealing the waste
	 * @return card that is on top of waste
	 */
	public Card peek() {
		if(!waste.isEmpty()) {
			return waste.peek();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Returns the cards on waste to the stock
	 * @return tempStack cards for the stock class
	 */
	public Stack<Card> returnToStock() {
		if(!waste.isEmpty()) {
			Stack<Card> tempStack = new Stack<Card>();
			while(!waste.isEmpty()) {
				if(waste.peek().isFaceup()) {
					waste.peek().toggleFaceup();
				}
				tempStack.push(waste.pop());
			}
			return tempStack;
		}
		return null;
	}
	
	/**
	 * String version of waste which is the top card
	 */
	public String toString() {
		if(!waste.isEmpty()) {
			return waste.peek().toString();
		}
		else {
			return "Empty";
		}
	}
}
