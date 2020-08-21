package com.delains.model.sales;

import com.delains.model.items.Item;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class DailyTotalSalesByItem {
    
    private StringProperty date;
    private ObjectProperty<BigDecimal> totalSales;
    private ObjectProperty<Item> item;

    public DailyTotalSalesByItem(String date, BigDecimal totalSales, Item item) {
        this.date = new SimpleStringProperty(date);
        this.totalSales = new SimpleObjectProperty<>(totalSales);
        this.item = new SimpleObjectProperty<>(item);
    }

    public DailyTotalSalesByItem() {
        this(null,BigDecimal.ZERO, null);
    }


    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public BigDecimal getTotalSales() {
        return totalSales.get();
    }

    public ObjectProperty<BigDecimal> totalSalesProperty() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales.set(totalSales);
    }

    public Item getItem() {
        return item.get();
    }

    public ObjectProperty<Item> itemProperty() {
        return item;
    }

    public void setItem(Item item) {
        this.item.set(item);
    }

    @Override
    public String toString() {
        return "DailyTotalSalesByItem{" +
                "date=" + date +
                ", totalSales=" + totalSales +
                ", item=" + item +
                '}';
    }
}
