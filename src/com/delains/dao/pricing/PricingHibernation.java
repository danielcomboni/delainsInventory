package com.delains.dao.pricing;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.history.AuditHistory;
import com.delains.model.pricing.Pricing;
import com.delains.ui.history.AuditHistoryData;

import com.delains.ui.pricing.PricingData;
import javafx.collections.ObservableList;

public class PricingHibernation {

	private static ObservableList < Pricing > pricing;

	public static ObservableList < Pricing > getPricing() {
		return pricing;
	}

	private static void setPricing(ObservableList<Pricing> pricing) {
		PricingHibernation.pricing = pricing;
	}

	public static void newPricing( Pricing pricing ) {

		if ( mappingItemIDsAsIDOfPrice().containsKey( pricing.getItemId().getId() ) ) {

			Pricing pricing2 = mappingItemIDsAsIDOfPrice().get( pricing.getItemId().getId() );


			Pricing pricing1 = updatePricing( pricing, pricing2.getId() );

			PricingData.data

					.set(

							PricingData.data.indexOf(pricing2), pricing1

					);

			AuditHistory auditHistory = AuditHistoryHibernation.auditValues(
					"item price edited: " + pricing.getItemId().getItemName() + " : " + pricing.getPrice(),
					UserLoggedIn.getUserLoggedIn() );
		AuditHistoryData.theData.add(auditHistory);
		} else {
			Pricing pricing1 = PricingDAO.newPricing( pricing );

			PricingData.data.add(pricing1);

			AuditHistory auditHistory = AuditHistoryHibernation.auditValues(
					"new item price set: " + pricing.getItemId().getItemName() + " : " + pricing.getPrice(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryData.theData.add(auditHistory);
		}

	}

	private static Pricing updatePricing(Pricing pricing, BigDecimal idOfPricing) {
		return PricingDAO.updatePricing( pricing, idOfPricing );
	}

	private static ObservableList < Pricing > findAllPricingObservableList() {
		if ( getPricing() == null ) {
			setPricing( PricingDAO.changeListUtilToListObservable() );
		}
		return getPricing();
	}

	public static ObservableList < Pricing > findAllPricingObservableListRefresh() {
		setPricing( PricingDAO.changeListUtilToListObservable() );
		return getPricing();
	}

	public static Map < BigDecimal, Pricing > mappingItemIDsAsIDOfPrice() {
		Map < BigDecimal, Pricing > map = new LinkedHashMap <>();
		for (int i = 0; i < findAllPricingObservableList().size(); i++ ) {
			Pricing type = findAllPricingObservableList().get( i );
			map.put( type.getItemId().getId(), type );
		}
		return map;
	}

}
