package com.delains.ui.stock;

import java.math.BigDecimal;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.stock.StockWarningPointHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.stock.StockWarningPoint;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.ui.sales.POSFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class StockQuantityWarningPointDialog {

	private StageForAllPopUps stageForAllPopUps;
	private BorderPane borderPane;
	private JFXButton buttonSave;
	private JFXButton buttonCancel;

	private Label labelItem;
	private static ComboBox < Item > comboBox;

	private Label labelQuantityLimitPoint;
	private TextField fieldQuantityLimitpoint;

	private GridPane gridPane;

	private VBox vBox;

	public StockQuantityWarningPointDialog() {

		gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		borderPane = new BorderPane();

		tableView = new TableView <>();
		setTableView( StockWarningPointTable.getTableView() );
		StockWarningPointTable.populateTableWithRefreshing();

		vBox = new VBox();
		vBox.getChildren().add( gridPane );
		vBox.getChildren().add( tableView );

		borderPane.setCenter( vBox );

		labelItem = new Label( "item" );
		gridPane.add( labelItem, 0, 0 );
		comboBox = new ComboBox <>();
		gridPane.add( comboBox, 1, 0 );

		labelQuantityLimitPoint = new Label( "lowest quantity" );
		gridPane.add( labelQuantityLimitPoint, 0, 1 );
		fieldQuantityLimitpoint = new TextField();
		gridPane.add( fieldQuantityLimitpoint, 1, 1 );

		HBox box = new HBox( 10 );
		buttonSave = new JFXButton( "save" );
		box.getChildren().add( buttonSave );

		buttonCancel = new JFXButton( "cancel" );
		box.getChildren().add( buttonCancel );

		gridPane.add( box, 1, 2 );

		stageForAllPopUps = new StageForAllPopUps( borderPane, "set warning point for qty in stock" );

populateComboBox();

		buttonCancel.setOnAction( e -> {
			stageForAllPopUps.close();
		} );

		buttonSave.setOnAction( e -> {
			saveNewLimit();
		} );

		comboBoxselection();

		populateTableWithoutRefreshing();

	}

	private static TableView < StockWarningPoint > tableView;

	public TableView < StockWarningPoint > getTableView() {
		return tableView;
	}

	public void setTableView( TableView < StockWarningPoint > tableView ) {
		StockQuantityWarningPointDialog.tableView = tableView;
	}

	public static ComboBox < Item > getComboBox() {
		return comboBox;
	}

	public static void setComboBox( ComboBox < Item > comboBox ) {
		StockQuantityWarningPointDialog.comboBox = comboBox;
	}

	public void showPopUpToSetLimitInStock() {

		this.stageForAllPopUps.showAndWait();
	}

	public void populateComboBox() {
comboBox.getItems().clear();
		comboBox.getItems().addAll( ItemHibernation.findAllItemsObservableList() );

        POSFrame.setConverterItemComboBox(comboBox);

        comboBox.valueProperty().addListener( ( obs, oldVal, newVal ) -> {
			if ( newVal != null ) {
}
		} );

	}

	private Item item;

	private void comboBoxselection() {
		comboBox.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
item = newVal;
		} );
	}

	private static void populateTableWithoutRefreshing() {

		tableView.getItems().clear();
		tableView.getItems().addAll( StockWarningPointHibernation.findAllStockWarningPointsObservableListRefreshed() );

	}

	private void saveNewLimit() {

		new StageForAlerts();

		if ( fieldQuantityLimitpoint.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the lowest limit can not be empty" );
			return;
		}

		String qtyStr = NumberFormatting.testNumberCorrectness( fieldQuantityLimitpoint.getText().trim() );

		if ( NumberFormatting.isNumberCorrect() == false ) {
			StageForAlerts.inform( "alert", "please check the number format" );
			return;
		}

		if ( item == null ) {

			StageForAlerts.inform( "alert", "select an item to give a quantity warning point" );
			return;

		}

		BigDecimal qty = new BigDecimal( qtyStr );

		StockWarningPoint point = new StockWarningPoint();
		point.setItemId( item );
		point.setQuantityLimit( qty );

		if ( ( !StockWarningPointHibernation.itemIDsMappedToThierStockWarningPoints().isEmpty()
				&& StockWarningPointHibernation.itemIDsMappedToThierStockWarningPoints().values() != null )
				&& StockWarningPointHibernation.itemIDsMappedToThierStockWarningPoints()
						.containsKey( point.getItemId().getId() ) ) {
			StockWarningPointHibernation.updateStockWarningPoint( point, item.getId() );
		} else {
			StockWarningPointHibernation.newStockWarningPoint( point );
		}

		populateTableWithoutRefreshing();

	}

}
