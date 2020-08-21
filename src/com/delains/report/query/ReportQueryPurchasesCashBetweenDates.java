package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.purchases.ReportPurchasesCashBetweenDatesRetrieve;
import com.delains.model.purchases.Purchase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryPurchasesCashBetweenDates {

	public static ObservableList < Purchase > reportForPurchasesCashBetweenDates( TableView < Purchase > tableView,
			String sheetName, String dateFrom, String dateTo ) {
		ObservableList < Purchase > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		ReportPurchasesCashBetweenDatesRetrieve.exportList( sheetName, columns, dateFrom, dateTo ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return ReportPurchasesCashBetweenDatesRetrieve.getWorkbook();
	}

}
