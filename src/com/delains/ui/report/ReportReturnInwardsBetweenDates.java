package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.customers.Customer;
import com.delains.model.sales.SalesReturn;
import com.delains.report.query.ReportQueryReturnsInwards;
import com.delains.report.query.ReportQueryReturnsInwardsBetweenDates;
import com.delains.ui.sales.SalesReturnTable;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ReportReturnInwardsBetweenDates extends BorderPane {

	private static TableView < SalesReturn > tableView;

	public static TableView < SalesReturn > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < SalesReturn > tableView ) {
		ReportReturnInwardsBetweenDates.tableView = tableView;
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

		this.setTop( gridPane );

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );
		tableView.setPrefWidth( 1200 );
		tableView.getColumns().addAll( SalesReturnTable.getTableView().getColumns() );

		String sheetName = null;
		LocalDate date = LocalDate.now();
		sheetName = "sales (inward) returns between dates " + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQueryReturnsInwards.reportForAllReturnsInwardsRegardless( tableView, sheetName ) );

		buttonExport.setDisable( true );

		datePickerFrom.setOnAction( e -> {
			LocalDate dateFrom = datePickerFrom.getValue();
			LocalDate dateTo = datePickerTo.getValue();
			if ( datePickerTo.getValue() != null ) {
				tableView.getItems().clear();
				tableView.setItems( ReportQueryReturnsInwardsBetweenDates.reportForReturnsInwardsBetweenDates(
						tableView, "sales (inward) returns between dates report", dateFrom.toString(),
						dateTo.toString() ) );
				WorkBookUtils.setWorkbook( ReportQueryReturnsInwardsBetweenDates.getWorkBook() );
				buttonExport.setDisable( false );
				dateBegin = datePickerFrom.getValue();
				dateEnd = datePickerTo.getValue();
			}
		} );

		datePickerTo.setOnAction( e -> {
			LocalDate dateFrom = datePickerFrom.getValue();
			LocalDate dateTo = datePickerTo.getValue();
			if ( datePickerFrom.getValue() != null ) {
				tableView.getItems().clear();
				tableView.setItems( ReportQueryReturnsInwardsBetweenDates.reportForReturnsInwardsBetweenDates(
						tableView, "sales (inward) returns between dates report", dateFrom.toString(),
						dateTo.toString() ) );
				WorkBookUtils.setWorkbook( ReportQueryReturnsInwardsBetweenDates.getWorkBook() );
				buttonExport.setDisable( false );
				dateBegin = datePickerFrom.getValue();
				dateEnd = datePickerTo.getValue();
			}
		} );

		WorkBookUtils.setSheetName( sheetName );
		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {
			String sheetTitle = "sales (inward) return - " + " from " + dateBegin + " to " + dateEnd + " ("
					+ date.toString() + ")";
			WorkBookUtils.setSheetName( sheetTitle );
			WorkBookUtils.setWorkbook( ReportQueryReturnsInwardsBetweenDates.getWorkBook() );
			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName()
							+ "/sales (inward) returns between dates" );
		} );

	}

	public ReportReturnInwardsBetweenDates() {
		this.setId( "main_borderpane" );
		getStylesheets().add( ReportReturnInwardsBetweenDates.class.getResource( "report.css" ).toExternalForm() );
		buildComponents();
	}
}