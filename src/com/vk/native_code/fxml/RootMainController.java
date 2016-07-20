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
	 * Создаёт пустую программу без таблиц.
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
	 * Открывает FileChooser, чтобы пользователь имел возможность выбрать
	 * адресную книгу для загрузки.
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		// Показываем диалог загрузки файла
		File file = fileChooser.showOpenDialog(main.getPrimaryStage());

		if (file != null) {
			main.loadPersonDataFromFile(file);
		}
	}

	/**
	 * Сохраняет файл в файл адресатов, который в настоящее время открыт. Если
	 * файл не открыт, то отображается диалог "save as".
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
	 * Открывает FileChooser, чтобы пользователь имел возможность выбрать файл,
	 * куда будут сохранены данные
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		// Задаём фильтр расширений
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		// Показываем диалог сохранения файла
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
	 * Открывает диалоговое окно about.
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Automation Application");
		alert.setHeaderText("About");
		alert.setContentText(
				"Author: Native_code\\feedback: new.vk.com/native_code");
		alert.showAndWait();
	}

	@FXML
	private void handleShowStatistics() {
		main.showStatistics();
	}

	/**
	 * Закрывает приложение.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

}
