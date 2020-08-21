package com.delains.report.query;

import java.math.BigDecimal;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.ReportAllSalesCreditByCustomerBetweenDatesRetrieve;
import com.delains.model.pos.POS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQuerySalesCreditByCustomerBetweenDates {

	public static ObservableList < POS > reportForAllSalesCreditByCustomerBetweenDates( TableView < POS > tableView,
			String sheetName, String dateFrom, String dateTo, BigDecimal customerId ) {
		ObservableList < POS > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		// String whereQuery = date;

		ReportAllSalesCreditByCustomerBetweenDatesRetrieve
				.exportList( sheetName, columns, dateFrom, dateTo, customerId ).entrySet().parallelStream()
				.forEachOrdered( s -> list.add( s.getValue() ) );

return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return ReportAllSalesCreditByCustomerBetweenDatesRetrieve.getWorkbook();
	}

}
