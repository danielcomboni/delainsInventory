package com.delains.ui.suppliers;

import com.delains.dao.suppliers.SupplierTypeHibernation;
import com.delains.model.suppliers.SupplierType;
import com.delains.ui.invoker.Refresh;

import javafx.scene.control.TableView;

public class SupplierTypeManipulation {

	private static void populateUsersTableWithoutRefreshing(TableView<SupplierType> tableView) {

		if (tableView.getItems().isEmpty() || tableView.getItems() == null) {

			tableView.setItems(SupplierTypeHibernation.findAllSupplierTypesObservableList());

		}

	}

	private static void populateAllSalesTableRefresh(TableView<SupplierType> tableView) {

		tableView.getItems().clear();
		tableView.getItems().addAll(SupplierTypeHibernation.findAllSupplierTypesObservableListRefreshed());

	}

	public static void populateUserTable(TableView<SupplierType> tableView) {
		if (Refresh.getRefreshingDeterminant() == 1) {
			populateAllSalesTableRefresh(tableView);
		} else {
			populateUsersTableWithoutRefreshing(tableView);
		}
	}

}
