package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.sales.SalesReturn;
import com.delains.report.query.ReportQueryReturnsInwards;
import com.delains.ui.sales.SalesReturnTable;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ReportAllReturnInwards extends BorderPane {

	private static TableView < SalesReturn > tableView;

	public static TableView < SalesReturn> getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < SalesReturn> tableView ) {
		ReportAllReturnInwards.tableView = tableView;
	}

	private GridPane gridPane;
	private JFXButton buttonExport;

	private void buildComponents() {

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonExport = new JFXButton( "export report" );
		gridPane.add( buttonExport, 0, 0 );

		this.setTop( gridPane );

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );
		tableView.setPrefWidth( 1200 );
		tableView.getColumns().addAll( SalesReturnTable.getTableView().getColumns() );

		String sheetName = null;
		LocalDate date = LocalDate.now();
		sheetName = "all sales (inward) returns report " + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQueryReturnsInwards.reportForAllReturnsInwardsRegardless( tableView, sheetName ) );

		WorkBookUtils.setSheetName( sheetName );

		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {

			WorkBookUtils.setWorkbook( ReportQueryReturnsInwards.getWorkBook() );

			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/all sales (inward) returns" );

		} );

	}

	public ReportAllReturnInwards() {
		this.setId( "main_borderpane" );

		getStylesheets().add( ReportAllReturnInwards.class.getResource( "report.css" ).toExternalForm() );

		buildComponents();

	}

}
