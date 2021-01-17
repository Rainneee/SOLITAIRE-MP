package application.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A representation of a deck of cards using an ArrayList of Card objects. Can
 * either be instantiated as a new deck, or as a copy of an old deck
 */
public class Deck extends ArrayList<Card> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */


	/**
	 * Constructor for the Deck class with no arguments. Initializes the deck to
	 * contain every card, then shuffles to randomize order
	 */
	public Deck() {
		for (String currentSuit : Card.getSuitsCodes()) {
			for (String currentValue : Card.getValuesCodes()) {
				this.add(new Card(currentSuit, currentValue));
			}
		}
		this.shuffle();

	}

	/**
	 * Constructor for the Deck class with one argument. Initializes the Deck to be
	 * an exact copy of another Deck
	 * 
	 * @param oldDeck The deck to be copied into the new Deck
	 */
	public Deck(Deck oldDeck) {
		for (Card currentCard : oldDeck) {
			this.add(new Card(currentCard.getCardSuit(), currentCard.getCardValue()));
		}
	}

	/**
	 * Shuffles this deck
	 */
	public void shuffle() {
		Collections.shuffle(this);
	}

}
