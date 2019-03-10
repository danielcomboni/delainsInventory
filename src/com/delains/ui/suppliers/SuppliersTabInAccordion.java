package com.delains.ui.suppliers;

import com.delains.model.suppliers.Supplier;
import com.delains.ui.invoker.StageForAlerts;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SuppliersTabInAccordion extends VBox {

	private GridPane gridPane;
	private JFXButton buttonAddNew;
	private JFXButton buttonSelectForEditing;
	private JFXButton buttonDelete;

	public SuppliersTabInAccordion() {

		gridPane = new GridPane();
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setPadding(new Insets(5, 5, 5, 5));

		buttonAddNew = new JFXButton("Add new supplier");
		gridPane.add(buttonAddNew, 0, 0);

		buttonSelectForEditing = new JFXButton("Edit supplier info");
		gridPane.add(buttonSelectForEditing, 0, 1);

		buttonDelete = new JFXButton("Delete supplier");
		gridPane.add(buttonDelete, 0, 2);

		this.getChildren().add(gridPane);

		frame = new SupplierFrame();

		this.buttonAddNew.setOnAction(e -> {
			popUpNewSupplierStage();
		});

		buttonSelectForEditing.setOnAction(e -> {
			chooseForUpdate();
		});

		buttonDelete.setOnAction(e -> {
			delete();
		});

	}

	private SupplierFrame frame;

	private void popUpNewSupplierStage() {
		frame.newSupplier();
	}

	private void chooseForUpdate() {

		new StageForAlerts();

		int row = SupplierFrame.getTableViewSupplier().getSelectionModel().getSelectedIndex();
		Supplier supplier = SupplierFrame.getTableViewSupplier().getSelectionModel().getSelectedItem();

		System.out.println("row; ==" + row);

		if (row < 0) {

			StageForAlerts.inform("alert", "please choose a row to be edited");
			return;

		}

		frame.updateSupplier(supplier);
	}

	private void delete() {
		new StageForAlerts();

		int row = SupplierFrame.getTableViewSupplier().getSelectionModel().getSelectedIndex();

		if (row < 0) {

			StageForAlerts.inform("alert", "please choose a row to be deleted");
			return;

		}

		Supplier s = SupplierFrame.getTableViewSupplier().getSelectionModel().getSelectedItem();

		// System.out.println("id called: " + frame.getIdOfSupplier());

		frame.deleteSupplier(s.getId());

	}

}
