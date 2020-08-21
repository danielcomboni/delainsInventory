package com.delains.ui.payments;

import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.model.payments.MediumOfPayment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class MediumOfPaymentData {
    public static ObservableList<MediumOfPayment> data =
            FXCollections.observableArrayList(MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed());

    public static ComboBox<MediumOfPayment> setConverter(ComboBox<MediumOfPayment> comboBox){

        comboBox.setItems(data);
        comboBox.setConverter(new StringConverter<MediumOfPayment>() {
            @Override
            public String toString(MediumOfPayment object) {
                return object.getNameOfMediumOfPayment();
            }

            @Override
            public MediumOfPayment fromString(String string) {
                return comboBox.getItems().stream().filter(e -> e.getNameOfMediumOfPayment().equals(string)).findFirst().orElse(null);
            }
        });

        return comboBox;
    }
}
