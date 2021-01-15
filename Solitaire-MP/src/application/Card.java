package application;

/**
 * Represents a card
 * @author willn
 *
 */
public class Card {
	private final Rank rank;
	private final Suit suit;
	
	private boolean active;
	
	/**
	 * Constructs a new card with specific rank, suit, and color
	 * @param rank The Card's rank
	 * @param suit The Card's suit
	 */
	Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		this.active = false;
	}
	
	/**
	 * Getter of Card's rank
	 * @return Card's rank
	 */
	public Rank getRank() {
		return this.rank;
	}
	
	/**
	 * Getter of Card's suit
	 * @return Card's suit
	 */
	public Suit getSuit() {
		return this.suit;
	}
	
	/**
	 * Checks the card if it is red
	 * @return true if card is RED, false otherwise
	 */
	public boolean isRed() {
		return (suit == Suit.DIAMOND || suit == Suit.DIAMOND);
	}
	
	/**
	 * Checks the card if it is black
	 * @return true if card is BLACK, false otherwise
	 */
	public boolean isBlack() {
		return (suit == Suit.CLUB || suit == Suit.SPADE);
	}
	
	/**
	 * Gets the string name of the card
	 * @return String name of the card
	 */
	public String toString() {
		return rank.toString().toLowerCase() + "of" + suit.toString().toLowerCase() + "s";
	}
}