package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.SalesReturnsInwardsBetweenDatesDAORetrieve;
import com.delains.model.sales.SalesReturn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryReturnsInwardsBetweenDates {

	public static ObservableList < SalesReturn > reportForReturnsInwardsBetweenDates(
			TableView < SalesReturn > tableView, String sheetName, String dateFrom, String dateTo ) {
		ObservableList < SalesReturn > list = FXCollections.observableArrayList();
		ObservableList < String > columns = FXCollections.observableArrayList();
		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );
		SalesReturnsInwardsBetweenDatesDAORetrieve.exportList( sheetName, columns, dateFrom, dateTo ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );
		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return SalesReturnsInwardsBetweenDatesDAORetrieve.getWorkbook();
	}

}
