package com.delains.report.query;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.stock.StockDAORetrieve;
import com.delains.model.stock.Stock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ReportQueryAllStock {

	public static ObservableList < Stock > reportForAllStockRegardless( TableView < Stock > tableView,
			String sheetName ) {

		ObservableList < Stock > list = FXCollections.observableArrayList();

		ObservableList < String > columns = FXCollections.observableArrayList();

		tableView.getColumns().parallelStream().forEachOrdered( c -> columns.add( c.getText() ) );

		String whereQuery = "";

		StockDAORetrieve.exportList( sheetName, columns, whereQuery ).parallelStream()
				.forEachOrdered( s -> list.add( s ) );

		return list;
	}

	public static XSSFWorkbook getWorkBook() {
		return StockDAORetrieve.getWorkbook();
	}

}
