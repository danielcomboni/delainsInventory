package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.purchases.ReportPurchasesCashOnlyRetrieve;
import com.delains.model.purchases.Purchase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryPurchasesCashOnly {

	public static ObservableList < Purchase > reportForPurchasesCashOnly( TableView < Purchase > tableView,
			String sheetName ) {
		ObservableList < Purchase > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		ReportPurchasesCashOnlyRetrieve.exportList( sheetName, columns ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return ReportPurchasesCashOnlyRetrieve.getWorkbook();
	}

}
