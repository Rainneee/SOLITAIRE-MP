package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Card;
import application.model.Game;
import application.model.SolitaireSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * 
 * The gameController is where all the code for the actual game is implemented.
 * This varies from picking up cards and dropping them to redrawing the deck.
 *
 */
public class GameController extends SuperController implements Initializable {

	private List<Double> xLayout;
	private List<Double> yLayout;
	private Double cardHeight;
	private Double cardWidth;
	private List<Integer> drawIndices;
	private List<Integer> foundationIndices;
	private SolitaireSettings appSettingsObject;
	private Game currentGame;
	private Deque<Card> srcStack;
	private Card cardToMove;
	private Integer cardYOffset;
	private Integer xMargin;
	private Integer yMargin;
	private Integer i = 0;
	
	public int Score = 0;
	public String pickCardLocation = new String(), dropCardLocation = new String();

	@FXML
	private Pane gamePane;
	@FXML
	private Canvas gameCanvas;
	@FXML
	private Button DrawCardButton;

	/**
	 * Looks up where the user releases the mouse and gets the stack that is under
	 * the mouse. If the mouse is over a valid stack, then the controller tells the
	 * Game object acting as the model to try to move the card from the stored
	 * source stack to the new destination stack
	 * 
	 * @param event
	 */

	@FXML
	void dropCard(MouseEvent event) {
		
		// reset the destStack
		Deque<Card> destStack = null;

		// Handle the user releasing in the top row
		if (event.getY() < this.yLayout.get(1)) {
			
			// The only place you can put things in the top row are the foundations
			if (event.getX() > this.xLayout.get(foundationIndices.get(0))) {
				
				// Store the X to manipulate it
				Double tempX = event.getX();
				Integer foundationNum;
				
				// Transform the X value for this index to find the relevant foundation number
				tempX = tempX - this.xLayout.get(foundationIndices.get(0));
				tempX = tempX / (cardWidth + 15);
				foundationNum = ((Double) Math.floor(tempX)).intValue();

				// Set the destination stack based on where the user clicked, then move
				destStack = currentGame.getAFoundation(foundationNum);

				// Don't bother trying to move if the source and destStacks are the same
				if (destStack == srcStack) {
					
					return;
				} else {
					boolean sound = currentGame.moveCardToFoundation(cardToMove, srcStack, destStack);
					dropCardLocation = "foundation";
					if(sound == true)
						drawCards();
				}
				

			}
		} else { 
			
			// Handle putting cards in the stack here
			// Store the X to manipulate
			Double tempX = event.getX();

			// Calculate which stack to grab cards from
			tempX = tempX / (cardWidth + 15);
			Integer stackNum = ((Double) Math.floor(tempX)).intValue();
			destStack = currentGame.getAPlayArea(stackNum);

			// Don't bother trying to move cards from the same stack to themselves
			if (destStack == srcStack) {
				
				return;
			} else {
				dropCardLocation = "tableau";
				boolean sound = currentGame.moveCardToStack(cardToMove, srcStack, destStack);
				if(sound == true)
					drawCards();
			}

		}

		// Redraw the game area to show any new changes to game state
		//drawCards();
	}

	/**
	 * allows player to click on a card to move and stores it in the stack to determine
	 * if it's a valid move
	 * 
	 * @param event
	 */

	@FXML
	void pickUpCard(MouseEvent event) {
		
		// Reset the srcStack
		this.srcStack = null;

		// Check if the click was in the top part of the board
		if (event.getY() < this.yLayout.get(1)) {
			
			// If it's within the drawDiscard box, set the srcStack to the discard stack and
			// the card to move as the top of the discard stack
			if (event.getX() > this.xLayout.get(1) && event.getX() < this.xLayout.get(3)) {
				srcStack = this.currentGame.getDrawDiscard();
				cardToMove = srcStack.peek();
				pickCardLocation = "waste";
			} else if (event.getX() > this.xLayout.get(foundationIndices.get(0))) { // If it's in one of the
																					// foundations, do this
				
				// Store the X to manipulate it
				Double tempX = event.getX();
				Integer foundationNum;

				// Transform the X value for this index to find the relevant foundation number.
				// Get the difference between the event's X and the first foundation's X, then
				// find out how many "card spaces" over the click was to find which foundation
				// was clicked
				tempX = tempX - this.xLayout.get(foundationIndices.get(0));
				tempX = tempX / (cardWidth + 15);
				foundationNum = ((Double) Math.floor(tempX)).intValue();

				// Set the source stack based on the foundation number calculated
				srcStack = currentGame.getAFoundation(foundationNum);
				cardToMove = srcStack.peek();
				pickCardLocation = "foundation";
			}
		} else { 
			
			// Handle getting cards from the game board in here
			// Store the X to manipulate
			Double tempX = event.getX();

			// Calculate which stack to grab cards from. This is similar to the math done to
			// figure out the foundation number above
			tempX = tempX / (cardWidth + 15);
			Integer stackNum = ((Double) Math.floor(tempX)).intValue();
			srcStack = currentGame.getAPlayArea(stackNum);

			// Figure out where in the stack the user clicked, to figure out which card they
			// selected
			Double bottomOfStack = ((srcStack.size() - 1) * cardYOffset) + cardWidth;
			Double startOfLastCard = (double) ((srcStack.size() - 1) * cardYOffset);
			Double tempY = event.getY();
			tempY -= this.yLayout.get(1);

			// If the user clicked on the last card on the stack, set it as the cardToMove
			if (tempY > startOfLastCard && tempY < bottomOfStack) {
				cardToMove = srcStack.peek();
			} else if (tempY < startOfLastCard) {
				
				// If the user clicked somewhere above that, then figure where in the
				// stack they clicked
				int i = 0;
				Integer index = ((Double) Math.floor(((startOfLastCard - tempY) / cardYOffset))).intValue() + 1;
				Card currentCard = srcStack.peek();
				
				// Since the stack doesn't support getting a card from a particular index,
				// iterate through until you get to the proper number. Once the card has been
				// found, set it as the card to move
				for (Iterator<Card> stkIt = srcStack.iterator(); stkIt.hasNext() && i <= index;) {
					currentCard = stkIt.next();
					i++;
				}
				cardToMove = currentCard;
			}
			pickCardLocation = "tableau";

		}

	}

	/*
	 * allows player to move a selected card to a desired location
	 * 
	 * @param event
	 */

	@FXML
	void dragCard(MouseEvent event) {
	}

	/**
	 * @param event
	 */
	// draws cards from the pile in the top left of the screen
	@FXML
	void Draw(ActionEvent event) {

		currentGame.drawNextCard();
		drawCards();

	}

	/**
	 * @param game
	 */
	// sets the game to whatever is chosen by the player
	public void setGame(Game game) {
		currentGame = game;
	}

	/**
	 * Sets up the needed values to initially draw the game. First, the size for
	 * the gameCanvas is bound to the size of the containing pane, with listeners
	 * added to update card size values whenever they are changed. Then initial
	 * values are specified for card sizes, and arrays to store the X and Y values for
	 * the layout are initialized
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// gamePane.setPrefWidth(900);
		// gamePane.setPrefHeight(650);
		gameCanvas.widthProperty().bind(gamePane.widthProperty());
		gameCanvas.heightProperty().bind(gamePane.heightProperty());
		gameCanvas.widthProperty().addListener(evt -> updateValues());
		gameCanvas.heightProperty().addListener(evt -> updateValues());
		
		// Set variables for the card height and width, with padding
		cardHeight = (double) 90;
		cardWidth = (double) 70;
		cardYOffset = 20;
		xMargin = 15;
		yMargin = 20;
		
		// Set the X and Y values for the start of each card row & column
		xLayout = new ArrayList<Double>();
		for (int i = 0; i < 7; i++) {
			xLayout.add(i * (cardWidth + xMargin));
		}
		yLayout = new ArrayList<Double>();
		yLayout.add((double) 0);
		yLayout.add(cardHeight + yMargin);

		drawIndices = new ArrayList<Integer>();
		drawIndices.add(0);
		drawIndices.add(1);

		foundationIndices = new ArrayList<Integer>();
		foundationIndices.add(3);
		foundationIndices.add(4);
		foundationIndices.add(5);
		foundationIndices.add(6);

	}

	/**
	 * Update all values that relate to card sizes. This allows cards to be sized
	 * proportionally to the game board
	 */

	public void updateValues() {

		cardWidth = gameCanvas.getWidth() / 8;
		cardHeight = cardWidth * 1.4529;
		cardYOffset = (int) (cardHeight / 8);

		for (int i = 0; i < 7; i++) {
			xLayout.set(i, i * (cardWidth + xMargin));
		}
		yLayout.set(0, (double) 0);
		yLayout.set(1, cardHeight + yMargin);

		drawCards();

	}

	/**
	 * Clears the whole board, then calls functions to draw the current state of the
	 * game on the canvas. It then checks to see if the card stack is empty and if
	 * it is then the current game is OVER
	 */

	public void drawCards() {
		// Clear the entire canvas, so we don't get any duplicate cards
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
		if (this.currentGame != null) {
			
			// Draw the whole table for the game
			drawTop(this.currentGame);
			drawStacks(this.currentGame);
			if (this.currentGame.checkGameComplete()) {
				Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION, "You have won the game!");
				gameOverAlert.show();
			}
		}
		/*if(i ==0) {
			String musicFile = "cardShuffle.mp3"; // Plays Roberts play list
			
			// Play music
			Media sound = new Media(new File(musicFile).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.volumeProperty().bind(this.appSettingsObject.effectsVolumeProperty()
					.multiply(this.appSettingsObject.masterVolumeProperty()).divide(10000));
			mediaPlayer.play();
			i++;
		} else if( i > 0) {
		String musicFile = "cardSound.mp3"; // Plays Robert's play list
		
		// Play music
		Media sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.volumeProperty().bind(this.appSettingsObject.effectsVolumeProperty()
				.multiply(this.appSettingsObject.masterVolumeProperty()).divide(10000));
		mediaPlayer.play();
		}*/
		updateScore();
		System.out.println(Score);
		pickCardLocation = null;
		dropCardLocation = null;
		currentGame.didrecycle = false;
	}

	/**
	 * Draws the draw and draw discard piles on the screen. Normally called by
	 * drawCards, passing the currentGame that belongs to the class.
	 * 
	 * @param runningGame (The game to draw on the screen)
	 */

	public void drawTop(Game runningGame) {
		GraphicsContext currentGC = gameCanvas.getGraphicsContext2D();

		// Get the drawStack from the current game, along with the current X and Y offsets
		Deque<Card> drawStack = runningGame.getDraw();
		Double currentX = xLayout.get(0);
		Double currentY = yLayout.get(0);

		// Either draw an empty rectangle or a card back. depending on whether the draw
		// stack is empty
		if (drawStack.isEmpty()) {
			currentGC.strokeRoundRect(currentX, currentY, cardWidth, cardHeight, 10, 10);
		} else {
			currentGC.drawImage(new Image(appSettingsObject.getSelectedCardBack().toURI().toString()), currentX,
					currentY, cardWidth, cardHeight);
		}

		// Get the drawDiscard stack and the next X slot to draw the discard stack
		Deque<Card> drawDiscard = runningGame.getDrawDiscard();
		currentX = xLayout.get(1);

		// If the discard is empty, draw an empty rectangle. If not, draw the face of
		// the top card
		if (drawDiscard.isEmpty()) {
			currentGC.strokeRoundRect(currentX, currentY, cardWidth, cardHeight, 10, 10);
		} else {
			Card topCard = drawDiscard.peek();
			currentGC.drawImage(new Image(topCard.getFaceLocation().toURI().toString()), currentX, currentY, cardWidth,
					cardHeight);
		}

	}

	/**
	 * Draws all the stacks besides the draw and drawDiscard stacks. Usually this is
	 * called from drawCards, which passes the currentGame belonging to the class as
	 * an argument
	 * 
	 * @param runningGame (The game to draw on the screen)
	 */
	public void drawStacks(Game runningGame) {
		GraphicsContext currentGC = gameCanvas.getGraphicsContext2D();
		Double currentX;
		Double currentY;
		
		// Put the foundation stack and populate accordingly
		for (int i = 0; i < 4; i++) {
			
			// Get the current foundation stack and set the X and Y coordinates for it
			Deque<Card> FoundationStack = runningGame.getAFoundation(i);
			currentX = xLayout.get(foundationIndices.get(i));
			currentY = yLayout.get(0);
			
			// If the current foundation in the model isn't empty, draw the top card
			if (!FoundationStack.isEmpty()) {
				Card topCard = FoundationStack.peek();
				if (topCard.getIsFaceUp()) {
					currentGC.drawImage(new Image(topCard.getFaceLocation().toURI().toString()), currentX, currentY,
							cardWidth, cardHeight);
				}
			} else {
				
				// If the current foundation stack is empty, then draw a rectangle
				currentGC.strokeRoundRect(currentX, currentY, cardWidth, cardHeight, 10, 10);
			}
		}
		// Populate stacks on the table
		for (int i = 0; i < 7; i++) {
			
			// Get the current stack number in the play area, along with the current X and Y
			// coordinates for it
			Deque<Card> currentStack = runningGame.getAPlayArea(i);
			currentX = xLayout.get(i);
			currentY = yLayout.get(1);

			// Offset that handles cascading the Cards' Y position
			int offset = 0;
			
			// Since the stack's top cards are the ones on top of the stack, draw the cards
			// in reverse order
			for (Iterator<Card> it = currentStack.descendingIterator(); it.hasNext();) {
				
				// Get the next card in the stack
				Card someCard = it.next();

				// Draw either the face of the card or the back, depending on whether that
				// particular card is face up. Each card is drawn at the start Y plus some
				// offset, which is specified stored in the class itself. This way the first
				// card gets drawn, then the second gets drawn some distance below, and so on,
				// until all cards are drawn
				if (someCard.getIsFaceUp()) {
					currentGC.drawImage(new Image(someCard.getFaceLocation().toURI().toString()), currentX,
							currentY + offset, cardWidth, cardHeight);
				} else {
					currentGC.drawImage(new Image(appSettingsObject.getSelectedCardBack().toURI().toString()), currentX,
							currentY + offset, cardWidth, cardHeight);
				}

				// Increment the offset based on the set Y offset in the class
				offset += cardYOffset;
			}
		}

	}

	/**
	 * @return the appSettingsObject
	 */
	public SolitaireSettings getAppSettingsObject() {
		return appSettingsObject;
	}

	/**
	 * @param appSettingsObject (the appSettingsObject to set)
	 */
	public void setAppSettingsObject(SolitaireSettings appSettingsObject) {
		this.appSettingsObject = appSettingsObject;
	}

	/**
	 * @return (the currentGame)
	 */
	public Game getCurrentGame() {
		return currentGame;
	}

	/**
	 * @param newGame (The new Game to set currentGame to)
	 */
	public void setCurrentGame(Game newGame) {
		this.Score = 0;
		this.currentGame = newGame;
		this.currentGame.getDrawTypeProperty().bind(this.appSettingsObject.getDrawTypeProperty());
	}

	
	public void updateScore() {
		if(currentGame.didrecycle) {
			Score -= 100;
			if(Score < 0) {
				Score = 0;
			}
		}
		System.out.println("Pick: " + pickCardLocation);
		System.out.println("Drop: " + dropCardLocation);
		if(pickCardLocation == null) {
			return;
		}
		
		if(pickCardLocation == "waste") {
			if(dropCardLocation == "tableau") {
				Score += 5;
			}
			else if(dropCardLocation == "foundation") {
				Score += 10;
			}
		}
		else if(pickCardLocation == "tableau") {
			if(dropCardLocation == "foundation") {
				Score += 10;
			}
			else if(dropCardLocation == "tableau") {
				Score += 5;
			}
		}
		else if(pickCardLocation == "foundation") {
			if(dropCardLocation == "tableau") {
				Score -= 15;
			}
		}
	}
}
