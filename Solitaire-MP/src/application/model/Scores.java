package application.model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Scores {

	/*
	 * A class to represent the names and the scores.
	 */
	
	private final SimpleStringProperty name;
	private final SimpleIntegerProperty score;

	public Scores (String name, int score){
		this.name = new SimpleStringProperty(name);
		this.score = new SimpleIntegerProperty(score);
	}

	//getter for name
	public String getName() {
		return name.get();
	}

	//getter for score
	public int getScore() {
		return score.get();
	}
	
	//setter for name
	public void setName(String name){
		this.name.set(name);
	}
	
	//setter for score
	public void setScore(int score){
		this.score.set(score);
	}
}