package com.delains.ui.customers;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.model.customers.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CustomerData {

    public static ObservableList<Customer> data = FXCollections.observableArrayList(CustomerHibernation.findAllCustomersObservableListRefreshed());

    public static Customer addNewCustomerToUI(Customer customer){
        Service<Customer> service;
        service = new Service<Customer>() {
            @Override
            protected Task<Customer> createTask() {
                return new Task<Customer>() {
                    @Override
                    protected Customer call() {

                        data.add(customer);
                        return customer;

                    }
                };
            }
        };
        service.start();

        return service.getValue();
    }
}
