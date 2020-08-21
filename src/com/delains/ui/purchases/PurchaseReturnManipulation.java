package com.delains.ui.purchases;

import com.delains.dao.purchases.PurchaseReturnHibernation;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.ui.invoker.Refresh;

import javafx.scene.control.TableView;

public class PurchaseReturnManipulation {

	private static void populateTableWithoutRefreshing( TableView < PurchaseReturn > tableView ) {

		if ( tableView.getItems().isEmpty() || tableView.getItems() == null ) {

			tableView.setItems( PurchaseReturnHibernation.findAllPurchaseReturnsObservableList() );

		}

	}

	private static void populateTableRefresh( TableView < PurchaseReturn > tableView ) {

		tableView.getItems().clear();
		tableView.getItems().addAll( PurchaseReturnHibernation.findAllPurchaseReturnsObservableListRefreshed() );

	}

	public static void populateTable( TableView < PurchaseReturn > tableView ) {
		if ( Refresh.getRefreshingDeterminant() == 1 ) {
			populateTableRefresh( tableView );
		} else {
			populateTableWithoutRefreshing( tableView );
		}
	}

}
