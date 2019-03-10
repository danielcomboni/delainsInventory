package com.delains.ui.sales;

import com.delains.dao.pos.SalesReturnHibernation;
import com.delains.model.sales.SalesReturn;
import com.delains.ui.invoker.Refresh;

import javafx.scene.control.TableView;

public class SalesReturnManipulation {

	private static void populateTableWithoutRefreshing(TableView<SalesReturn> tableView) {

		if (tableView.getItems().isEmpty() || tableView.getItems() == null) {

			tableView.setItems(SalesReturnHibernation.findAllSalesReturnsWithoutRefreshing());

		}

	}

	private static void populateTableRefresh(TableView<SalesReturn> tableView) {

		tableView.getItems().clear();
		tableView.getItems().addAll(SalesReturnHibernation.findAllSalesReturnsWithRefreshing());

	}

	public static void populateTable(TableView<SalesReturn> tableView) {
		if (Refresh.getRefreshingDeterminant() == 1) {
			populateTableRefresh(tableView);
		} else {
			populateTableWithoutRefreshing(tableView);
		}
	}

	
}
