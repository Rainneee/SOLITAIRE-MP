package application.controller;

import javafx.fxml.FXML;
import java.io.IOException;
import application.model.SolitaireSettings;
import javafx.event.ActionEvent;

import javafx.scene.control.Slider;

import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * Controller for the Settings.fxml view which supports manipulating our settings.
 */
public class SettingsController extends SuperController {

	private SolitaireSettings appSettingsObject;
	private Double oldMasterVolume;
	private Double oldMusicVolume;
	private Double oldEffectsVolume;
	private Boolean oldUndo;
	private Boolean oldSolveable;
	private Boolean oldShowTime;
	private Integer oldDrawType;
	private Integer oldCardBackIndex;

	@FXML
	private ComboBox<String> cardBackSelector;
	@FXML
	private ComboBox<String> drawTypeSelector;
	@FXML
	private Slider masterVolumeSlider;
	@FXML
	private Slider musicVolumeSlider;
	@FXML
	private Slider effectsVolumeSlider;
	@FXML
	private CheckBox undoSelection;
	@FXML
	private CheckBox solveableSelection;
	@FXML
	private CheckBox showTimeSelection;
	@FXML
	private ImageView cardBackPreview;
	@FXML
	private Button cancelButton;
	@FXML
	private Button okButton;
	@FXML
	private Button applySettingsButton;

	/**
	 * @return the appSettingsObject
	 */
	public SolitaireSettings getAppSettingsObject() {
		return appSettingsObject;
	}

	/**
	 * Setter for the appSettingsObject. This does not just set the
	 * appSettingsObject for this controller, but also creates bindings between the
	 * controls and the settings in the model. This allows updating of the values
	 * with minimal effort
	 * 
	 * @param appSettingsObject the reference to a SolitaireSettings object to use
	 *                          as this objects appSettingsObject
	 */
	public void setAppSettingsObject(SolitaireSettings appSettingsObject) {
		// Set the settings object reference in the class
		this.appSettingsObject = appSettingsObject;

		// Update bindings on all controls
		/*this.masterVolumeSlider.valueProperty().bindBidirectional(this.appSettingsObject.masterVolumeProperty());
		this.musicVolumeSlider.valueProperty().bindBidirectional(this.appSettingsObject.musicVolumeProperty());
		this.effectsVolumeSlider.valueProperty().bindBidirectional(this.appSettingsObject.effectsVolumeProperty());
		this.undoSelection.selectedProperty().bindBidirectional(this.appSettingsObject.undoableProperty());
		this.solveableSelection.selectedProperty().bindBidirectional(this.appSettingsObject.solvableProperty());
		this.showTimeSelection.selectedProperty().bindBidirectional(this.appSettingsObject.showTimesProperty());*/

		// Get all card back options from the settings object, then update the list, and
		// select the previously chosen selection
		cardBackSelector.getItems().addAll(appSettingsObject.getCardBacks().keySet());
		cardBackSelector.getSelectionModel().select(appSettingsObject.getSelectedBackIndex());
		appSettingsObject.getSelectedBackIndexProperty()
				.bind(cardBackSelector.getSelectionModel().selectedIndexProperty());
		appSettingsObject.setSelectedCardBack(cardBackSelector.getSelectionModel().getSelectedItem());
		updateCardBack(new ActionEvent());

		// Add the available draw types, then bind the selected one to the draw type
		// integer in the settings object
		drawTypeSelector.getItems().add("1-Card draw");
		drawTypeSelector.getItems().add("3-Card draw");
		drawTypeSelector.getSelectionModel().select(appSettingsObject.getDrawType());
		appSettingsObject.getDrawTypeProperty().bind(drawTypeSelector.getSelectionModel().selectedIndexProperty());
	}

	@FXML
	void applySettingsAndExit(ActionEvent event) {
		// All values are already updated in the settings object because of the
		// bindings. The settings simply have to be saved to the disk
		try {
			this.appSettingsObject.saveSettings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Restore the old center pane to the main pane
		((GameController) this.appControllerMap.get("gameController")).drawCards();
		((MainController) this.appControllerMap.get("mainController")).restoreCenterPane();
	}

	@FXML
	void applySettings(ActionEvent event) {
		// Update the saved values for the settings to be the current value. Since the
		// values are bound to the settings object, they are already set upon changes.
		// This just keeps them from being overwritten on the cancel button
		this.saveOldSettings();
		try {
			this.appSettingsObject.saveSettings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void cancelSettings(ActionEvent event) {
		// Restore all settings values to the stored old values.
		// If apply has been pressed, this should set them to the current values
		/*this.masterVolumeSlider.setValue(this.oldMasterVolume);
		this.musicVolumeSlider.setValue(this.oldMusicVolume);
		this.effectsVolumeSlider.setValue(this.oldEffectsVolume);
		this.undoSelection.setSelected(this.oldUndo);
		this.solveableSelection.setSelected(this.oldSolveable);
		this.showTimeSelection.setSelected(this.oldShowTime);*/

		this.cardBackSelector.getSelectionModel().select(this.oldCardBackIndex);
		updateCardBack(event);

		this.drawTypeSelector.getSelectionModel().select(this.oldDrawType);

		// Restore the old center pane to the main pane
		((GameController) this.appControllerMap.get("gameController")).drawCards();
		((MainController) this.appControllerMap.get("mainController")).restoreCenterPane();
	}

	// Event Listener on ComboBox[#cardBackSelector].onAction
	@FXML
	public void updateCardBack(ActionEvent event) {
		appSettingsObject.setSelectedCardBack(cardBackSelector.getSelectionModel().getSelectedItem());
		cardBackPreview.setImage(new Image(appSettingsObject.getSelectedCardBack().toURI().toString()));
	}

	// Event Listener on ComboBox[#drawTypeSelector].onAction
	@FXML
	public void updateDrawType(ActionEvent event) {
	}

	// Event Listener on Slider[#masterVolumeSlider].onDragDetected
	/*@FXML
	public void masterVolumeDragged(MouseEvent event) {
	}

	// Event Listener on Slider[#musicVolumeSlider].onDragDetected
	@FXML
	public void musicVolumeDragged(MouseEvent event) {
	}

	// Event Listener on Slider[#effectsVolumeSlider].onDragDetected
	@FXML
	public void effectsVolumeDragged(MouseEvent event) {
	}*/

	/**
	 * Is called before the settings page is shown to the user. Saves all the old
	 * settings in order to allow the user to revert them if they change their mind.
	 * Since the settings are generally updated via binding, these values have to be
	 * saved if the user wants to revert back to the old values
	 */
	public void saveOldSettings() {
		try {
			this.appSettingsObject.saveSettings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*this.oldMasterVolume = this.masterVolumeSlider.getValue();
		this.oldMusicVolume = this.musicVolumeSlider.getValue();
		this.oldEffectsVolume = this.effectsVolumeSlider.getValue();
		this.oldUndo = this.undoSelection.isSelected();
		this.oldSolveable = this.solveableSelection.isSelected();
		this.oldShowTime = this.showTimeSelection.isSelected();*/
		this.oldCardBackIndex = this.cardBackSelector.getSelectionModel().getSelectedIndex();
		this.oldDrawType = this.drawTypeSelector.getSelectionModel().getSelectedIndex();
	}

}