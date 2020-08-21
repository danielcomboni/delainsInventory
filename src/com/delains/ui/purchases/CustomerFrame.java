package com.delains.ui.purchases;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.customers.Customer;
import com.delains.model.history.AuditHistory;
import com.delains.ui.customers.CustomerData;
import com.delains.ui.history.AuditHistoryData;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;

import com.jfoenix.controls.JFXButton;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CustomerFrame extends BorderPane {

	public CustomerFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( CustomerFrame.class.getResource( "customer.css" ).toExternalForm() );

		buildingCustomerFrameComponents();

		buildTable();

		buildTopOfThisFrame();

	}

	private TextField fieldName;

	private TextField fieldEmail;

	private TextField fieldPhone;

	private GridPane gridPane;

	private JFXButton buttonSave;
	private StageForAllPopUps stageForAllPopUps;

	private void buildingCustomerFrameComponents() {

		HBox hBox = new HBox();

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		Label labelName = new Label("customer name");
		addToGrid(labelName, 0, 0 );

		this.fieldName = new TextField();
		addToGrid( fieldName, 1, 0 );

		Label labelEmail = new Label("customer email");
		addToGrid(labelEmail, 0, 1 );

		this.fieldEmail = new TextField();
		ComponentWidth.setWidthOfTextField( fieldEmail, 400 );
		addToGrid( fieldEmail, 1, 1 );

		Label labelPhone = new Label("customer phone");
		addToGrid(labelPhone, 0, 2 );

		this.fieldPhone = new TextField();
		addToGrid( fieldPhone, 1, 2 );

		HBox hBoxButtons = new HBox(10);
		this.buttonSave = new JFXButton( "save" );
		hBoxButtons.getChildren().add( buttonSave );

		JFXButton buttonCancel = new JFXButton("cancel");
		hBoxButtons.getChildren().add(buttonCancel);

		addToGrid(hBoxButtons, 1, 3 );

		hBox.getChildren().add( gridPane );

		stageForAllPopUps = new StageForAllPopUps(hBox, "add new customer" );

		this.buttonSave.setOnAction( e -> {

			if ( buttonSave.getText().equals( "save" ) ) {
				addNewCustomer();
			} else {
				saveChanges();
			}

		} );

		buttonCancel.setOnAction(e -> {

			stageForAllPopUps.close();
			stageForAllPopUps.setTitle("add new customer");
			clearFields();

		} );

	}

	public void newCustomer() {

		stageForAllPopUps.showAndWait();

	}

	private void addToGrid( Node node, int col, int row ) {
		this.gridPane.add( node, col, row );
	}

	private static TableView < Customer > tableView;

	public static TableView < Customer > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < Customer > tableView ) {
		CustomerFrame.tableView = tableView;
	}


	private void buildTable() {

		tableView = new TableView<>();
		tableView.setTableMenuButtonVisible( true );
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );


		TableColumn < Customer, String > colName = new TableColumn <>( "Name" );
		TableColumn < Customer, String > colEmail = new TableColumn <>( "Email" );
		TableColumn < Customer, String > colPhone = new TableColumn <>( "Phone" );

		tableView.getColumns().add( colName );

		colEmail.setCellValueFactory( cellData -> cellData.getValue().customerEmailProperty() );
		tableView.getColumns().add( colEmail );

		colPhone.setCellValueFactory( cellData -> cellData.getValue().customerPhoneProperty() );
		tableView.getColumns().add( colPhone );

		/*
		 * 
		 * the use of a vbox makes it half way filled and using table view direct into
		 * the centre of the border pane fills the whole screen up
		 */

		tableView.setItems(CustomerData.data);
		this.setCenter( tableView );

	}

	private void addNewCustomer() {
		String customerName;
		String customerEmail;
		String customerPhone;

		new StageForAlerts();

		if (!fieldName.getText().trim().isEmpty()) {

			customerName = fieldName.getText();

		} else {

			StageForAlerts.inform( "alert", "customer name can not be empty" );
			return;

		}

		customerEmail = fieldEmail.getText();
		customerPhone = fieldPhone.getText();

		Customer customer = new Customer();

		customer.setCustomerEmail( customerEmail );
		customer.setCustomerName( customerName );
		customer.setCustomerPhone( customerPhone );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save this customer?" );

		if (StageForAlerts.isDecide()) {

			Service<Void> service;
			service = new Service<Void>() {
				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {
						@Override
						protected Void call() {

							Customer customer1 = CustomerHibernation.newCustomer( customer );
							clearFields();
							CustomerData.addNewCustomerToUI(customer1);

							return null;
						}
					};
				}
			};
			service.start();
		}

	}


	private void buildTopOfThisFrame() {
		HBox hBox = new HBox();
		Label labelTitleOfTable = new Label( "Table showing all customers" );
		hBox.getChildren().add( labelTitleOfTable );
		hBox.setAlignment( Pos.CENTER );
		this.setTop( hBox );
	}

	public void showEditingPopUp( Customer customer ) {

		stageForAllPopUps.setTitle( "edit customer info" );
		buttonSave.setText( "save changes" );
		fieldEmail.setText( customer.getCustomerEmail() );
		fieldName.setText( customer.getCustomerName() );
		fieldPhone.setText( customer.getCustomerPhone() );
		stageForAllPopUps.showAndWait();

	}

	private void saveChanges() {
		new StageForAlerts();

		String customerName;
		String customerEmail;
		String customerPhone;

		if ( fieldName.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "customer name can not be empty" );
			return;

		} else {

			customerName = fieldName.getText();

		}

		customerEmail = fieldEmail.getText();
		customerPhone = fieldPhone.getText();

		Customer customer = tableView.getSelectionModel().getSelectedItem();

		customer.setCustomerEmail( customerEmail );
		customer.setCustomerName( customerName );
		customer.setCustomerPhone( customerPhone );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save changes?" );

		if (StageForAlerts.isDecide()) {

			Service<Void> service;

			service = new Service<Void>() {
				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {
						@Override
						protected Void call() {

							Customer customer1 = CustomerHibernation.updateCustomer(customer, customer.getId() );

							stageForAllPopUps.setTitle( "add new customer" );
							buttonSave.setText( "save" );
							clearFields();
							stageForAllPopUps.close();

							CustomerData.addNewCustomerToUI(customer1);

							AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "edited a customer: " + customer.getCustomerName(),
									UserLoggedIn.getUserLoggedIn() );

							AuditHistoryData.theData.add(auditHistory);

							return null;
						}
					};
				}
			};
			service.start();

		}

	}

	private void clearFields() {
		fieldEmail.clear();
		fieldName.clear();
		fieldPhone.clear();
	}

}
