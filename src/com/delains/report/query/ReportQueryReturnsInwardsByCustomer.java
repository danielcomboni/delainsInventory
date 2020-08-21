package com.delains.report.query;

import java.math.BigDecimal;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.SalesReturnsInwardsByCustomerDAORetrieve;
import com.delains.model.sales.SalesReturn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryReturnsInwardsByCustomer {

	public static ObservableList < SalesReturn > reportForReturnsInwardsByCustomer( TableView < SalesReturn > tableView,
			String sheetName, BigDecimal customerId ) {
		ObservableList < SalesReturn > list = FXCollections.observableArrayList();
		ObservableList < String > columns = FXCollections.observableArrayList();
		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );
		SalesReturnsInwardsByCustomerDAORetrieve.exportList( sheetName, columns, customerId ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );
		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return SalesReturnsInwardsByCustomerDAORetrieve.getWorkbook();
	}

}
