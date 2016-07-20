package com.vk.native_code.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Класс-модель для пользователя (Person).
 *
 */

@XmlType(propOrder = { "name", "department", "detailsList" })
public class Person {

	private final StringProperty name;
	private final StringProperty department;
	private final ObservableList<PersonDetails> detailsList = FXCollections.observableArrayList();

	/**
	 * Конструктор по умолчанию.
	 */
	public Person() {
		this("Empty Name", "Empty Department");
	}

	/**
	 * Конструктор с некоторыми начальными данными.
	 * 
	 * @param name
	 * @param department
	 */
	public Person(String name, String department) {
		this.name = new SimpleStringProperty(name);
		this.department = new SimpleStringProperty(department);
	}

	/**
	 * Метод для добавления детального списка данных для пользователя
	 */
	public void addDetailsList(String itemName, int count, Boolean state, Boolean cheked) {
		detailsList.add(new PersonDetails(itemName, this.getDepartment(), count, state, cheked));
	}

	@XmlElement
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	@XmlElement
	public String getDepartment() {
		return department.get();
	}

	public void setDepartment(String department) {
		this.department.set(department);
	}

	public StringProperty departmentProperty() {
		return department;
	}

	@XmlElementWrapper
	public ObservableList<PersonDetails> getDetailsList() {
		return detailsList;
	}

}
