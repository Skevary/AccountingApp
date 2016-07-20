package com.vk.native_code.fxml;

import com.vk.native_code.model.Person;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Окно для изменения информации о пользователе.
 * 
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
	 * Инициализирует класс-контроллер. Этот метод вызывается автоматически
	 * после того, как fxml-файл будет загружен.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Устанавливает сцену для этого окна.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Задаёт пользователя, информацию о котором будем менять.
	 * 
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;

		nameField.setText(person.getName());
		departmentField.setText(person.getDepartment());
	}

	/**
	 * Returns true, если пользователь кликнул OK, в другом случае false.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Вызывается, когда пользователь кликнул по кнопке Cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Вызывается, когда пользователь кликнул по кнопке OK.
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
	 * Проверяет пользовательский ввод в текстовых полях.
	 * 
	 * @return true, если пользовательский ввод корректен
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
