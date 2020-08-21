package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.customers.Customer;
import com.delains.model.sales.SalesReturn;
import com.delains.report.query.ReportQueryReturnsInwards;
import com.delains.report.query.ReportQueryReturnsInwardsByCustomer;
import com.delains.report.query.ReportQuerySalesCreditByCustomer;
import com.delains.ui.sales.SalesReturnTable;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

public class ReportReturnInwardsByCustomer extends BorderPane {

	private static TableView < SalesReturn > tableView;

	public static TableView < SalesReturn > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < SalesReturn > tableView ) {
		ReportReturnInwardsByCustomer.tableView = tableView;
	}

	private GridPane gridPane;
	private JFXButton buttonExport;

	private ComboBox < Customer > comboBox;

	private void buildComponents() {
		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );
		buttonExport = new JFXButton( "export report" );
		buttonExport.setDisable( true );
		gridPane.add( buttonExport, 0, 0 );
		comboBox = new ComboBox <>();
		gridPane.add( comboBox, 0, 1 );
		this.setTop( gridPane );
		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );
		tableView.setPrefWidth( 1200 );
		tableView.getColumns().addAll( SalesReturnTable.getTableView().getColumns() );
		String sheetName = null;
		LocalDate date = LocalDate.now();
		sheetName = " sales (inward) returns by customer report " + date.toString();
		tableView.getItems().clear();
		tableView.setItems( ReportQueryReturnsInwards.reportForAllReturnsInwardsRegardless( tableView, sheetName ) );
		comboBox.setOnAction( e -> {
			tableView.getItems().clear();
			tableView.setItems( ReportQueryReturnsInwardsByCustomer.reportForReturnsInwardsByCustomer( tableView,
					"sales (inward) returns by customer", comboBox.getSelectionModel().getSelectedItem().getId() ) );
			WorkBookUtils.setWorkbook( ReportQuerySalesCreditByCustomer.getWorkBook() );
			buttonExport.setDisable( false );
		} );
		WorkBookUtils.setSheetName( sheetName );
		this.setCenter( tableView );
		buttonExport.setOnAction( e -> {

			// String sheetTitle = "cash purchases from " + dateBegin.toString() + " to " +
			// dateEnd.toString() + " ("
			// + date.toString() + ")";

			WorkBookUtils.setSheetName( "sales (inward) returns by customer "
					+ comboBox.getSelectionModel().getSelectedItem().getCustomerName() + " (" + date.toString() + ")" );

			// WorkBookUtils.setWorkbook( ReportQueryPurchasesCashBetweenDates.getWorkBook()
			// );
			//
			// WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
			// ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/cash purchases
			// between dates" );
			//

			WorkBookUtils.setWorkbook( ReportQueryReturnsInwardsByCustomer.getWorkBook() );
			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/sales (inward) returns by customer" );
		} );
		populateComboBoxCustomers();
	}

	public void populateComboBoxCustomers() {
		comboBox.getItems().clear();
		if ( comboBox.getItems().isEmpty() ) {
			comboBox.setItems( CustomerHibernation.findAllCustomersObservableListRefreshed() );
		} else {
			comboBox.getItems().clear();
			comboBox.getItems().addAll( CustomerHibernation.findAllCustomersObservableListRefreshed() );
		}
		comboBox.setConverter( new StringConverter < Customer >() {
			@Override
			public String toString( Customer object ) {
				return object.getCustomerName();
			}

			@Override
			public Customer fromString( String string ) {
				return comboBox.getItems().stream().filter( e -> e.getCustomerName().equals( string ) ).findFirst()
						.orElse( null );
			}
		} );
	}

	public ReportReturnInwardsByCustomer() {
		this.setId( "main_borderpane" );

		getStylesheets().add( ReportAllReturnInwards.class.getResource( "report.css" ).toExternalForm() );

		buildComponents();

	}

}
