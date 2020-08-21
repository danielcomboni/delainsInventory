package com.delains.ui.purchases;

import com.delains.dao.purchases.PurchasesClearanceHibernation;
import com.delains.model.purchases.PurchaseClearance;
import com.delains.ui.invoker.Refresh;

import javafx.scene.control.TableView;

public class PurchasesClearanceManipulation {

	private static void populateTableWithoutRefreshing( TableView < PurchaseClearance > tableView ) {

		if ( tableView.getItems().isEmpty() || tableView.getItems() == null ) {

			tableView.setItems( PurchasesClearanceHibernation.findAllPurchasesClearancesWithoutRefreshing() );

		}

	}

	private static void populateTableRefresh( TableView < PurchaseClearance > tableView ) {

		tableView.getItems().clear();
		tableView.getItems().addAll( PurchasesClearanceHibernation.findAllPurchasesClearancesWithRefreshing() );

	}

	public static void populateTable( TableView < PurchaseClearance > tableView ) {
		if ( Refresh.getRefreshingDeterminant() == 1 ) {
			populateTableRefresh( tableView );
		} else {
			populateTableWithoutRefreshing( tableView );
		}
	}

}
