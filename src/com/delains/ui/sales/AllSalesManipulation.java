package com.delains.ui.sales;

import com.delains.dao.pos.POSHibernation;
import com.delains.model.pos.POS;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class AllSalesManipulation {

	public static ObservableList<POS> data = POSHibernation.findAllPOSesObservableListRefreshed();

//	public static void populateAllSalesTable(TableView<POS> tableView) {
//
//		if (tableView.getItems().isEmpty() || tableView.getItems() == null) {
//
//			tableView.setItems(POSHibernation.findAllPOSesObservableList());
//
//		}
//
//	}
//
//	public static void populateAllSalesTableRefresh(TableView<POS> tableView) {
//
//		tableView.getItems().clear();
//		tableView.getItems().addAll(POSHibernation.findAllPOSesObservableListRefreshed());
//
//	}

}
