package com.delains.ui.suppliers;

import com.delains.dao.suppliers.SupplierTypeHibernation;
import com.delains.model.suppliers.SupplierType;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class SupplierTypeData {

    public static ObservableList<SupplierType> data = SupplierTypeHibernation.findAllSupplierTypesObservableListRefreshed();

    public static ComboBox<SupplierType> setConverter(ComboBox<SupplierType> comboBox){

        comboBox.setItems(data);
        comboBox.setConverter(new StringConverter<SupplierType>() {
            @Override
            public String toString(SupplierType object) {
                return object.getType();
            }

            @Override
            public SupplierType fromString(String string) {
                return comboBox.getItems().stream().filter(e -> e.getType().equals(string)).findFirst().orElse(null);
            }
        });

        return comboBox;
    }
}
