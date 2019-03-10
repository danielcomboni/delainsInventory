package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.purchases.PurchaseReturnDAORetieve;
import com.delains.model.purchases.PurchaseReturn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryReturnsOutwards {

	public static ObservableList < PurchaseReturn > reportForAllReturnsOutwardsRegardless(
			TableView < PurchaseReturn > tableView, String sheetName ) {
		ObservableList < PurchaseReturn > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		String whereQuery = "";
		PurchaseReturnDAORetieve.exportList( sheetName, columns, whereQuery ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return PurchaseReturnDAORetieve.getWorkbook();
	}

}
