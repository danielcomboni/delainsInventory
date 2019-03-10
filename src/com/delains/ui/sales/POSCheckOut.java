package com.delains.ui.sales;

import java.math.BigDecimal;
import java.util.Map.Entry;

import com.delains.model.customers.Customer;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class POSCheckOut {

	public static void getDetailsFromCartForPrinting( ObservableMap < Item, POS > map, BigDecimal totalCost,
			BigDecimal amountPaid, BigDecimal change, BigDecimal balance ) {

	}

	public static ObservableMap < Item, POS > breakingDownPosTableForBatchEnchancement(
			ObservableMap < Item, POS > mapForBatchEnchancement, BigDecimal amountPaid, BigDecimal totalCost,
			BigDecimal change, BigDecimal balance, boolean credit, Customer customer ) {

		ObservableMap < Item, POS > map = FXCollections.observableHashMap();

		for ( Entry < Item, POS > p : mapForBatchEnchancement.entrySet() ) {

			POS pos = new POS();

			pos.setAmountPaid( amountPaid );
			pos.setBarCode( p.getKey().getItemDescription() );
			pos.setCost( p.getValue().getCost() );
			pos.setCredit( credit );
			pos.setCustomerId( customer );
			// pos.setDiscountAllowed(discount);
			pos.setItemId( p.getValue().getItemId() );
			pos.setPrice( p.getValue().getPrice() );
			pos.setPricing( p.getValue().getPricing() );
			pos.setQuantity( p.getValue().getQuantity() );
			pos.setTotalCost( totalCost );

			p.getValue().setChange( change );
			pos.setChange( p.getValue().getChange() );

			p.getValue().setBalanceToBePaidByCustomer( balance );
			pos.setBalanceToBePaidByCustomer( p.getValue().getBalanceToBePaidByCustomer() );

			map.put( p.getKey(), pos );

		}

		System.out.println( "map in POSCheckOut: " + map );

		return map;

	}

}
