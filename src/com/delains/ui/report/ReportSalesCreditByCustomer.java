package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.customers.Customer;
import com.delains.model.pos.POS;
import com.delains.report.query.ReportQuerySalesCreditByCustomer;
import com.delains.report.query.ReportQuerySalesCreditOnly;
import com.delains.ui.sales.AllSalesFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

public class ReportSalesCreditByCustomer extends BorderPane {

	private static TableView < POS > tableView;

	public static TableView < POS > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < POS > tableView ) {
		ReportSalesCreditByCustomer.tableView = tableView;
	}

	private GridPane gridPane;
	private JFXButton buttonExport;

	private Label labelDateFrom;
	private DatePicker datePickerFrom;

	private Label labelDateTo;
	private DatePicker datePickerTo;

	private LocalDate dateBegin = null;
	private LocalDate dateEnd = null;

	private ComboBox < Customer > comboBox;

	private void buildComponents() {

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonExport = new JFXButton( "export report" );
		gridPane.add( buttonExport, 0, 0 );

		labelDateFrom = new Label( "customer name:" );
		gridPane.add( labelDateFrom, 0, 1 );

		datePickerFrom = new DatePicker();
		comboBox = new ComboBox <>();
		datePickerFrom.setEditable( false );
		gridPane.add( comboBox, 0, 2 );

		// labelDateTo = new Label( "to date:" );
		// gridPane.add( labelDateTo, 0, 3 );
		// datePickerTo = new DatePicker();
		// gridPane.add( datePickerTo, 0, 4 );

		// buttonToday = new JFXButton( "Today" );
		// gridPane.add( buttonToday, 0, 2 );

		this.setTop( gridPane );

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );
		tableView.setPrefWidth( 1200 );

		tableView.getColumns().addAll( AllSalesFrame.getTableView().getColumns() );

		String sheetName = null;
		LocalDate date = LocalDate.now();

		sheetName = "credit sales between dates reports " + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQuerySalesCreditOnly.reportForAllSalesCreditOnly( tableView, sheetName ) );

		buttonExport.setDisable( true );

		comboBox.setOnAction( e -> {

			tableView.getItems().clear();
			tableView.setItems( ReportQuerySalesCreditByCustomer.reportForAllSalesCreditByCustomer( tableView,
					"credit sales by customer", comboBox.getSelectionModel().getSelectedItem().getId() ) );

			WorkBookUtils.setWorkbook( ReportQuerySalesCreditByCustomer.getWorkBook() );

			buttonExport.setDisable( false );

		} );

		WorkBookUtils.setSheetName( sheetName );

		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {

			String sheetTitle = "credit sales by customer - "
					+ comboBox.getSelectionModel().getSelectedItem().getCustomerName() + " (" + date.toString() + ")";

			WorkBookUtils.setSheetName( sheetTitle );

			WorkBookUtils.setWorkbook( ReportQuerySalesCreditByCustomer.getWorkBook() );

			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/credit sales by customer" );

		} );

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

	public ReportSalesCreditByCustomer() {

		this.setId( "main_borderpane" );

		getStylesheets().add( ReportSalesCreditByCustomer.class.getResource( "report.css" ).toExternalForm() );

		buildComponents();

		populateComboBoxCustomers();

	}

}
