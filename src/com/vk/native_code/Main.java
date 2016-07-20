package com.vk.native_code;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.vk.native_code.fxml.MainController;
import com.vk.native_code.fxml.PersonEditDialogController;
import com.vk.native_code.fxml.PersonEditDialogDetailsController;
import com.vk.native_code.fxml.RootMainController;
import com.vk.native_code.fxml.StatisticsController;
import com.vk.native_code.model.Person;
import com.vk.native_code.model.PersonDetails;
import com.vk.native_code.model.PersonListWrapper;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Person> personData = FXCollections.observableArrayList();

	/**
	 * Конструктор
	 */
	public Main() {

		personData.add(new Person("Name Name", "Colombia"));
		personData.add(new Person("Name Name", "France"));
		personData.add(new Person("Name Name", "Honduras"));
		personData.add(new Person("Name Name", "Peru"));
		for (int i = 0; i < personData.size(); i++) {
			personData.get(i).addDetailsList("Name one", 4, true, false);
			personData.get(i).addDetailsList("Name two", 2, true, false);
			personData.get(i).addDetailsList("Name three", 42, true, false);
			personData.get(i).addDetailsList("Name four", 0, false, false);
			personData.get(i).addDetailsList("Name five", 2, true, false);
		}
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Automation Application");
		// Устанавливаем иконку приложения.
		this.primaryStage.getIcons().add(new Image("file:resources/images/index.png"));

		initRootMain();
		initMain();
	}

	/**
	 * Инициализирует корневой макет.
	 */
	private void initRootMain() {
		try {
			// Загружаем корневой макет из fxml файла.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/RootMain.fxml"));
			rootLayout = (BorderPane) loader.load();
			// Отображаем сцену, содержащую корневой макет
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			// Даём контроллеру доступ к главному прилодению.
			RootMainController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Пытается загрузить последний открытый файл с адресатами.
		File file = getPersonFilePath();
		if (file != null) {
			loadPersonDataFromFile(file);
		}
	}

	/**
	 * Показывает в корневом макете сведения об пользователях.
	 */
	private void initMain() {
		try {
			// Загружаем сведения об пользователях.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/Main.fxml"));
			SplitPane main = (SplitPane) loader.load();
			// Помещаем сведения об пользователях в центр корневого макета.
			rootLayout.setCenter(main);
			// Даём контроллеру доступ к главному приложению.
			MainController controller = loader.getController();
			controller.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Открывает диалоговое окно для изменения деталей указанного пользователя.
	 * Если кликнул OK, то изменения сохраняются в предоставленном объекте
	 * пользователя и возвращается значение true.
	 * 
	 * @param person
	 *            - объект пользователя, который надо изменить
	 * @return true, если пользователь кликнул OK, в противном случае false.
	 */
	public boolean showPersonEditDialog(Person person) {
		try {
			// Загружаем fxml-файл и создаём новую сцену
			// для всплывающего диалогового окна.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setResizable(false);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Передаём пользователя в контроллер.
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			// Отображаем диалоговое окно и ждём, пока пользователь его не
			// закроет
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Открывает диалоговое окно для изменения полей деталей для пользователя.
	 * Если кликнул OK, то изменения сохраняются в предоставленном объекте
	 * пользователя и возвращается значение true.
	 * 
	 * @param person
	 *            - объект пользователя, который надо изменить
	 * @return true, если пользователь кликнул OK, в противном случае false.
	 */
	public boolean showPersonEditDialogDetails(PersonDetails personDetails) {
		try {
			// Загружаем fxml-файл и создаём новую сцену
			// для всплывающего диалогового окна.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/PersonEditDialogDetails.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person Details");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setResizable(false);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Передаём пользователя в контроллер.
			PersonEditDialogDetailsController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(personDetails);
			// Отображаем диалоговое окно и ждём, пока пользователь его не
			// закроет
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Открывает диалоговое окно для вывода статистики .
	 */
	public void showStatistics() {
		try {
			// Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/Statistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Statistics data");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Передаёт адресатов в контроллер.
			StatisticsController controller = loader.getController();
			for (int i = 0; i < personData.size(); i++) {
				controller.setPersonData(personData.get(i).getDetailsList());
			}
			dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Возвращает preference файла пользователя, то есть, последний открытый
	 * файл. Этот preference считывается из реестра, специфичного для конкретной
	 * операционной системы. Если preference не был найден, то возвращается
	 * null.
	 * 
	 * @return
	 */
	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Задаёт путь текущему загруженному файлу. Этот путь сохраняется в реестре,
	 * специфичном для конкретной операционной системы.
	 * 
	 * @param file
	 *            - файл или null, чтобы удалить путь
	 */
	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			primaryStage.setTitle("AutomationApp- " + file.getName());
		} else {
			prefs.remove("filePath");

			primaryStage.setTitle("AutomationApp");
		}
	}

	/**
	 * Загружает информацию о пользователях из указанного файла. Текущая
	 * информация об адресатах будет заменена.
	 * 
	 * @param file
	 */
	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			// Чтение XML из файла и демаршализация.
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
			personData.clear();
			personData.addAll(wrapper.getPersons());
			// Сохраняем путь к файлу в реестре.
			setPersonFilePath(file);

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	/**
	 * Сохраняет текущую информацию об адресатах в указанном файле.
	 * 
	 * @param file
	 */
	public void savePersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			// Обёртываем наши данные о пользователе.
			PersonListWrapper wrapper = new PersonListWrapper();
			wrapper.setPersons(personData);
			// Маршаллируем и сохраняем XML в файл.
			m.marshal(wrapper, file);
			// Сохраняем путь к файлу в реестре.
			setPersonFilePath(file);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	/**
	 * Возвращает главную сцену.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Возвращает данные в виде наблюдаемого списка адресатов.
	 * 
	 * @return
	 */
	public ObservableList<Person> getPersonData() {
		return personData;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
