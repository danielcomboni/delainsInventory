package com.delains.model.items;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class Category {

    private ObjectProperty<BigDecimal> id;
    private StringProperty name;

    public Category(){
        this(BigDecimal.ZERO,null);
    }

    public Category(BigDecimal id, String name) {
        this.id = new SimpleObjectProperty<>(id);
        this.name = new SimpleStringProperty(name);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
