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
	 * Constructor
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
		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/index.png"));

		initRootMain();
		initMain();
	}

	/**
	 * Initialisere the root layout.
	 */
	private void initRootMain() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/RootMain.fxml"));
			rootLayout = (BorderPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			// Give the controller access to the main app.
			RootMainController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Try to load last opened person file.
		File file = getPersonFilePath();
		if (file != null) {
			loadPersonDataFromFile(file);
		}
	}

	/**
	 * Shows in the root layout of information about person.
	 */
	private void initMain() {
		try {
			// Load information about users.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/Main.fxml"));
			SplitPane main = (SplitPane) loader.load();
			// Publish information about users in the center of root layout.
			rootLayout.setCenter(main);
			// Give the controller access to the main app.
			MainController controller = loader.getController();
			controller.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 * 
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Person person) {
		try {
			// Load the fxml file and create a new stage for the pop-up dialog.
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
			// Set the person into the controller.
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			// Show the dialog and wait until the user closes it.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens a dialog to edit details for the specified fields person. If the
	 * user clicks OK, the changes are saved into the provided person object and
	 * true is returned.
	 * 
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialogDetails(PersonDetails personDetails) {
		try {
			// Load the fxml file and create a new stage for the pop-up dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/PersonEditDialogDetails.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person Details");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setResizable(false);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			// Set the person into the controller.
			PersonEditDialogDetailsController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(personDetails);
			// Show the dialog and wait until the user closes it.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens a dialog to show person statistics.
	 */
	public void showStatistics() {
		try {
			// Load the fxml file and create a new stage for the pop-up.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("fxml/Statistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Statistics data");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the persons into the controller.
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
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
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
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file
	 *            the file or null to remove the path
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
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			// Reading XML from the file and unmarshalling.
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
			personData.clear();
			personData.addAll(wrapper.getPersons());
			// Save the file path to the registry.
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
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void savePersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			// Wrapping our person data.
			PersonListWrapper wrapper = new PersonListWrapper();
			wrapper.setPersons(personData);
			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);
			// Save the file path to the registry.
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
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Returns the data as an observable list of Persons.
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
