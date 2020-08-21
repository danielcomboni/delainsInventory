package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.customers.Customer;
import com.delains.model.pos.POS;
import com.delains.report.query.ReportQuerySalesCreditByCustomerBetweenDates;
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

public class ReportSalesCreditByCustomerBetweenDates extends BorderPane {

	private static TableView < POS > tableView;

	public static TableView < POS > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < POS > tableView ) {
		ReportSalesCreditByCustomerBetweenDates.tableView = tableView;
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

		labelDateFrom = new Label( "from date:" );
		gridPane.add( labelDateFrom, 0, 1 );

		datePickerFrom = new DatePicker();
		datePickerFrom.setEditable( false );
		gridPane.add( datePickerFrom, 0, 2 );

		labelDateTo = new Label( "to date:" );
		gridPane.add( labelDateTo, 0, 3 );
		datePickerTo = new DatePicker();
		gridPane.add( datePickerTo, 0, 4 );

		Label labelCustomerName = new Label( "customer name:" );
		gridPane.add( labelCustomerName, 0, 5 );

		comboBox = new ComboBox <>();
		gridPane.add( comboBox, 0, 6 );

		this.setTop( gridPane );

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );
		tableView.setPrefWidth( 1200 );

		tableView.getColumns().addAll( AllSalesFrame.getTableView().getColumns() );

		String sheetName = null;
		LocalDate date = LocalDate.now();

		sheetName = "credit sales by customer between dates report " + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQuerySalesCreditOnly.reportForAllSalesCreditOnly( tableView, sheetName ) );

		buttonExport.setDisable( true );

		datePickerFrom.setOnAction( e -> {

			LocalDate dateFrom = datePickerFrom.getValue();

			LocalDate dateTo = datePickerTo.getValue();

			if ( datePickerTo.getValue() != null && comboBox.getSelectionModel().getSelectedItem() != null ) {

				tableView.getItems().clear();
				tableView.setItems(
						ReportQuerySalesCreditByCustomerBetweenDates.reportForAllSalesCreditByCustomerBetweenDates(
								tableView, "credit sales by customer between dates report", dateFrom.toString(),
								dateTo.toString(), comboBox.getSelectionModel().getSelectedItem().getId() ) );

				WorkBookUtils.setWorkbook( ReportQuerySalesCreditByCustomerBetweenDates.getWorkBook() );

				buttonExport.setDisable( false );

				dateBegin = datePickerFrom.getValue();
				dateEnd = datePickerTo.getValue();

			}

		} );

		datePickerTo.setOnAction( e -> {

			LocalDate dateFrom = datePickerFrom.getValue();

			LocalDate dateTo = datePickerTo.getValue();

			if ( datePickerFrom.getValue() != null && comboBox.getSelectionModel().getSelectedItem() != null ) {

				tableView.getItems().clear();
				tableView.setItems(
						ReportQuerySalesCreditByCustomerBetweenDates.reportForAllSalesCreditByCustomerBetweenDates(
								tableView, "credit sales by customer between dates report", dateFrom.toString(),
								dateTo.toString(), comboBox.getSelectionModel().getSelectedItem().getId() ) );

				WorkBookUtils.setWorkbook( ReportQuerySalesCreditByCustomerBetweenDates.getWorkBook() );

				buttonExport.setDisable( false );

				dateBegin = datePickerFrom.getValue();
				dateEnd = datePickerTo.getValue();

			}

		} );

		comboBox.setOnAction( e -> {

			LocalDate dateFrom = datePickerFrom.getValue();

			LocalDate dateTo = datePickerTo.getValue();

			if ( datePickerFrom.getValue() != null && datePickerTo.getValue() != null ) {

				tableView.getItems().clear();
				tableView.setItems(
						ReportQuerySalesCreditByCustomerBetweenDates.reportForAllSalesCreditByCustomerBetweenDates(
								tableView, "credit sales by customer between dates report", dateFrom.toString(),
								dateTo.toString(), comboBox.getSelectionModel().getSelectedItem().getId() ) );

				WorkBookUtils.setWorkbook( ReportQuerySalesCreditByCustomerBetweenDates.getWorkBook() );

				buttonExport.setDisable( false );

				dateBegin = datePickerFrom.getValue();
				dateEnd = datePickerTo.getValue();

			}

		} );

		WorkBookUtils.setSheetName( sheetName );

		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {

			String sheetTitle = "credit sales by customer - "
					+ comboBox.getSelectionModel().getSelectedItem().getCustomerName() + " from " + dateBegin + " to "
					+ dateEnd + " (" + date.toString() + ")";

			WorkBookUtils.setSheetName( sheetTitle );

			WorkBookUtils.setWorkbook( ReportQuerySalesCreditByCustomerBetweenDates.getWorkBook() );

			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/credit sales by customer between dates" );

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

	public ReportSalesCreditByCustomerBetweenDates() {

		this.setId( "main_borderpane" );

		getStylesheets()
				.add( ReportSalesCreditByCustomerBetweenDates.class.getResource( "report.css" ).toExternalForm() );

		buildComponents();

		populateComboBoxCustomers();

	}

}
