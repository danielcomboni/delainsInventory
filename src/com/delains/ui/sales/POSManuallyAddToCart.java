package com.delains.ui.sales;

import com.delains.model.items.Item;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class POSManuallyAddToCart {

	// private ComboBox < Item > comboBoxItem;
	// private TextField fieldQuantity;
	// private TableView < POS > tableView;

	private static String barcode;

	public static String getBarcode() {
		return barcode;
	}

	public static void setBarcode( String barcode ) {
		POSManuallyAddToCart.barcode = barcode;
	}

	public String whenItemChosenFromComboBox( ComboBox < Item > comboBox, TextField fieldBarcode ) {

		comboBox.getSelectionModel().selectedItemProperty().addListener( e -> {
			String bar = null;
			Item item = comboBox.getSelectionModel().getSelectedItem();
			bar = item.getBarcode();
setBarcode( bar );

			fieldBarcode.setText( bar );

			fieldBarcode.requestFocus();

			fieldBarcode
					.fireEvent( new KeyEvent( KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, true, true, true, true ) );

			comboBox.getSelectionModel().clearSelection();

		} );

		
		return getBarcode();

	}

	public POSManuallyAddToCart() {

	}

}
