package com.delains.ui.customers;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.customers.Customer;
import com.delains.model.history.AuditHistory;
import com.delains.ui.history.AuditHistoryData;
import com.delains.ui.history.AuditHistoryFrame;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.purchases.CustomerFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CustomersTabInAccordion extends VBox {

	public CustomersTabInAccordion() {

		GridPane gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		JFXButton buttonAddNew = new JFXButton("Add new customer");
		gridPane.add(buttonAddNew, 0, 0 );

		JFXButton buttonSelectForEditing = new JFXButton("Edit customer info");
		gridPane.add(buttonSelectForEditing, 0, 1 );

		JFXButton buttonDelete = new JFXButton("Delete customer");
		gridPane.add(buttonDelete, 0, 2 );

		this.getChildren().add(gridPane);

		frame = new CustomerFrame();

		buttonAddNew.setOnAction(e -> newCustomerPopUp());

		buttonSelectForEditing.setOnAction(e -> showPopUp());

		buttonDelete.setOnAction(e -> deleteCustomer());

	}

	private CustomerFrame frame;

	private void newCustomerPopUp() {
		frame.newCustomer();
	}

	private void showPopUp() {

		new StageForAlerts();

		int row = CustomerFrame.getTableView().getSelectionModel().getSelectedIndex();

		// Purchase p =
		// PurchaseFrame.getTableView().getSelectionModel().getSelectedItem();
		Customer cust = CustomerFrame.getTableView().getSelectionModel().getSelectedItem();
		if ( row < 0 ) {

			StageForAlerts.inform( "alert", "please choose a row to edit" );
			return;

		}

		frame.showEditingPopUp( cust );
	}

	private void deleteCustomer() {
		new StageForAlerts();

		int row = CustomerFrame.getTableView().getSelectionModel().getSelectedIndex();

		// Purchase p =
		// PurchaseFrame.getTableView().getSelectionModel().getSelectedItem();
		Customer cust = CustomerFrame.getTableView().getSelectionModel().getSelectedItem();
		if ( row < 0 ) {

			StageForAlerts.inform( "alert", "please choose a row to delete" );
			return;

		}

		StageForAlerts.discontinue( "confirm", "are you sure you want to delete this customer permanently?" );
		if (StageForAlerts.isDecide()) {

			CustomerHibernation.deleteCustomer( cust.getId() );
			CustomerData.data.remove(cust);

			AuditHistory auditHistory = AuditHistoryHibernation.auditValues( " a customer deleted", UserLoggedIn.getUserLoggedIn() );
			AuditHistoryData.theData.add(auditHistory);
		}

	}
}
