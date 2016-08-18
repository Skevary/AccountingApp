package com.vk.native_code.fxml;

import com.vk.native_code.model.PersonDetails;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Form change additional information about the user.
 */
public class PersonEditDialogDetailsController {
	ObservableList<Boolean> itemStateList = FXCollections.observableArrayList(true, false);

	@FXML
	private TextField itemNameField;
	@FXML
	private TextField itemCountField;
	@FXML
	private ChoiceBox<Boolean> itemStateBox;

	private Stage dialogStage;
	private PersonDetails personDetails;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		itemStateBox.setValue(true);
		itemStateBox.setItems(itemStateList);
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person
	 */
	public void setPerson(PersonDetails personDetails) {
		this.personDetails = personDetails;

		itemNameField.setText(personDetails.getItemName());
		itemCountField.setText(Integer.toString(personDetails.getCount()));
		itemStateBox.setValue(personDetails.getState());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			personDetails.setItemName(itemNameField.getText());
			personDetails.setCount(Integer.parseInt(itemCountField.getText()));
			personDetails.setState(itemStateBox.getValue());

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (itemNameField.getText() == null || itemNameField.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}

		if (itemCountField.getText() == null || itemCountField.getText().length() == 0) {
			errorMessage += "No valid postal code!\n";
		} else {
			try {
				Integer.parseInt(itemCountField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid postal code (must be an integer)!\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}
