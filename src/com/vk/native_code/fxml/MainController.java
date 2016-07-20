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

	// Ссылка на главное приложение.
	private Main main;

	/**
	 * Заполняет все колонки, отображая подробности о пользователе.
	 * 
	 * @param person
	 *            — адресат типа Person или null
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			itemTable.setItems(person.getDetailsList());
		}
	}

	/**
	 * Инициализация класса-контроллера. Этот метод вызывается автоматически
	 * после того, как fxml-файл будет загружен.
	 */
	@FXML
	private void initialize() {
		// Инициализация таблицы пользователя с двумя столбцами.
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
		// Инициализация таблицы пользователя с дополнительной информацией.
		itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
		departmentColumnTwo.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
		countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
		stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
		dataColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
		chekedColumn.setCellValueFactory(cellData -> cellData.getValue().chekedProperty());
		// Очистка дополнительной информации о пользователе. !
		showPersonDetails(null);
		// Слушаем изменения выбора, и при изменении отображаем
		// дополнительную информацию о пользователе.
		personTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

	}

	/**
	 * Вызывается, когда пользователь кликает по кнопке New Открывает диалоговое
	 * окно с дополнительной информацией нового пользователя.
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
	 * Вызывается, когда пользователь кликает по кнопка Edit Открывает
	 * диалоговое окно для изменения выбранного пользователя.
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
			// Ничего не выбрано.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Вызывается, когда пользователь кликает по кнопке Delete.
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		} else {
			// Ничего не выбрано.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Вызывается, когда пользователь кликает по кнопке New Открывает диалоговое
	 * окно с дополнительной информацией поля пользователя.
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
			// Ничего не выбрано.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Вызывается, когда пользователь кликает по кнопка Edit Открывает
	 * диалоговое окно для изменения выбранного поля дополнительной информации.
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
	 * Вызывается, когда пользователь кликает по кнопке Delete.
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
	 * Вызывается главным приложением, которое даёт на себя ссылку.
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
