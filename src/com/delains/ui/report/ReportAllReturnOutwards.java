package com.delains.ui.report;

import java.time.LocalDate;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.report.query.ReportQueryReturnsOutwards;
import com.delains.ui.purchases.PurchaseReturnDialog;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ReportAllReturnOutwards extends BorderPane {

	private static TableView < PurchaseReturn > tableView;

	public static TableView < PurchaseReturn > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < PurchaseReturn > tableView ) {
		ReportAllReturnOutwards.tableView = tableView;
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
		tableView.getColumns().addAll( PurchaseReturnDialog.getTableView().getColumns() );

		String sheetName = null;
		LocalDate date = LocalDate.now();
		sheetName = "all purchases (outward) returns report " + date.toString();

		tableView.getItems().clear();
		tableView.setItems( ReportQueryReturnsOutwards.reportForAllReturnsOutwardsRegardless( tableView, sheetName ) );

		WorkBookUtils.setSheetName( sheetName );
		this.setCenter( tableView );

		buttonExport.setOnAction( e -> {
			WorkBookUtils.setWorkbook( ReportQueryReturnsOutwards.getWorkBook() );
			WorkBookUtils.writeWorkBookOut( WorkBookUtils.getSheetName(),
					ReceiptHeaderDAO.getReceiptHeader().getBusinessName() + "/all purchases (outward) returns" );
		} );
	}

	public ReportAllReturnOutwards() {
		this.setId( "main_borderpane" );
		getStylesheets().add( ReportAllReturnOutwards.class.getResource( "report.css" ).toExternalForm() );
		buildComponents();
	}

}
