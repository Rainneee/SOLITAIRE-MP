package application.model;

import java.util.Collections;
import java.util.Stack;

/**
 * Represents a deck of cards
 * @author willn
 *
 */
public class Deck {
	private Stack<Card> deck = new Stack<Card>();
	
	/**
	 * Constructor for Deck class
	 */
	public Deck() {
		for(Suit suit : Suit.values()) {
			for(Rank rank : Rank.values()) {
				deck.push(new Card(rank, suit));
			}
		}
		shuffle();
	}
	
	/**
	 * Shuffles the deck
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	/**
	 * Checks if the deck is empty
	 * @return
	 */
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	
	/**
	 * Deals the top of the card
	 * @return top of the deck if not empty, null otherwise
	 */
	public Card deal() {
		if(!isEmpty()) {
			return deck.pop();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Gets deck
	 * @return
	 */
	public Stack<Card> getDeck() {
		return deck;
	}
	
	/**
	 * String representation of a Deck
	 */
	public String toString() {
		if(isEmpty()) {
			return "empty";
		}
		else {
			String deckString = new String();
			for(Card card : deck) {
				deckString += card.toString() + "\n";
			}
			return deckString;
		}
	}
}
