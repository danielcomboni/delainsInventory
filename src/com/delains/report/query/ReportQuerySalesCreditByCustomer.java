package com.delains.report.query;

import java.math.BigDecimal;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.ReportAllSalesCreditByCustomerRetrieve;
import com.delains.model.pos.POS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQuerySalesCreditByCustomer {

	public static ObservableList < POS > reportForAllSalesCreditByCustomer( TableView < POS > tableView,
			String sheetName, BigDecimal customerId ) {
		ObservableList < POS > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		// String whereQuery = date;

		ReportAllSalesCreditByCustomerRetrieve.exportList( sheetName, columns, customerId ).entrySet().parallelStream()
				.forEachOrdered( s -> list.add( s.getValue() ) );

		System.out.println( "list by date: " + list );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return ReportAllSalesCreditByCustomerRetrieve.getWorkbook();
	}

}
