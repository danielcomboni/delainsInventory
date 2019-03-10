package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.SalesReturnDAORetrieve;
import com.delains.model.sales.SalesReturn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryReturnsInwards {

	public static ObservableList < SalesReturn > reportForAllReturnsInwardsRegardless(
			TableView < SalesReturn > tableView, String sheetName ) {
		ObservableList < SalesReturn > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		String whereQuery = "";
		SalesReturnDAORetrieve.exportList( sheetName, columns, whereQuery ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return SalesReturnDAORetrieve.getWorkbook();
	}

}