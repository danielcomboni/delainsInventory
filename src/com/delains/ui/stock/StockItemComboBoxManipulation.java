package com.delains.ui.stock;

import com.delains.dao.item.ItemHibernation;
import com.delains.model.items.Item;
import com.delains.ui.combobox.ComboBoxGeneric;

import javafx.scene.control.ComboBox;

public class StockItemComboBoxManipulation {

	public StockItemComboBoxManipulation() {

Item item = new Item();
		this.comboBoxGeneric = new ComboBoxGeneric < Item >( item );
	}

	private ComboBoxGeneric < Item > comboBoxGeneric;

	public ComboBoxGeneric < Item > getComboBoxGeneric() {
		return comboBoxGeneric;
	}

	public void setComboBoxGeneric( ComboBoxGeneric < Item > comboBoxGeneric ) {
		this.comboBoxGeneric = comboBoxGeneric;
	}

	public ComboBox < Item > getComboBox() {
		Item item = new Item();

		comboBoxGeneric = new ComboBoxGeneric <>( item );

		return comboBoxGeneric.getComboBox();

	}

	public void populateComboBoxItem() {

		Item item = new Item();

comboBoxGeneric.populateComboBox( item, ItemHibernation.findAllItemsObservableListRefreshed(), "getItemName" );
	}

}
