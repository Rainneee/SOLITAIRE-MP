package application.controller;

import java.util.Arrays;

import application.Main;
import application.model.HighScorer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class HighScoresController {
	
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	AnchorPane tablePane;
	
	private ObservableList<HighScorer> tableData;
	private TableView<HighScorer> table;
	
	
	
	public void setTable(HighScorer[] topTen){
		tableData = FXCollections.observableArrayList();
		table = new TableView<HighScorer>();
	
		if(topTen[0] == null) {
			topTen[0] = new HighScorer("Will",100);
			topTen[1] = new HighScorer("Rice",95);
			topTen[2] = new HighScorer("Romaine",90);
			topTen[3] = new HighScorer("Biboy",85);
			topTen[4] = new HighScorer("Mikasa",80);
			topTen[5] = new HighScorer("Sheshe",70);
			topTen[6] = new HighScorer("Marlou",75);
			topTen[7] = new HighScorer("Aling Vickie",65);
			topTen[8] = new HighScorer("Mang Kanor",60);
			topTen[9] = new HighScorer("hatdog",50);
		}
		int i = 0;
		while(i < topTen.length && topTen[i] != null)
		{
			tableData.add(topTen[i]);
			i++;
		}
		
		TableColumn <HighScorer, String> nameColumn = new TableColumn<HighScorer, String>("Name");
		TableColumn <HighScorer, Integer> scoreColumn = new TableColumn<HighScorer, Integer>("Score");

		nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
		scoreColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.49));
		
		nameColumn.setCellValueFactory(
                new PropertyValueFactory<HighScorer, String>("name"));
		scoreColumn.setCellValueFactory(
                new PropertyValueFactory<HighScorer, Integer>("score"));
        table.getColumns().addAll(Arrays.asList(nameColumn, scoreColumn));

		table.setItems(tableData);
		
		AnchorPane.setTopAnchor(table, 0.0);
	    AnchorPane.setRightAnchor(table, 0.0);
	    AnchorPane.setBottomAnchor(table, 0.0);
	    AnchorPane.setLeftAnchor(table, 0.0);
		tablePane.getChildren().addAll(table);	
	}
	
	@FXML
	public void backMenu() {
		main.openMainMenu();
	}

}
