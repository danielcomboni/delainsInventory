package com.delains.report.query;

import java.math.BigDecimal;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.SalesReturnsInwardsByCustomerBetweenDatesDAORetrieve;
import com.delains.model.sales.SalesReturn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryReturnsInwardsByCustomerBetweenDates {

	public static ObservableList < SalesReturn > reportForReturnsInwardsByCustomerBetweenDates(
			TableView < SalesReturn > tableView, String sheetName, String dateFrom, String dateTo,
			BigDecimal customerId ) {
		ObservableList < SalesReturn > list = FXCollections.observableArrayList();
		ObservableList < String > columns = FXCollections.observableArrayList();
		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );
		SalesReturnsInwardsByCustomerBetweenDatesDAORetrieve
				.exportList( sheetName, columns, dateFrom, dateTo, customerId ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );
		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return SalesReturnsInwardsByCustomerBetweenDatesDAORetrieve.getWorkbook();
	}

}
