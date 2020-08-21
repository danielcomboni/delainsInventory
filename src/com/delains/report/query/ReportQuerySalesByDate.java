package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.pos.ReportAllSalesByDateRetrieve;
import com.delains.model.pos.POS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQuerySalesByDate {

	public static ObservableList < POS > reportForAllSalesByDate( TableView < POS > tableView, String sheetName,
			String date ) {
		ObservableList < POS > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		String whereQuery = date;

		ReportAllSalesByDateRetrieve.exportList( sheetName, columns, whereQuery ).entrySet().parallelStream()
				.forEachOrdered( s -> list.add( s.getValue() ) );

return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return ReportAllSalesByDateRetrieve.getWorkbook();
	}

}
