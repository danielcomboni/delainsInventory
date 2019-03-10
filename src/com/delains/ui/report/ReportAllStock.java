package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.stock.Stock;
import com.delains.report.query.ReportQueryAllStock;
import com.delains.ui.stock.StockFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ReportAllStock extends BorderPane {

	private static TableView < Stock > tableView;

	public static TableView < Stock > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < Stock > tableView ) {
		ReportAllStock.tableView = tableView;
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
		tableView.getColumns().addAll( StockFrame.getTableView().getColumns() );
		// ReportAllSales.setTableView( );

		String sheetName = null;
		LocalDate date = LocalDate.now();
		sheetName = "all stock (inventory) available" + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQueryAllStock.reportForAllStockRegardless( tableView, sheetName ) );

		WorkBookUtils.setSheetName( sheetName );

		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {

			WorkBookUtils.setWorkbook( ReportQueryAllStock.getWorkBook() );

			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/all stock (inventory) available" );

		} );

	}

	public ReportAllStock() {
		this.setId( "main_borderpane" );

		getStylesheets().add( ReportAllStock.class.getResource( "report.css" ).toExternalForm() );

		buildComponents();

	}

}
