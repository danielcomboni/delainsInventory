package com.delains.ui.purchases;

import com.delains.dao.purchases.PurchasesHibernation;
import com.delains.model.purchases.Purchase;
import com.delains.ui.invoker.Refresh;

import javafx.scene.control.TableView;

public class PurchaseManipulation {

	private static void populateTableWithoutRefreshing( TableView < Purchase > tableView ) {

		if ( tableView.getItems().isEmpty() || tableView.getItems() == null ) {

			tableView.setItems( PurchasesHibernation.findAllPurchasesObservableList() );

		}

	}

	private static void populateTableRefresh( TableView < Purchase > tableView ) {

		tableView.getItems().clear();
		tableView.getItems().addAll( PurchasesHibernation.findAllPurchasesObservableListRefreshed() );

	}

	public static void populateTable( TableView < Purchase > tableView ) {
		if ( Refresh.getRefreshingDeterminant() == 1 ) {
			populateTableRefresh( tableView );
		} else {
			populateTableWithoutRefreshing( tableView );
		}
	}

//	public static void populateComboBoxSupplier( ComboBox < Supplier > comboBox ) {
//
//		if ( Refresh.getRefreshingDeterminant() == 0 ) {
//
//			comboBox.setItems( SupplierHibernation.findAllSuppliersObservableList() );
//
//			comboBox.setConverter( new StringConverter < Supplier >() {
//
//				@Override
//				public String toString( Supplier object ) {
//
//					String type = object.getSupplierName();
//
//					return type;
//				}
//
//				@Override
//				public Supplier fromString( String string ) {
//
//					Supplier type = comboBox.getItems().stream().filter( e -> e.getSupplierName().equals( string ) )
//							.findFirst().orElse( null );
//					return type;
//				}
//			} );
//
//		}
//
//		else {
//
//			comboBox.setItems( SupplierHibernation.findAllSuppliersObservableListRefreshed() );
//
//			comboBox.setConverter( new StringConverter < Supplier >() {
//
//				@Override
//				public String toString( Supplier object ) {
//
//					String type = object.getSupplierName();
//
//					return type;
//				}
//
//				@Override
//				public Supplier fromString( String string ) {
//
//					Supplier type = comboBox.getItems().stream().filter( e -> e.getSupplierName().equals( string ) )
//							.findFirst().orElse( null );
//					return type;
//				}
//			} );
//
//		}
//
//	}

//	public static void populateComboBoxItems( ComboBox < Item > comboBox ) {
//
//		if ( Refresh.getRefreshingDeterminant() == 0 ) {
//
//			comboBox.setItems( ItemHibernation.findAllItemsObservableList() );
//
//			comboBox.setConverter( new StringConverter < Item >() {
//
//				@Override
//				public String toString( Item object ) {
//
//					String type = object.getItemName().concat( " " ).concat( object.getPackageVolume().toString() )
//							.concat( "" ).concat( object.getUnitOfMeasurement() );
//
//					return type;
//				}
//
//				@Override
//				public Item fromString( String string ) {
//
//					Item type = comboBox.getItems().stream()
//							.filter( e -> e.getItemName().concat( " " ).concat( e.getPackageVolume().toString() )
//									.concat( "" ).concat( e.getUnitOfMeasurement() ).equals( string ) )
//							.findFirst().orElse( null );
//					return type;
//				}
//			} );
//
//		}
//
//		else {
//
//			comboBox.setItems( ItemHibernation.findAllItemsObservableListRefreshed() );
//
//			comboBox.setConverter( new StringConverter < Item >() {
//
//				@Override
//				public String toString( Item object ) {
//
//					String type = object.getItemName().concat( " " ).concat( object.getPackageVolume().toString() )
//							.concat( "" ).concat( object.getUnitOfMeasurement() );
//
//					return type;
//				}
//
//				@Override
//				public Item fromString( String string ) {
//
//					Item type = comboBox.getItems().stream()
//							.filter( e -> e.getItemName().concat( " " ).concat( e.getPackageVolume().toString() )
//									.concat( "" ).concat( e.getUnitOfMeasurement() ).equals( string ) )
//							.findFirst().orElse( null );
//					return type;
//				}
//			} );
//
//		}
//
//	}

}
