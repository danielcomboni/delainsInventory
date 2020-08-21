package com.delains.dao.customers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.customers.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerDAO {

	private static LinkedHashMap < String, String > customersTableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "customer_email", "VARCHAR(255)" );
		map.put( "customer_phone", "VARCHAR(255)" );
		map.put( "customer_name", "VARCHAR(255)" );
		map.put( "date", "VARCHAR(255)" );
		return map;
	}

	private static void createTableCustomers() {
		DBUtils.apiToCreateTable( customersTableDefinitions(), "customers" );
	}

	public static Customer newCustomer( Customer customer ) {
		createTableCustomers();
		return CustomerDAOInsert.newCustomer( customer );
	}

	public static Customer updateCustomer( Customer customer, BigDecimal idOfCustomer ) {
		return CustomerDAOUpdate.updateUser( customer, idOfCustomer );
	}

	public static void deleteCustomer( BigDecimal id ) {
		CustomerDAODelete.deleteCustomer( id );
	}

	public static List < Customer > findAllCustomersListUtil() {
		createTableCustomers();
		return CustomerDAORetrieve.findAllCustomers();
	}

	public static ObservableList < Customer > changeListUtilToListObservable() {

		ObservableList < Customer > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllCustomersListUtil().size(); i++ ) {
			Customer u = findAllCustomersListUtil().get( i );
			observableList.add( u );
			;
		}

		return observableList;
	}

}
