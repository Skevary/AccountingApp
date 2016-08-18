package com.vk.native_code.fxml;

import java.time.LocalDate;

import com.vk.native_code.Main;
import com.vk.native_code.model.Person;
import com.vk.native_code.model.PersonDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> nameColumn;
	@FXML
	private TableColumn<Person, String> departmentColumn;

	@FXML
	private TableView<PersonDetails> itemTable;
	@FXML
	private TableColumn<PersonDetails, String> itemNameColumn;
	@FXML
	private TableColumn<PersonDetails, String> departmentColumnTwo;
	@FXML
	private TableColumn<PersonDetails, Integer> countColumn;
	@FXML
	private TableColumn<PersonDetails, Boolean> stateColumn;
	@FXML
	private TableColumn<PersonDetails, LocalDate> dataColumn;
	@FXML
	private TableColumn<PersonDetails, Boolean> chekedColumn;

	// Reference to the main application.
	private Main main;

	/**
	 * Fills all text fields to show details about the person. If the specified
	 * person is null, all text fields are cleared.
	 * 
	 * @param person
	 *            the person or null
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			itemTable.setItems(person.getDetailsList());
		}
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
		// Initialize the person table with the special information about the
		// user.
		itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
		departmentColumnTwo.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
		countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
		stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
		dataColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
		chekedColumn.setCellValueFactory(cellData -> cellData.getValue().chekedProperty());
		// Clear person details.
		showPersonDetails(null);
		// Listen for selection changes and show the person details when
		// changed.
		personTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person();
		boolean okClicked = main.showPersonEditDialog(tempPerson);
		if (okClicked) {
			main.getPersonData().add(tempPerson);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = main.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				showPersonDetails(selectedPerson);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * special details for a new person.
	 */
	@FXML
	private void handleNewPersonDetails() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {

			PersonDetails personDetails = new PersonDetails(
					personTable.getSelectionModel().getSelectedItem().getDepartment());
			boolean okClicked = main.showPersonEditDialogDetails(personDetails);
			if (okClicked) {
				personTable.getSelectionModel().getSelectedItem().getDetailsList().add(personDetails);
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * special details for the selected person.
	 */
	@FXML
	private void handleEditPersonDetails() {
		PersonDetails selectedPerson = itemTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = main.showPersonEditDialogDetails(selectedPerson);
			if (okClicked) {
				showPersonDetails(personTable.getSelectionModel().getSelectedItem());
			}

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePersonDetails() {
		int selectedIndex = itemTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			itemTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param main
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

	public void setMain(Main main) {
		this.main = main;

		personTable.setItems(main.getPersonData());
	}

}
