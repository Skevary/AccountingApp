package com.vk.native_code.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.vk.native_code.util.LocalDateAdapter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a additional data to the Person.
 *
 */
public class PersonDetails {
	private final StringProperty itemName;
	private final StringProperty department;
	private final IntegerProperty count;
	private final BooleanProperty state;
	private final BooleanProperty cheked;
	private final ObjectProperty<LocalDate> data;

	/**
	 * Default constructor.
	 */
	public PersonDetails() {
		this("Empty Name", "Empty Department", 0, true, false);
	}

	/**
	 * Constructor with some initial data.
	 */
	public PersonDetails(String department) {
		this("Empty Name", department, 0, true, false);
	}

	/**
	 * Constructor with full initialization.
	 * 
	 * @param itemName
	 * @param department
	 * @param count
	 * @param state
	 * @param data
	 * @param cheked
	 */
	public PersonDetails(String itemName, String department, int count, Boolean state, Boolean cheked) {
		this.itemName = new SimpleStringProperty(itemName);
		this.department = new SimpleStringProperty(department);
		this.count = new SimpleIntegerProperty(count);
		this.state = new SimpleBooleanProperty(state);
		this.data = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.cheked = new SimpleBooleanProperty(cheked);
	}

	public String getItemName() {
		return itemName.get();
	}

	public void setItemName(String itemName) {
		this.itemName.set(itemName);
	}

	public StringProperty itemNameProperty() {
		return itemName;
	}

	public String getDepartment() {
		return department.get();
	}

	public void setDepartment(String department) {
		this.department.set(department);
	}

	public StringProperty departmentProperty() {
		return department;
	}

	public int getCount() {
		return count.get();
	}

	public void setCount(int count) {
		this.count.set(count);
	}

	public IntegerProperty countProperty() {
		return count;
	}

	public Boolean getState() {
		return state.get();
	}

	public void setState(Boolean state) {
		this.state.set(state);
	}

	public BooleanProperty stateProperty() {
		return state;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getData() {
		return data.get();
	}

	public void setData(LocalDate data) {
		this.data.set(data);
	}

	public ObjectProperty<LocalDate> dataProperty() {
		return data;
	}

	public Boolean getCheked() {
		return cheked.get();
	}

	public void setCheked(Boolean cheked) {
		this.cheked.set(cheked);
	}

	public BooleanProperty chekedProperty() {
		return cheked;
	}
}
