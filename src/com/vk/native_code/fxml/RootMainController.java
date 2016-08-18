package com.vk.native_code.fxml;

import java.io.File;

import com.vk.native_code.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class RootMainController {
	private Main main;

	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * Creates an empty table without fields.
	 */
	@FXML
	private void handleNew() {
		for (int i = 0; i < main.getPersonData().size(); i++) {
			main.getPersonData().get(i).getDetailsList().clear();
		}
		main.getPersonData().clear();
		main.setPersonFilePath(null);
	}

	/**
	 * Opens a FileChooser to let the user select an address book to load.
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(main.getPrimaryStage());

		if (file != null) {
			main.loadPersonDataFromFile(file);
		}
	}

	/**
	 * Saves the file to the person file that is currently open. If there is no
	 * open file, the "save as" dialog is shown.
	 */
	@FXML
	private void handleSave() {
		File personFile = main.getPersonFilePath();
		if (personFile != null) {
			main.savePersonDataToFile(personFile);
		} else {
			handleSaveAs();
		}
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(main.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			main.savePersonDataToFile(file);
		}
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Automation Application");
		alert.setHeaderText("About");
		alert.setContentText("Author: Native_code\\feedback: new.vk.com/native_code");
		alert.showAndWait();
	}

	@FXML
	private void handleShowStatistics() {
		main.showStatistics();
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

}
