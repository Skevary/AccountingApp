package com.vk.native_code.fxml;

import com.vk.native_code.model.Person;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Form change information about the user.
 */
public class PersonEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField departmentField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
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
    public void setPerson(Person person) {
	this.person = person;

	nameField.setText(person.getName());
	departmentField.setText(person.getDepartment());
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
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
	dialogStage.close();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
	if (isInputValid()) {
	    person.setName(nameField.getText());
	    person.setDepartment(departmentField.getText());

	    okClicked = true;
	    dialogStage.close();
	}
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
	String errorMessage = "";

	if (nameField.getText() == null || nameField.getText().length() == 0) {
	    errorMessage += "No valid name!\n";
	}
	if (departmentField.getText() == null || departmentField.getText().length() == 0) {
	    errorMessage += "No valid department name!\n";
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
