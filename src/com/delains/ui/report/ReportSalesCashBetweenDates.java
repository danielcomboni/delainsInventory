package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.pos.POS;
import com.delains.report.query.ReportQuerySalesCashBetweenDates;
import com.delains.report.query.ReportQuerySalesCashOnly;
import com.delains.ui.sales.AllSalesFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ReportSalesCashBetweenDates extends BorderPane {

	private static TableView < POS > tableView;

	public static TableView < POS > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < POS > tableView ) {
		ReportSalesCashBetweenDates.tableView = tableView;
	}

	private GridPane gridPane;
	private JFXButton buttonExport;

	private Label labelDateFrom;
	private DatePicker datePickerFrom;

	private Label labelDateTo;
	private DatePicker datePickerTo;

	private LocalDate dateBegin = null;
	private LocalDate dateEnd = null;

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

		sheetName = "cash sales between dates reports " + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQuerySalesCashOnly.reportForAllSalesCashOnly( tableView, sheetName ) );

		buttonExport.setDisable( true );

		datePickerFrom.setOnAction( e -> {

			LocalDate dateFrom = datePickerFrom.getValue();

			LocalDate dateTo = datePickerTo.getValue();

			if ( datePickerTo.getValue() != null ) {

				tableView.getItems().clear();
				tableView.setItems( ReportQuerySalesCashBetweenDates.reportForAllSalesCashBetweenDates( tableView,
						"cash sales between dates", dateFrom.toString(), dateTo.toString() ) );

				WorkBookUtils.setWorkbook( ReportQuerySalesCashBetweenDates.getWorkBook() );

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
				tableView.setItems( ReportQuerySalesCashBetweenDates.reportForAllSalesCashBetweenDates( tableView,
						"cash sales between dates", dateFrom.toString(), dateTo.toString() ) );

				WorkBookUtils.setWorkbook( ReportQuerySalesCashBetweenDates.getWorkBook() );

				buttonExport.setDisable( false );

				dateBegin = datePickerFrom.getValue();
				dateEnd = datePickerTo.getValue();

			}

		} );

		WorkBookUtils.setSheetName( sheetName );

		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {

			String sheetTitle = "cash sales from " + dateBegin.toString() + " to " + dateEnd.toString() + " ("
					+ date.toString() + ")";

			WorkBookUtils.setSheetName( sheetTitle );

			WorkBookUtils.setWorkbook( ReportQuerySalesCashBetweenDates.getWorkBook() );

			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/cash sales between dates" );

		} );

	}

	public ReportSalesCashBetweenDates() {

		this.setId( "main_borderpane" );

		getStylesheets().add( ReportSalesCashBetweenDates.class.getResource( "report.css" ).toExternalForm() );

		buildComponents();

	}

}
