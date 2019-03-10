package com.delains.ui.suppliers;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.suppliers.SupplierTypeHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.suppliers.SupplierType;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SupplierTypeTabInAccordion extends VBox {

	private GridPane gridPane;
	private JFXButton buttonAddNew;
	private JFXButton buttonSelectForEditing;
	private JFXButton buttonDelete;

	private SupplierTypeFrame frame;

	public SupplierTypeTabInAccordion() {
		// TODO Auto-generated constructor stub

		gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonAddNew = new JFXButton( "set new supplier type" );
		gridPane.add( buttonAddNew, 0, 0 );

		buttonSelectForEditing = new JFXButton( "Edit supplier type" );
		gridPane.add( buttonSelectForEditing, 0, 1 );

		buttonDelete = new JFXButton( "Delete supplier type" );
		gridPane.add( buttonDelete, 0, 2 );

		this.getChildren().add( gridPane );

		frame = new SupplierTypeFrame();

		this.buttonAddNew.setOnAction( e -> {

			newType();

		} );

		buttonSelectForEditing.setOnAction( e -> {
			chooseForUpdate();
		} );

		buttonDelete.setOnAction( e -> {
			deleteSupplierType();
		} );

	}

	private void newType() {

		frame.newSupplierType();
	}

	private void chooseForUpdate() {
		new StageForAlerts();
		int row = SupplierTypeFrame.getTableView().getSelectionModel().getSelectedIndex();

		SupplierType type = SupplierTypeFrame.getTableView().getSelectionModel().getSelectedItem();

		if ( row < 0 ) {
			StageForAlerts.inform( "alert", "please choose a row to edit" );
			return;
		}
		frame.chooseTypeForEditing( type );
	}

	private void deleteSupplierType() {

		new StageForAlerts();

		int row = SupplierTypeFrame.getTableView().getSelectionModel().getSelectedIndex();

		SupplierType type = SupplierTypeFrame.getTableView().getSelectionModel().getSelectedItem();

		if ( row < 0 ) {
			StageForAlerts.inform( "alert", "please choose a row to delete" );
			return;
		}

		StageForAlerts.discontinue( "confirm", "are you sure you want to delete this supplier type permanently?" );

		if ( StageForAlerts.isDecide() == true ) {

			SupplierTypeHibernation.deleteSupplierType( type.getId() );

			Refresh.setRefreshingDeterminant( 1 );
			SupplierTypeManipulation.populateUserTable( SupplierTypeFrame.getTableView() );
			// SupplierManipulation.populateComboBoxSupplierType(SupplierFrame.getTheComboBoxSupplierType());
			Refresh.setRefreshingDeterminant( 0 );

			AuditHistoryHibernation.auditValues( "supplier type deleted: ", UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

		}

	}

}
