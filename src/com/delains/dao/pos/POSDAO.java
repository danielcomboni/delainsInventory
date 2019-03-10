package com.delains.dao.pos;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.pricing.PricingHibernation;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;
import com.delains.model.pricing.Pricing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class POSDAO {

	private static Map < BigDecimal, Item > itemsHibernated;

	private static BigDecimal unitCost;

	private static Item item;

	private static Pricing pricing;

	private static POS currentPOS;

	public static Map < BigDecimal, Item > getItemsHibernated() {
		return itemsHibernated;
	}

	public static void setItemsHibernated( Map < BigDecimal, Item > itemsHibernated ) {
		POSDAO.itemsHibernated = itemsHibernated;
	}

	public static BigDecimal getUnitCost() {
		return unitCost;
	}

	public static void setUnitCost( BigDecimal unitCost ) {
		POSDAO.unitCost = unitCost;
	}

	public static Item getItem() {
		return item;
	}

	public static void setItem( Item item ) {
		POSDAO.item = item;
	}

	public static Pricing getPricing() {
		return pricing;
	}

	public static void setPricing( Pricing pricing ) {
		POSDAO.pricing = pricing;
	}

	public static POS getCurrentPOS() {
		return currentPOS;
	}

	public static void setCurrentPOS( POS currentPOS ) {
		POSDAO.currentPOS = currentPOS;
	}

	public static Map < BigDecimal, Item > allHibernatedItems() {
		if ( getItemsHibernated() == null ) {
			setItemsHibernated( ItemHibernation.mapOfItemsToThierId() );
		}
		return getItemsHibernated();
	}

	public static Map < BigDecimal, Pricing > itemsAndPricesMapped() {
		return PricingHibernation.mappingItemIDsAsIDOfPrice();
	}

	public static Item getItemByBarcode( String barcode ) {
		Item item = ItemHibernation.barcodesMappedToThierItems().get( barcode );
		POSDAO.setItem( item );
		return item;
	}

	public static Pricing getPricingOfTheItem( Item item ) {
		Pricing pricing = PricingHibernation.mappingItemIDsAsIDOfPrice().get( item.getId() );
		POSDAO.setPricing( pricing );
		POSDAO.setUnitCost( POSDAO.getPricing().getPrice() );
		return pricing;
	}

	public static POS addToPOS( BigDecimal quantity ) {

		POS pos = new POS();

		pos.setBarCode( POSDAO.getItem().getBarcode() );
		pos.setPricing( POSDAO.getPricing() );
		pos.setCost( pos.getPricing().getPrice() );
		pos.setQuantity( quantity );

		pos.setCost( quantity.multiply( pos.getPricing().getPrice() ) );
		pos.setItemId( POSDAO.getItem() );

		POSDAO.setCurrentPOS( pos );

		return pos;

	}

	private static ObservableMap < Item, POS > posValuesForTheTable;

	public static ObservableMap < Item, POS > getPosValuesForTheTable() {
		return posValuesForTheTable;
	}

	public static void setPosValuesForTheTable( ObservableMap < Item, POS > posValuesForTheTable ) {
		POSDAO.posValuesForTheTable = posValuesForTheTable;
	}

	private ObservableList < POS > observableList;

	public ObservableList < POS > getObservableList() {
		return observableList;
	}

	public void setObservableList( ObservableList < POS > observableList ) {
		this.observableList = observableList;
	}

	public static ObservableList < POS > listForPOSTable() {
		ObservableList < POS > observableList = FXCollections.observableArrayList();

		if ( getPosValuesForTheTable() != null ) {
			for ( Entry < Item, POS > pos : getPosValuesForTheTable().entrySet() ) {
				observableList.add( pos.getValue() );
			}
		}

		return observableList;

	}

	public static BigDecimal totalCostCalculated() {

		BigDecimal total = BigDecimal.ZERO;
		for ( int i = 0; i < POSDAO.listForPOSTable().size(); i++ ) {
			total = total.add( listForPOSTable().get( i ).getCost() );
		}
		return total;

	}

	// public static ObservableMap<Item, POS> posValuesToPopulatePOSTable() {
	//
	// if (getPosValuesForTheTable() == null) {
	// ObservableMap<Item, POS> poses = FXCollections.observableArrayList();
	// poses.add(POSDAO.getCurrentPOS());
	// POSDAO.setPosValuesForTheTable(poses);
	// }
	//
	// return POSDAO.getPosValuesForTheTable();
	// }

}
