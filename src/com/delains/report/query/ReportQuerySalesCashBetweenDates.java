package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.ReportAllSalesCashBetweenDatesRetrieve;
import com.delains.model.pos.POS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQuerySalesCashBetweenDates {

	public static ObservableList < POS > reportForAllSalesCashBetweenDates( TableView < POS > tableView,
			String sheetName, String dateFrom, String dateTo ) {
		ObservableList < POS > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		// String whereQuery = date;

		ReportAllSalesCashBetweenDatesRetrieve.exportList( sheetName, columns, dateFrom, dateTo ).entrySet()
				.parallelStream().forEachOrdered( s -> list.add( s.getValue() ) );

		System.out.println( "list by date: " + list );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return ReportAllSalesCashBetweenDatesRetrieve.getWorkbook();
	}

}
