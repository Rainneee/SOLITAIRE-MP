package application.model;

import java.util.Stack;

/**
 * Represents a stock in a game of solitaire
 * @author willn
 *
 */
public class Stock {
	private Stack<Card> stock = new Stack<Card>();
	
	/**
	 * Constructs a stock
	 */
	Stock() {
		setStock(null);
	}
	
	/**
	 * Constructs a stock 
	 * @param stock a Stack of Cards
	 */
	Stock(Stack<Card> stock) {
		setStock(stock);
	}
	
	/**
	 * Setter for stock
	 * @param stock a Stack of Cards
	 */
	public void setStock(Stack<Card> stock) {
		for(Card card : stock) {
			if(card.isFaceup()) {
				//Set the card facedown
				card.toggleFaceup();
			}			
		}
		this.stock = stock;
	}
	
	/**
	 * Deals the top card of the stock
	 * @return top card of the stock
	 */
	public Card dealToWaste() {
		if(!stock.isEmpty()) {
			stock.peek().toggleFaceup(); // Sets the card faceup
			return stock.pop();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Peeks the top of stock without dealing the card
	 * @return card on top of stock
	 */
	public Card peek() {
		if(!stock.isEmpty()) {
			return stock.peek();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Gets the string version of the stock
	 * @return "facedown" as the stock is always facedown
	 */
	public String toString() {
		return "facedown";
	}
}
