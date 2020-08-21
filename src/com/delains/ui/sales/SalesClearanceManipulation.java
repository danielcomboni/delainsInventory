package com.delains.ui.sales;

import com.delains.model.sales.SalesClearance;
import com.delains.ui.invoker.Refresh;

import javafx.scene.control.TableView;

public class SalesClearanceManipulation {

	private static void populateTableWithoutRefreshing( TableView < SalesClearance > tableView ) {

		if ( tableView.getItems().isEmpty() || tableView.getItems() == null ) {

			tableView.setItems( SalesClearanceHibernation.findAllSalesClearancesWithoutRefreshing() );

		}

	}

	private static void populateTableRefresh( TableView < SalesClearance > tableView ) {

		tableView.getItems().clear();
		tableView.getItems().addAll( SalesClearanceHibernation.findAllSalesClearancesWithRefreshing() );

	}

	public static void populateTable( TableView < SalesClearance > tableView ) {
		if ( Refresh.getRefreshingDeterminant() == 1 ) {
			populateTableRefresh( tableView );
		} else {
			populateTableWithoutRefreshing( tableView );
		}
	}

}
