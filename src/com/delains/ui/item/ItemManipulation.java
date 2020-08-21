package com.delains.ui.item;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.table.AddColumnsToTable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.math.BigDecimal;

public class ItemManipulation {

	private static AddColumnsToTable < Item > addColumnsToTable;


	public static ObservableList<Item> data = ItemHibernation.findAllItemsObservableListRefreshed();


	public static TableView < Item > getTableView() {

		Item item = new Item();
		addColumnsToTable = new AddColumnsToTable <>( item );

		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "item" ), "item" );
		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "unit\nof\nmeasurement" ), "unit" );
		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "package" ), "package" );
		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "package\nvolume" ), "volume" );

		addButtonToTable( addColumnsToTable.getTableViewGeneric().getTableView() );

		return addColumnsToTable.getTableViewGeneric().getTableView();

	}


	private static TableColumn < Item, String > setColumnValueFactory( TableColumn < Item, String > column,
																	   String decideValue ) {

		column.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Item, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Item, String > param ) {

						Item item = param.getValue();

						StringProperty val = null;

						if ( decideValue.equalsIgnoreCase( "item" ) ) {
							val = item.itemNameProperty();
						}

						if ( decideValue.equalsIgnoreCase( "unit" ) ) {

							val = item.unitOfMeasurementProperty();

						}

						if ( decideValue.equalsIgnoreCase( "package" ) ) {

							val = item.packageNameProperty();

						}

						if ( decideValue.equalsIgnoreCase( "volume" ) ) {

							ObjectProperty<BigDecimal> vol = item.packageVolumeProperty();
							String volStr =  NumberFormatting.formatToEnglish( vol.getValue().toString() );
							val = new SimpleStringProperty(volStr);

						}

//						SimpleStringProperty simpleStringProperty = new SimpleStringProperty( val );
//
//						StringProperty property = simpleStringProperty;

						return val;
					}
				} );

		return column;

	}

	private static void addButtonToTable( TableView < Item > tableView ) {
		TableColumn < Item, Void > colBtn = new TableColumn <>( "Description" );

		Callback < TableColumn < Item, Void >, TableCell < Item, Void > > cellFactory = new Callback < TableColumn < Item, Void >, TableCell < Item, Void > >() {
			@Override
			public TableCell < Item, Void > call( final TableColumn < Item, Void > param ) {
				final TableCell < Item, Void > cell = new TableCell < Item, Void >() {

					private final Button btn = new Button( "desc" );

					{
						btn.setOnAction( ( ActionEvent event ) -> {
							Item data = getTableView().getItems().get( getIndex() );
							new StageForAlerts();
							StageForAlerts.inform( "description of item", data.getItemDescription() );

						} );
					}

					@Override
					public void updateItem( Void item, boolean empty ) {
						super.updateItem( item, empty );
						if ( empty ) {
							setGraphic( null );
						} else {
							setGraphic( btn );
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory( cellFactory );

		tableView.getColumns().add( colBtn );

	}

	private static Item itemPrev;

	public static Item getItemPrev() {
		return itemPrev;
	}

	public static void setItemPrev( Item itemPrev ) {
		ItemManipulation.itemPrev = itemPrev;
	}

	public static Item clickRow() {

		getTableView().setRowFactory( tr -> {

			TableRow < Item > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {

					Item purchase = row.getItem();

					// this.setIdOfSupplier(s.getId());

					itemPrev = purchase;

				}
			} );

			return row;
		} );
		return itemPrev;
	}


}
