package com.delains.ui.pricing;

import com.delains.dao.pricing.PricingHibernation;
import com.delains.model.pricing.Pricing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PricingData {

    public static ObservableList<Pricing> data = FXCollections.observableArrayList(PricingHibernation.findAllPricingObservableListRefresh());


}
