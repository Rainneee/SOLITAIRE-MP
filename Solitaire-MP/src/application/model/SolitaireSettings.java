package application.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class contains the settings for the entire application. Most settings
 * are held in Properties, to allow easy binding of controls to values. In some
 * cases, this is not feasible, such as with the selectedCardBack. In this case,
 * a map is used to map strings to Files, and the currently selected one is
 * easily referenced by controller classes for the drawing of card backs
 * 
 * The class also enables saving settings to the disk, by default to a file
 * called settings.txt. An alternate constructor is available to specify the
 * settings file at construction time
 */
public class SolitaireSettings {

	private DoubleProperty masterVolume = new SimpleDoubleProperty();
	private DoubleProperty musicVolume = new SimpleDoubleProperty();
	private DoubleProperty effectsVolume = new SimpleDoubleProperty();
	private BooleanProperty undoable = new SimpleBooleanProperty();
	private BooleanProperty showTimes = new SimpleBooleanProperty();
	private BooleanProperty solvable = new SimpleBooleanProperty();
	private IntegerProperty selectedBackIndex = new SimpleIntegerProperty();
	private IntegerProperty drawType = new SimpleIntegerProperty();
	private File selectedCardBack;
	private File cardBackLocation = new File("COMPRESSED CARDS/Back");
	private Map<String, File> cardBacks;
	private File settingsFile;

	/**
	 * Constructor with no arguments. Default settings file is "settings.txt". This
	 * loads up every file in the cardBackLocation folder and puts in in a map.
	 * After that, it loads up every setting from the current settingsFile if it
	 * exists. If it does not, it sets all settings to the default, as defined in
	 * the setDefaults function.
	 */
	public SolitaireSettings() {

		this.settingsFile = new File("settings.txt");
		// Read all possible card backs from disk and load them into the the cardBacks
		// Map
		File[] fileList = cardBackLocation.listFiles();
		cardBacks = new HashMap<String, File>();
		for (File cardBack : fileList) {
			cardBacks.put(cardBack.getName().substring(0, cardBack.getName().lastIndexOf('.')), cardBack);
		}

		// Try to load settings or set all to default
		if (this.settingsFile.exists()) {
			try {
				this.loadSettings();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			this.setDefaults();
		}

	}

	/*
	 * Constructor that saves settings to a non-default settings file. The
	 * settingsFile is set from the argument, then it loads up every file in the
	 * cardBackLocation folder and puts in in a map. After that, it loads up every
	 * setting from the current settingsFile if it exists. If it does not, it sets
	 * all settings to the default, as defined in the setDefaults function.
	 * 
	 * @param settingsFile A File to use as the save location for settings
	 */
	public SolitaireSettings(File settingsFile) {

		this.settingsFile = settingsFile;
		// Read all possible card backs from disk and load them into the the cardBacks
		// Map
		File[] fileList = cardBackLocation.listFiles();
		cardBacks = new HashMap<String, File>();
		for (File cardBack : fileList) {
			cardBacks.put(cardBack.getName().substring(0, cardBack.getName().lastIndexOf('.')), cardBack);
		}

		// Try to load settings or set all to default
		if (this.settingsFile.exists()) {
			try {
				this.loadSettings();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.setDefaults();
		}
	}

	/**
	 * Set defaults to all settings, as specified:
	 * 
	 * 
	 * masterVolume: 50
	 *
	 * musicVolume: 50
	 * 
	 * effectsVolume: 50
	 * 
	 * undoable: false
	 * 
	 * solvable: false
	 * 
	 * showTimes: false
	 * 
	 * selectedCardBack: the first returned by calling cardBacks.keySet()
	 * 
	 * selectedBackIndex: 0
	 * 
	 * drawType: 0
	 * 
	 */
	private void setDefaults() {
		setMasterVolume(50.0);
		setMusicVolume(50.0);
		setEffectsVolume(50.0);
		setUndoable(false);
		setSolvable(false);
		setShowTimes(false);
		// Make sure cardBacks isn't empty before trying to set one as the
		// selectedCardBack
		if (!cardBacks.isEmpty()) {
			for (String key : cardBacks.keySet()) {
				setSelectedCardBack(key);
				break;
			}
		}
		setSelectedBackIndex(0);
		setDrawType(0);

	}

	/**
	 * Getter for the DoubleProperty masterVolume
	 * 
	 * @return the current masterVolume as a DoubleProperty
	 */
	public DoubleProperty masterVolumeProperty() {
		return masterVolume;
	}

	/**
	 * Getter for the DoubleProperty musicVolume
	 * 
	 * @return the current musicVolume as a DoubleProperty
	 */
	public DoubleProperty musicVolumeProperty() {
		return musicVolume;
	}

	/**
	 * Getter for the DoubleProperty effectsVolume
	 * 
	 * @return the current effectsVolume as a DoubleProperty
	 */
	public DoubleProperty effectsVolumeProperty() {
		return effectsVolume;
	}

	/**
	 * 
	 * Getter for the BooleanProperty undoable
	 * 
	 * @return the current undoable as a BooleanProperty
	 */
	public BooleanProperty undoableProperty() {
		return undoable;
	}

	/**
	 * 
	 * Getter for the BooleanProperty showTimes
	 * 
	 * @return the current showTimes as a BooleanProperty
	 */
	public BooleanProperty showTimesProperty() {
		return showTimes;
	}

	/**
	 * 
	 * Getter for the BooleanProperty solvable
	 * 
	 * @return the current solvable as a BooleanProperty
	 */
	public BooleanProperty solvableProperty() {
		return solvable;
	}

	/**
	 * Getter for the masterVolume
	 * 
	 * @return The current value of masterVolume
	 */
	public Double getMasterVolume() {
		return masterVolume.getValue();
	}

	/**
	 * Setter for the masterVolume
	 * 
	 * @param masterVolume The new value to set the masterVolume to
	 */
	public void setMasterVolume(Double masterVolume) {
		this.masterVolume.setValue(masterVolume);
	}

	/**
	 * Getter for the musicVolume
	 * 
	 * @return the current value of musicVolume
	 */
	public Double getMusicVolume() {
		return musicVolume.getValue();
	}

	/**
	 * Setter for the masterVolume
	 * 
	 * @param musicVolume The new value to set the musicVolume to
	 */
	public void setMusicVolume(Double musicVolume) {
		this.musicVolume.setValue(musicVolume);
	}

	/**
	 * Getter for the effectsVolume
	 * 
	 * @return the current value of effectsVolume
	 */
	public Double getEffectsVolume() {
		return effectsVolume.getValue();
	}

	/**
	 * Setter for the masterVolume
	 * 
	 * @param effectsVolume The new value to set the effectVolume to
	 */
	public void setEffectsVolume(Double effectsVolume) {
		this.effectsVolume.setValue(effectsVolume);
	}

	/**
	 * Getter for undoable
	 * 
	 * @return the current value of the undoable property
	 */
	public Boolean getUndoable() {
		return undoable.getValue();
	}

	/**
	 * Setter for the undoable property
	 * 
	 * @param undoable the new value to set undoable to
	 */
	public void setUndoable(Boolean undoable) {
		this.undoable.setValue(undoable);
	}

	/**
	 * Getter for showTimes
	 * 
	 * @return the current value of the showTimes property
	 */
	public Boolean getShowTimes() {
		return showTimes.getValue();
	}

	/**
	 * Setter for the showTimes property
	 * 
	 * @param showTimes the new value to set showTimes to
	 */
	public void setShowTimes(Boolean showTimes) {
		this.showTimes.setValue(showTimes);
	}

	/**
	 * Getter for solvable
	 * 
	 * @return the current value of the solvable property
	 */
	public Boolean getSolvable() {
		return solvable.getValue();
	}

	/**
	 * Setter for the solvable property
	 * 
	 * @param solvable The new value to set solvable to
	 */
	public void setSolvable(Boolean solveable) {
		this.solvable.setValue(solveable);
	}

	/**
	 * Getter for the selected back index property
	 * 
	 * @return the selectedBackIndex property
	 */
	public IntegerProperty getSelectedBackIndexProperty() {
		return selectedBackIndex;
	}

	/**
	 * Setter for the selectedBackIndex
	 * 
	 * @param selectedBackIndex the new value to set selectedBackIndex to
	 */
	public void setSelectedBackIndex(Integer selectedBackIndex) {
		this.selectedBackIndex.setValue(selectedBackIndex);
	}

	/**
	 * Getter for selectedBackIndex
	 * 
	 * @return The current value of selectedBackIndex
	 */
	public Integer getSelectedBackIndex() {
		return this.selectedBackIndex.getValue();
	}

	/**
	 * Getter for the selectedCardBack
	 * 
	 * @return The current File that is currently the selectedCardBack
	 */
	public File getSelectedCardBack() {
		return selectedCardBack;
	}

	/**
	 * Setter for selectedCardBack, which looks up a file in the cardBacks HashMap
	 * 
	 * @param selectedCard The key used to look up a card, which is then set as the
	 *                     selectedCardBack
	 */
	public void setSelectedCardBack(String selectedCard) {
		// Make sure the selectedCard is actually in the map before trying to load from
		// the Map
		if (cardBacks.containsKey(selectedCard)) {
			this.selectedCardBack = this.cardBacks.get(selectedCard);
		}
	}

	/*
	 * Getter for the IntegerProperty drawType
	 * 
	 * @return the drawType property
	 */
	public IntegerProperty getDrawTypeProperty() {
		return drawType;
	}

	/**
	 * Setter for drawType
	 * 
	 * @param drawType The new value to set drawType to
	 */
	public void setDrawType(Integer drawType) {
		this.drawType.setValue(drawType);
	}

	/**
	 * Getter for drawType
	 * 
	 * @return the current value of drawType
	 */
	public Integer getDrawType() {
		return this.drawType.getValue();
	}

	/**
	 * Getter for the Map cardBacks
	 * 
	 * @return the Map that contains the cardBacks
	 */
	public Map<String, File> getCardBacks() {
		return cardBacks;
	}

	/**
	 * Getter for the settings file
	 * 
	 * @return the settingsFile
	 */
	public File getSettingsFile() {
		return settingsFile;
	}

	/**
	 * Setter for the settingsFile
	 * 
	 * @param settingsFile The new File to use as the settingsFile
	 */
	public void setSettingsFile(File settingsFile) {
		this.settingsFile = settingsFile;
	}

	/**
	 * Saves all settings to a File that is passed in as an argument. Uses a
	 * PrintWriter to write each setting out on its own line
	 * 
	 * @param settingsFile The File to write settings to
	 * @throws IOException
	 */
	public void saveSettings(File settingsFile) throws IOException {
		// Open the settings file
		PrintWriter outFile = new PrintWriter(settingsFile);

		// Write out all the settings to a file
		outFile.println(getMasterVolume());
		outFile.println(getMusicVolume());
		outFile.println(getEffectsVolume());
		outFile.println(getUndoable());
		outFile.println(getSolvable());
		outFile.println(getShowTimes());
		outFile.println(getSelectedCardBack());
		outFile.println(getSelectedBackIndex());
		outFile.println(getDrawType());

		outFile.close();
	}

	/**
	 * Load settings from a file that is passed as an argument. If some settings
	 * cannot be read, they are left as defaults
	 * 
	 * @param settingsFile The file to attempt reading settings from
	 * @throws IOException
	 */
	public void loadSettings(File settingsFile) throws IOException {
		// Set all settings to default. If they are not able to be read from a file,
		// they will stay as defaults
		this.setDefaults();
		Scanner inScan = new Scanner(settingsFile);
		if (inScan.hasNextDouble())
			setMasterVolume(inScan.nextDouble());
		if (inScan.hasNextDouble())
			setMusicVolume(inScan.nextDouble());
		if (inScan.hasNextDouble())
			setEffectsVolume(inScan.nextDouble());
		if (inScan.hasNextBoolean())
			setUndoable(inScan.nextBoolean());
		if (inScan.hasNextBoolean())
			setSolvable(inScan.nextBoolean());
		if (inScan.hasNextBoolean())
			setShowTimes(inScan.nextBoolean());
		if (inScan.hasNextLine())
			setSelectedCardBack(inScan.nextLine());
		if (inScan.hasNextInt())
			setSelectedBackIndex(inScan.nextInt());
		if (inScan.hasNextInt())
			setDrawType(inScan.nextInt());

		inScan.close();
	}

	/**
	 * Save all settings to the default settingsFile. Calls the saveSettings
	 * function with this.settingsFile as an argument
	 * 
	 * @throws IOException
	 */
	public void saveSettings() throws IOException {
		this.saveSettings(this.settingsFile);
	}

	/**
	 * Load all settings from the default settings file. Calls the loadSettings
	 * function with this.settingsFile as an argument
	 * 
	 * @throws IOException
	 */
	public void loadSettings() throws IOException {
		this.loadSettings(this.settingsFile);
	}

}
