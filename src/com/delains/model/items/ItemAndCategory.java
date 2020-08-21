package com.delains.model.items;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

public class ItemAndCategory {

    private ObjectProperty<BigDecimal> id;
    private ObjectProperty<Category> category;
    private ObjectProperty<Item> item;

    public ItemAndCategory(BigDecimal id, Category category, Item item) {
        this.id = new SimpleObjectProperty<>(id);
        this.category = new SimpleObjectProperty<>(category);
        this.item = new SimpleObjectProperty<>(item);
    }

    public ItemAndCategory(){
        this(BigDecimal.ZERO,null,null);
    }

    public BigDecimal getId() {
        return id.get();
    }

    public ObjectProperty<BigDecimal> idProperty() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id.set(id);
    }

    public Category getCategory() {
        return category.get();
    }

    public ObjectProperty<Category> categoryProperty() {
        return category;
    }

    public void setCategory(Category category) {
        this.category.set(category);
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
        return "ItemAndCategory{" +
                "id=" + id +
                ", category=" + category +
                ", item=" + item +
                '}';
    }
}
