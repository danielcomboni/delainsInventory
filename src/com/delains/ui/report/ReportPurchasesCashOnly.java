package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.purchases.Purchase;
import com.delains.report.query.ReportQueryPurchasesCashOnly;
import com.delains.ui.purchases.PurchaseFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ReportPurchasesCashOnly extends BorderPane {

	private static TableView < Purchase > tableView;

	public static TableView < Purchase > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < Purchase > tableView ) {
		ReportPurchasesCashOnly.tableView = tableView;
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
		tableView.getColumns().addAll( PurchaseFrame.getTableView().getColumns() );

		String sheetName = null;
		LocalDate date = LocalDate.now();
		sheetName = "cash purchases report " + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQueryPurchasesCashOnly.reportForPurchasesCashOnly( tableView, sheetName ) );

		WorkBookUtils.setSheetName( sheetName );

		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {

			WorkBookUtils.setWorkbook( ReportQueryPurchasesCashOnly.getWorkBook() );

			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/cash purchases only" );

		} );

	}

	public ReportPurchasesCashOnly() {
		this.setId( "main_borderpane" );

		getStylesheets().add( ReportPurchasesCashOnly.class.getResource( "report.css" ).toExternalForm() );

		buildComponents();

	}

}
