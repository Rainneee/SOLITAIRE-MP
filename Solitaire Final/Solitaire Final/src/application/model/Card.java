package application.model;

/**
 * Represents a card
 * @author willn
 *
 */
public class Card {
	private final Rank rank;
	private final Suit suit;
	
	private boolean faceup = false, active = false;
	
	/**
	 * Constructor for card class
	 * @param rank enum representation of rank
	 * @param suit enum representation of suit
	 */
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;

		faceup = false;
	}
	
	/**
	 * Gets card rank
	 * @return rank
	 */
	public Rank getRank() {
		return rank;
	}
	
	/**
	 * Gets card suit
	 * @return suit
	 */
	public Suit getSuit() {
		return suit;
	}
	
	/**
	 * Checks if the card is red
	 * @return
	 */
	public boolean isRed() {
		return (suit == Suit.DIAMOND || suit == Suit.HEART);
	}
	
	/**
	 * Checks if the card is black
	 * @return
	 */
	public boolean isBlack() {
		return (suit == Suit.CLUB || suit == Suit.SPADE);
	}
	
	/**
	 * Checks if the card is Face up
	 * @return
	 */
	public boolean isFaceUp() {
		return faceup;
	}
	
	/**
	 * Checks if the card is Face down
	 * @return
	 */
	public boolean isFaceDown() {
		return !faceup;
	}
	
	/**
	 * Sets the card face up
	 */
	public void turnFaceup() {
		faceup = true;
	}
	
	/**
	 * Sets the card face down
	 */
	public void turnFacedown() {
		faceup = false;
	}
	
	/**
	 * Toggles activity of the card
	 */
	public void toggleActive() {
		active = !active;
	}
	
	/**
	 * Checks if the card is active
	 * @return
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Compares another card if they are the same
	 * @param card the other carx to compare with
	 * @return
	 */
	public boolean equals(Card card) {
		return (this.rank == card.getRank()) && (this.suit == card.getSuit());
	}
	
	/**
	 * String representation of a card
	 */
	public String toString() {
		if(isFaceDown()) {
			return "facedown";
		}
		return rank.toString().toLowerCase() + "of" + suit.toString().toLowerCase() + "s";
	}
	
}
