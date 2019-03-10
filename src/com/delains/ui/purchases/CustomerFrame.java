package com.delains.ui.purchases;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.customers.Customer;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.ui.sales.POSFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
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

	private Label labelName;
	private TextField fieldName;

	private Label labelEmail;
	private TextField fieldEmail;

	private Label labelPhone;
	private TextField fieldPhone;

	private GridPane gridPane;
	private HBox hBox;

	private HBox hBoxButtons;
	private JFXButton buttonSave;
	private JFXButton buttonCancel;
	private StageForAllPopUps stageForAllPopUps;

	private void buildingCustomerFrameComponents() {

		this.hBox = new HBox();

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		this.labelName = new Label( "customer name" );
		addToGrid( labelName, 0, 0 );

		this.fieldName = new TextField();
		addToGrid( fieldName, 1, 0 );

		this.labelEmail = new Label( "customer email" );
		addToGrid( labelEmail, 0, 1 );

		this.fieldEmail = new TextField();
		ComponentWidth.setWidthOfTextField( fieldEmail, 400 );
		addToGrid( fieldEmail, 1, 1 );

		this.labelPhone = new Label( "customer phone" );
		addToGrid( labelPhone, 0, 2 );

		this.fieldPhone = new TextField();
		addToGrid( fieldPhone, 1, 2 );

		this.hBoxButtons = new HBox( 10 );
		this.buttonSave = new JFXButton( "save" );
		this.hBoxButtons.getChildren().add( buttonSave );

		this.buttonCancel = new JFXButton( "cancel" );
		this.hBoxButtons.getChildren().add( buttonCancel );

		addToGrid( hBoxButtons, 1, 3 );

		this.hBox.getChildren().add( gridPane );

		stageForAllPopUps = new StageForAllPopUps( hBox, "add new customer" );

		this.buttonSave.setOnAction( e -> {

			if ( buttonSave.getText().equals( "save" ) ) {
				addNewCustomer();
			} else {
				saveChanges();
			}

		} );

		this.buttonCancel.setOnAction( e -> {

			stageForAllPopUps.close();

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

	private TableColumn < Customer, String > colName;
	private TableColumn < Customer, String > colEmail;
	private TableColumn < Customer, String > colPhone;

	private void buildTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		colName = new TableColumn <>( "Name" );
		colName.setCellValueFactory( new PropertyValueFactory <>( "customerName" ) );
		tableView.getColumns().add( colName );

		colEmail = new TableColumn <>( "Email" );
		colEmail.setCellValueFactory( new PropertyValueFactory <>( "customerEmail" ) );
		tableView.getColumns().add( colEmail );

		colPhone = new TableColumn <>( "Phone" );
		colPhone.setCellValueFactory( new PropertyValueFactory <>( "customerPhone" ) );
		tableView.getColumns().add( colPhone );

		/*
		 * 
		 * the use of a vbox makes it half way filled and using table view direct into
		 * the centre of the border pane fills the whole screen up
		 */

		this.setCenter( tableView );

		populateCustomerTableWithoutRefreshing();

	}

	private void addNewCustomer() {
		String customerName = null;
		String customerEmail = null;
		String customerPhone = null;

		new StageForAlerts();

		if ( fieldName.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "customer name can not be empty" );
			return;

		} else {

			customerName = fieldName.getText();

		}

		customerEmail = fieldEmail.getText();
		customerPhone = fieldPhone.getText();

		Customer customer = new Customer();

		customer.setCustomerEmail( customerEmail );
		customer.setCustomerName( customerName );
		customer.setCustomerPhone( customerPhone );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save this customer?" );

		if ( StageForAlerts.isDecide() == true ) {
			CustomerHibernation.newCustomer( customer );

			fieldEmail.clear();
			fieldName.clear();
			fieldPhone.clear();

			populateCustomerTableWithRefreshing();

			new POSFrame().populateComboBoxCustomers();

		}

	}

	private void populateCustomerTableWithoutRefreshing() {
		tableView.setItems( CustomerHibernation.findAllCustomersObservableList() );
	}

	public static void populateCustomerTableWithRefreshing() {
		tableView.getItems().clear();
		tableView.getItems().addAll( CustomerHibernation.findAllCustomersObservableListRefreshed() );

	}

	private void buildTopOfThisFrame() {
		HBox hBox = new HBox();
		Label labelTitleOfTable = new Label( "Table showing all customers" );
		hBox.getChildren().add( labelTitleOfTable );
		hBox.setAlignment( Pos.CENTER );
		this.setTop( hBox );
		chooseRow();
	}

	private void chooseRow() {

		tableView.setRowFactory( tr -> {

			TableRow < Customer > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {

					// Customer purchase = row.getItem();

				}
			} );

			return row;
		} );

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

		String customerName = null;
		String customerEmail = null;
		String customerPhone = null;

		if ( fieldName.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "customer name can not be empty" );
			return;

		} else {

			customerName = fieldName.getText();

		}

		customerEmail = fieldEmail.getText();
		customerPhone = fieldPhone.getText();

		Customer customer = new Customer();

		customer = tableView.getSelectionModel().getSelectedItem();

		System.out.println( "cust to ch: " + customer );

		customer.setCustomerEmail( customerEmail );
		customer.setCustomerName( customerName );
		customer.setCustomerPhone( customerPhone );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save changes?" );

		if ( StageForAlerts.isDecide() == true ) {

			CustomerHibernation.updateCustomer( customer, customer.getId() );

			stageForAllPopUps.setTitle( "add new customer" );
			buttonSave.setText( "save" );
			fieldEmail.clear();
			fieldName.clear();
			fieldPhone.clear();
			stageForAllPopUps.close();

			populateCustomerTableWithRefreshing();

			AuditHistoryHibernation.auditValues( "edited a customer: " + customer.getCustomerName(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

			new POSFrame().populateComboBoxCustomers();

		}

	}

}
