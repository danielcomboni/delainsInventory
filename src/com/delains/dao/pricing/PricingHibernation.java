package com.delains.dao.pricing;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.pricing.Pricing;
import com.delains.ui.history.AuditHistoryManipulation;

import javafx.collections.ObservableList;

public class PricingHibernation {

	private static ObservableList < Pricing > pricings;

	public static ObservableList < Pricing > getPricings() {
		return pricings;
	}

	public static void setPricings( ObservableList < Pricing > pricings ) {
		PricingHibernation.pricings = pricings;
	}

	public static void newPricing( Pricing pricing ) {

		if ( mappingItemIDsAsIDOfPrice().containsKey( pricing.getItemId().getId() ) ) {

			Pricing pricing2 = mappingItemIDsAsIDOfPrice().get( pricing.getItemId().getId() );

			updatePricing( pricing, pricing2.getId() );

			AuditHistoryHibernation.auditValues(
					"item price edited: " + pricing.getItemId().getItemName() + " : " + pricing.getPrice(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

		} else {
			PricingDAO.newPricing( pricing );
			AuditHistoryHibernation.auditValues(
					"new item price set: " + pricing.getItemId().getItemName() + " : " + pricing.getPrice(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

		}

	}

	public static void updatePricing( Pricing pricing, BigDecimal idOfPricing ) {
		PricingDAO.updatePricing( pricing, idOfPricing );
	}

	public static void deletePricing( BigDecimal id ) {
		PricingDAO.deletePricing( id );
	}

	public static ObservableList < Pricing > findAllPricingsObservableList() {
		if ( getPricings() == null ) {
			setPricings( PricingDAO.changeListUtilToListObservable() );
		}
		return getPricings();
	}

	public static ObservableList < Pricing > findAllPricingsObservableListRefresh() {
		setPricings( PricingDAO.changeListUtilToListObservable() );
		return getPricings();
	}

	public static Map < BigDecimal, Pricing > mapOfPricingsToThierId() {
		Map < BigDecimal, Pricing > map = new LinkedHashMap <>();
		for ( int i = 0; i < findAllPricingsObservableList().size(); i++ ) {
			Pricing type = findAllPricingsObservableList().get( i );
			map.put( type.getId(), type );
		}
		return map;
	}

	public static Map < BigDecimal, Pricing > mappingItemIDsAsIDOfPrice() {
		Map < BigDecimal, Pricing > map = new LinkedHashMap <>();
		for ( int i = 0; i < findAllPricingsObservableList().size(); i++ ) {
			Pricing type = findAllPricingsObservableList().get( i );
			map.put( type.getItemId().getId(), type );
		}
		return map;
	}

}
