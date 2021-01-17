package application.model;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that models cards and some of the relationships between them. Cards
 * values are represented by a pair of single character strings, one for suit
 * and one for value. There are static Maps that translate the strings to values
 * for some lookup functions
 */
public class Card {
	private static final Map<String, String> suitsMap;
	private static final Map<String, Integer> valuesMap;
	private static final String fileExtension = ".png";

	static {
		valuesMap = new HashMap<String, Integer>();
		valuesMap.put("a", 1);
		valuesMap.put("2", 2);
		valuesMap.put("3", 3);
		valuesMap.put("4", 4);
		valuesMap.put("5", 5);
		valuesMap.put("6", 6);
		valuesMap.put("7", 7);
		valuesMap.put("8", 8);
		valuesMap.put("9", 9);
		valuesMap.put("T", 10);
		valuesMap.put("J", 11);
		valuesMap.put("Q", 12);
		valuesMap.put("K", 13);

		suitsMap = new HashMap<String, String>();
		suitsMap.put("h", "Hearts");
		suitsMap.put("d", "Diamonds");
		suitsMap.put("c", "Clubs");
		suitsMap.put("s", "Spades");

	}

	private String cardSuit;
	private String cardValue;
	private Boolean isRed;
	private Boolean isFaceUp;

	/**
	 * Function that gets the location of the image that corresponds to the face of
	 * a particular card
	 * 
	 * 
	 * @param suit  A one character code to match a suit
	 * @param value The face value of the card
	 * @return A file object for the given suit and value
	 */
	public static File getFaceLocation(String suit, String value) {
		return new File("COMPRESSED CARDS/" + suitsMap.get(suit) + "/" + value + suit + fileExtension);
	}

	/**
	 * A wrapper for the static method that gets the location of this card
	 * 
	 * @return A file object that matches the face of this card
	 */
	public File getFaceLocation() {
		return Card.getFaceLocation(this.cardSuit, this.cardValue);
	}

	/**
	 * Function to return whether it is valid to put this card on top of any given
	 * foundation stack; That is, whether is it possible to put them "away" or out
	 * of play
	 * 
	 * @param otherCard
	 * @return
	 */
	public Boolean isValidFoundationCard(Card otherCard) {

		Integer thisCardValue = Card.valuesMap.get(this.getCardValue());
		if (otherCard == null) {
			return (thisCardValue == 1);
		}
		Integer otherCardValue = Card.valuesMap.get(otherCard.getCardValue());
		return (thisCardValue == (otherCardValue + 1) && this.getCardSuit() == otherCard.getCardSuit());

	}

	/**
	 * Function to return whether it is valid to put a card on top of another card
	 * that is face-up and in play
	 * 
	 * @param otherCard the card this card is being checked against
	 * @return Whether or not this card is able to be played on top of the other
	 *         card on the table
	 */
	public Boolean isValidTableCard(Card otherCard) {
		Integer thisCardValue = Card.valuesMap.get(this.getCardValue());
		if (otherCard == null) {
			return (thisCardValue == 13);
		}
		Integer otherCardValue = Card.valuesMap.get(otherCard.getCardValue());
		// Return true if the other card is one higher than this card and they are not
		// the same color
		return (thisCardValue == (otherCardValue - 1) && this.getIsRed() != otherCard.getIsRed());

	}

	/**
	 * Getter for the suitsMap keys, returns all possible card suits
	 * 
	 * @return All the keys in the suitsMap
	 */
	public static Collection<String> getSuitsCodes() {
		return suitsMap.keySet();
	}

	/**
	 * Getter for the valuesMap keys, returns a set of all possible card values
	 * 
	 * @return All the keys in the valuesMap
	 */
	public static Collection<String> getValuesCodes() {
		return valuesMap.keySet();
	}

	/**
	 * Constructor for the Card class. Sets the suit and value of the card to the
	 * strings passed as parameters
	 * 
	 * @param suit  One character string representation of the suit of the card to
	 *              create
	 * @param value One character string representation of the value of the card to
	 *              create
	 */
	public Card(String suit, String value) {
		this.setCardSuit(suit);
		this.setCardValue(value);
		this.setIsFaceUp(false);
		if (suit == "h" || suit == "d") {
			this.setIsRed(true);
		} else {
			this.setIsRed(false);
		}
	}

	/**
	 * Method to compare to cards to each other. Returns true if they both have the
	 * same suit and value
	 * 
	 * @param otherCard A card to compare this card to
	 * @return True if both cards have the same suit and value, false otherwise
	 */
	public Boolean equals(Card otherCard) {
		return (this.cardSuit.equals(otherCard.getCardSuit()) && this.cardValue.equals(otherCard.getCardValue()));
	}

	/**
	 * Getter for the suit of the current Card
	 * 
	 * @return the cardSuit
	 */
	public String getCardSuit() {
		return cardSuit;
	}

	/**
	 * Setter for the cardSuit property
	 * 
	 * @param cardSuit the cardSuit to set as this Card's cardSuit
	 */
	public void setCardSuit(String cardSuit) {
		this.cardSuit = cardSuit;
	}

	/**
	 * Getter for the cardValue property
	 * 
	 * @return the cardValue of the current Card
	 */
	public String getCardValue() {
		return cardValue;
	}

	/**
	 * Setter for the value of the current card
	 * 
	 * @param cardValue the cardValue to set and this Card's cardValue
	 */
	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}

	/**
	 * Getter for the isFaceUp property
	 * 
	 * @return the isFaceUp
	 */
	public Boolean getIsFaceUp() {
		return isFaceUp;
	}

	/**
	 * Setter for the isFaceUp property
	 * 
	 * @param isFaceUp the new value to set isFaceUp to
	 */
	public void setIsFaceUp(Boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}

	/**
	 * Getter for the isRed property
	 * 
	 * @return the current value of isRed
	 */
	public Boolean getIsRed() {
		return isRed;
	}

	/**
	 * Setter for the isRed property
	 * 
	 * @param isRed the isRed to set
	 */
	public void setIsRed(Boolean isRed) {
		this.isRed = isRed;
	}

	/**
	 * Allow the card to be converted to a string
	 */
	@Override
	public String toString() {
		return this.getCardValue() + this.getCardSuit();
	}
}
