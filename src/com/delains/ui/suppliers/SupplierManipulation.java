package com.delains.ui.suppliers;

import com.delains.dao.suppliers.SupplierHibernation;
import com.delains.dao.suppliers.SupplierTypeHibernation;
import com.delains.model.suppliers.Supplier;
import com.delains.model.suppliers.SupplierType;
import com.delains.ui.invoker.Refresh;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

public class SupplierManipulation {

	private static void populateTableWithoutRefreshing(TableView<Supplier> tableView) {

		if (tableView.getItems().isEmpty() || tableView.getItems() == null) {

			tableView.setItems(SupplierHibernation.findAllSuppliersObservableList());

		}

	}

	private static void populateTableRefresh(TableView<Supplier> tableView) {

		tableView.getItems().clear();
		tableView.getItems().addAll(SupplierHibernation.findAllSuppliersObservableListRefreshed());

	}

	public static void populateTable(TableView<Supplier> tableView) {
		if (Refresh.getRefreshingDeterminant() == 1) {
			populateTableRefresh(tableView);
		} else {
			populateTableWithoutRefreshing(tableView);
		}
	}

	public static void populateComboBoxSupplierType(ComboBox<SupplierType> comboBox) {

		if (Refresh.getRefreshingDeterminant() == 0) {

			comboBox.setItems(SupplierTypeHibernation.findAllSupplierTypesObservableList());

			comboBox.setConverter(new StringConverter<SupplierType>() {

				@Override
				public String toString(SupplierType object) {

					String type = object.getType();

					return type;
				}

				@Override
				public SupplierType fromString(String string) {

					SupplierType type = comboBox.getItems().stream().filter(e -> e.getType().equals(string)).findFirst()
							.orElse(null);
					return type;
				}
			});

		}

		else {

			comboBox.setItems(SupplierTypeHibernation.findAllSupplierTypesObservableListRefreshed());

			comboBox.setConverter(new StringConverter<SupplierType>() {

				@Override
				public String toString(SupplierType object) {

					String type = object.getType();

					return type;
				}

				@Override
				public SupplierType fromString(String string) {

					SupplierType type = comboBox.getItems().stream().filter(e -> e.getType().equals(string)).findFirst()
							.orElse(null);
					return type;
				}
			});

		}

		// comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
		// if (newVal != null) {
		// // System.out.println("selected:..." + newVal.getType() + " ID:..." +
		// // newVal.getId());
		// }
		// });

	}

}
