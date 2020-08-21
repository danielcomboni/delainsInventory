package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.POSDAORetrieve;
import com.delains.model.pos.POS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQuerySales {

	public static ObservableList < POS > reportForAllSalesRegardless( TableView < POS > tableView, String sheetName ) {
		ObservableList < POS > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		String whereQuery = "";
		POSDAORetrieve.exportList( sheetName, columns, whereQuery ).entrySet().parallelStream()
				.forEachOrdered( s -> list.add( s.getValue() ) );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return POSDAORetrieve.getWorkbook();
	}
}
