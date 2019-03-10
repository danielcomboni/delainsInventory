package com.delains.dao.customers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.customers.Customer;

import javafx.collections.ObservableList;

public class CustomerHibernation {

	private static ObservableList<Customer> customers;

	public static ObservableList<Customer> getCustomers() {
		return customers;
	}

	public static void setCustomers(ObservableList<Customer> customers) {
		CustomerHibernation.customers = customers;
	}

	public static void newCustomer(Customer customer) {
		CustomerDAO.newCustomer(customer);
	}

	public static void updateCustomer(Customer customer, BigDecimal idOfCustomer) {
		CustomerDAO.updateCustomer(customer, idOfCustomer);
	}

	public static void deleteCustomer(BigDecimal id) {
		CustomerDAO.deleteCustomer(id);
	}

	public static ObservableList<Customer> findAllCustomersObservableList() {
		if (getCustomers() == null) {
			setCustomers(CustomerDAO.changeListUtilToListObservable());
		}
		return getCustomers();
	}

	public static ObservableList<Customer> findAllCustomersObservableListRefreshed() {
		setCustomers(CustomerDAO.changeListUtilToListObservable());
		return getCustomers();
	}

	public static Map<BigDecimal, Customer> mapOfCustomersToThierId() {
		Map<BigDecimal, Customer> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllCustomersObservableList().size(); i++) {
			Customer type = findAllCustomersObservableList().get(i);
			map.put(type.getId(), type);
		}
		return map;
	}

}
