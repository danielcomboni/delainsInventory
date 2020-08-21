package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.purchases.ReportPurchasesCreditBetweenDatesRetrieve;
import com.delains.model.purchases.Purchase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryPurchasesCreditBetweenDates {

	public static ObservableList < Purchase > reportForPurchasesCreditBetweenDates( TableView < Purchase > tableView,
			String sheetName, String dateFrom, String dateTo ) {
		ObservableList < Purchase > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		ReportPurchasesCreditBetweenDatesRetrieve.exportList( sheetName, columns, dateFrom, dateTo ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return ReportPurchasesCreditBetweenDatesRetrieve.getWorkbook();
	}

	
}
