package com.delains.ui.item;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ItemData {

    public static ObservableList<Item> data = FXCollections.observableArrayList(ItemHibernation.findAllItemsObservableListRefreshed());

    public static ComboBox<Item> setConverter(ComboBox<Item> comboBox){

        comboBox.setItems(data);
        comboBox.setConverter(new StringConverter<Item>() {
            @Override
            public String toString(Item object) {
                return (

                        object.getItemName()
                                + " " +
                        object.getPackageName()
                        + " " +
                                object.getPackageVolume()

                        );
            }

            @Override
            public Item fromString(String string) {
                return comboBox.getItems().stream().filter(e -> e.getBarcode().equals(string)).findFirst().orElse(null);
            }
        });

        return comboBox;
    }



}
