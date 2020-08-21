package com.delains.ui.suppliers;

import com.delains.dao.suppliers.SupplierHibernation;
import com.delains.model.suppliers.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class SupplierData {

    public static ObservableList<Supplier> data = FXCollections.observableArrayList(SupplierHibernation.findAllSuppliersObservableListRefreshed());

    public static ComboBox<Supplier> setConverter(ComboBox<Supplier> comboBox){

        comboBox.setItems(data);
        comboBox.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier object) {
                return object.getSupplierName();
            }

            @Override
            public Supplier fromString(String string) {
                return comboBox.getItems().stream().filter(e -> e.getSupplierName().equals(string)).findFirst().orElse(null);
            }
        });

        return comboBox;
    }
}
