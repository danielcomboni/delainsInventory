package com.delains.ui.sales;

import java.math.BigDecimal;

import com.delains.model.customers.Customer;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;
import com.jfoenix.controls.JFXCheckBox;

import javafx.collections.ObservableMap;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class POSButtonListening {

	public static void reduce(POS pos, ObservableMap<Item, POS> posValuesForTheTable) {

		POS pos2 = pos;

		BigDecimal qty = pos.getQuantity();
		BigDecimal price = pos.getPricing().getPrice();

		Item item = pos.getItemId();

		if (qty.doubleValue() >= 2) {

			qty = qty.subtract(BigDecimal.ONE);
			pos2.setQuantity(qty);

			pos2.setCost(price.multiply(pos2.getQuantity()));

			posValuesForTheTable.put(item, pos2);

		}

		if ((qty.doubleValue() > 0 && qty.doubleValue() < 1)) {

			posValuesForTheTable.remove(item);

		}

	}

	public static void increase(POS pos, ObservableMap<Item, POS> posValuesForTheTable) {

		POS pos2 = pos;

		BigDecimal qty = pos.getQuantity();
		BigDecimal price = pos.getPricing().getPrice();

		Item item = pos.getItemId();

		if (qty.doubleValue() > 0) {

			qty = qty.add(BigDecimal.ONE);

			pos2.setQuantity(qty);

			pos2.setCost(price.multiply(pos2.getQuantity()));

			posValuesForTheTable.put(item, pos2);

		}

	}

	public static void remove(POS pos, ObservableMap<Item, POS> posValuesForTheTable) {

		Item item = pos.getItemId();

		posValuesForTheTable.remove(item);
	}

	public static void cancelPOS(ObservableMap<Item, POS> posValuesForTheTable, TextField fieldItem,
			ComboBox<Item> comboBoxItem, TextField fieldQuantity, JFXCheckBox checkBoxCredit, TextField fieldAmountPaid,
			ComboBox<Customer> comboBoxCustomer, Label labelTotalCost, Label labelChange,
			Label labelBalanceToBePaidByCustomer) {

		posValuesForTheTable.clear();

		fieldAmountPaid.clear();
		fieldItem.clear();
		fieldQuantity.clear();

		checkBoxCredit.setSelected(false);
		comboBoxCustomer.getSelectionModel().clearSelection();
		comboBoxItem.getSelectionModel().clearSelection();

		labelBalanceToBePaidByCustomer.setText(null);
		labelChange.setText(null);
		labelTotalCost.setText(null);

	}

}
