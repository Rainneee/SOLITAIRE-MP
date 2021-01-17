package application.model;

import java.util.Collections;
import java.util.Stack;

/**
 * Class to represent a deck of cards
 * @author willn
 *
 */
public class Deck {
	private Stack<Card> deck = new Stack<Card>();
	
	/**
	 * Constructs a standard deck of cards then shuffled
	 */
	public Deck() {
		for(Suit suit : Suit.values()) {
			for(Rank rank : Rank.values()) {
				deck.add(new Card(rank, suit));
			}
		}
		this.shuffle();
	}
	
	/**
	 * Constructs deck based on another deck
	 * @param deck Another deck
	 */
	Deck(Deck deck) {
		this.deck = deck.deck;
	}
	
	/**
	 * Shuffles the current deck
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	/**
	 * Checks if the deck is empty
	 * @return true if it is empty, false otherwise
	 */
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	
	/**
	 * Deals the card at the top of the deck
	 * @return card on top of the deck
	 */
	public Card deal() {
		if(isEmpty()) {
			return null;
		}
		return this.deck.pop();
	}
	
	/**
	 * Peeks the top of the deck without dealing the card
	 * @return card that is on top of deck
	 */
	public Card peek() {
		if(!deck.isEmpty()) {
			return deck.peek();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Returns String name of cards in the deck
	 * @return String name of cards in the deck
	 */
	public String toString() {
		String deckString = new String();
		for(Card card: deck) {
			deckString += card + "\n";
		}
		return deckString;
	}
}
