package application.controller;

import java.util.Arrays;

import application.model.Scores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HighScoreController {
	
	private Stage stage;
	
	@FXML
	private AnchorPane tablePane;
	
	private ObservableList<Scores> tableData;
	private TableView<Scores> table;
	
	public void setTable(Scores[] high){
		tableData = FXCollections.observableArrayList();
		table = new TableView<Scores>();
		
		int i = 0;
		while(i < high.length && high[i] != null)
		{
			tableData.add(high[i]);
			i++;
		}
		
		TableColumn <Scores, String> nameColumn = new TableColumn<Scores, String>("Name");
		TableColumn <Scores, Integer> scoreColumn = new TableColumn<Scores, Integer>("Score");

		nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
		scoreColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.49));
		
		nameColumn.setCellValueFactory(
                new PropertyValueFactory<Scores, String>("name"));
		scoreColumn.setCellValueFactory(
                new PropertyValueFactory<Scores, Integer>("score"));
        table.getColumns().addAll(Arrays.asList(nameColumn, scoreColumn));

		table.setItems(tableData);
		
		AnchorPane.setTopAnchor(table, 0.0);
	    AnchorPane.setRightAnchor(table, 0.0);
	    AnchorPane.setBottomAnchor(table, 0.0);
	    AnchorPane.setLeftAnchor(table, 0.0);
		tablePane.getChildren().addAll(table);	
	}
	
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}
	
	@FXML
	public void close()
	{
		stage.close();
	}
}