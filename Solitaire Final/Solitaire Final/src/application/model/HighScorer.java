package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HighScorer {
	
	private final SimpleStringProperty name;
	private final SimpleIntegerProperty score;
	
	public HighScorer(String name, int score) {
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
		
	//getter for score
	public void setScore(int score){
		this.score.set(score);
		}
}
