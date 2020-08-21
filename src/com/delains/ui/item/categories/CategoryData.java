package com.delains.ui.item.categories;

import com.delains.dao.item.CategoryDAO;
import com.delains.model.items.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class CategoryData {

    public static ObservableList<Category> data = FXCollections.observableArrayList(CategoryDAO.findAllCategories());

    public static ComboBox<Category> setConverter(ComboBox<Category> comboBox){

        comboBox.setItems(data);
        comboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category object) {
                return object.getName();
            }

            @Override
            public Category fromString(String string) {
                return comboBox.getItems().stream().filter(e -> e.getName().equals(string)).findFirst().orElse(null);
            }
        });

        return comboBox;
    }

}
