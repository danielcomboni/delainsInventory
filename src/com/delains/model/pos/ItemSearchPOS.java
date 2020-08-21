package com.delains.model.pos;

import java.math.BigDecimal;

public class ItemSearchPOS {
    private String itemName;

    private String barcode;

    public ItemSearchPOS(String itemName, String barcode) {
        this.itemName = itemName;
        this.barcode = barcode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "" +itemName;
    }
}
