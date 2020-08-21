package com.delains.model.pos;

import com.delains.model.items.Item;
import com.delains.model.pricing.Pricing;

public class ItemPriceBarcode {
    private Item item;
    private Pricing pricing;

    public ItemPriceBarcode(Item item, Pricing pricing) {
        this.item = item;
        this.pricing = pricing;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    @Override
    public String toString() {
        return "" +item.getItemName()
                +"\t"
                +item.getUnitOfMeasurement()
                +"\t"
                +item.getPackageVolume()
                +"\t"
                +item.getPackageName();
    }
}
