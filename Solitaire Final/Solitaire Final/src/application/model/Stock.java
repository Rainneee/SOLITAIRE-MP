package application.model;

import java.util.Stack;

/**
 * Represents a Stock in a game of solitaire
 * @author willn
 *
 */
public class Stock {
	private Stack<Card> stock = new Stack<Card>();
	
	/**
	 * Constructs Stock class
	 * @param stock Stack of cards to be received
	 */
	public Stock(Stack<Card> stock) {
		setStock(stock);
	}
	
	/**
	 * Sets stock
	 * @param stock Stack of cards to be set
	 */
	public void setStock(Stack<Card> stock) {
		for(Card card : stock) {
			card.turnFacedown();
		}
		this.stock = stock;
	}
	
	/**
	 * Checks if the stock is empty
	 * @return
	 */
	public boolean isEmpty() {
		return stock.isEmpty();
	}
	
	/**
	 * Deals the top of the stock
	 * @return
	 */
	public Card deal() {
		if(!isEmpty()) {
			return stock.pop();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Gets stock deck
	 * @return
	 */
	public Stack<Card> getStock() {
		return stock;
	}
	
	/**
	 * Peeks top of Stock
	 * @return
	 */
	public Card peek() {
		return stock.peek();
	}
	
	/**
	 * String representation of a Stock
	 */
	public String toString() {
		if(isEmpty()) {
			return "empty";
		}
		else {
			return stock.peek().toString();
		}
	}
}
