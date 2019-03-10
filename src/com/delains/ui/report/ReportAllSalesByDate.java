package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.pos.POS;
import com.delains.report.query.ReportQuerySales;
import com.delains.report.query.ReportQuerySalesByDate;
import com.delains.ui.sales.AllSalesFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ReportAllSalesByDate extends BorderPane {

	private static TableView < POS > tableView;

	public static TableView < POS > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < POS > tableView ) {
		ReportAllSalesByDate.tableView = tableView;
	}

	private GridPane gridPane;
	private JFXButton buttonExport;

	private DatePicker datePicker;
	private JFXButton buttonToday;
	// private JFXButton buttonView;

	private void buildComponents() {

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonExport = new JFXButton( "export report" );
		gridPane.add( buttonExport, 0, 0 );

		datePicker = new DatePicker();
		datePicker.setEditable( false );
		gridPane.add( datePicker, 0, 1 );

		buttonToday = new JFXButton( "Today" );
		gridPane.add( buttonToday, 0, 2 );

		this.setTop( gridPane );

		tableView = new TableView <>();
		tableView.setPrefWidth( 1200 );

		tableView.getColumns().addAll( AllSalesFrame.getTableView().getColumns() );

		String sheetName = null;
		LocalDate date = LocalDate.now();
		sheetName = "all sales by date report " + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQuerySales.reportForAllSalesRegardless( tableView, sheetName ) );

		datePicker.setOnAction( e -> {

			LocalDate d = datePicker.getValue();

			tableView.getItems().clear();
			tableView.setItems( ReportQuerySalesByDate.reportForAllSalesByDate( tableView, "all sales by date report",
					d.toString() ) );

			WorkBookUtils.setWorkbook( ReportQuerySalesByDate.getWorkBook() );

		} );

		WorkBookUtils.setSheetName( sheetName );

		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {

			WorkBookUtils.setWorkbook( ReportQuerySalesByDate.getWorkBook() );

			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/all sales by date" );

		} );

	}

	public ReportAllSalesByDate() {

		this.setId( "main_borderpane" );

		getStylesheets().add( ReportAllSalesByDate.class.getResource( "report.css" ).toExternalForm() );

		buildComponents();

	}

}
