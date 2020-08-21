package com.delains.ui.item.categories;

import com.delains.dao.item.ItemAndCategoryDAO;
import com.delains.model.items.Item;
import com.delains.model.items.ItemAndCategory;
import com.delains.ui.item.ItemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemAndCategoryData {

    public static ObservableList<ItemAndCategory> data = FXCollections.observableArrayList(ItemAndCategoryDAO.findAllItemAndCategory());

    public static ObservableList<Item> dataForCategorySetting = FXCollections.observableArrayList(ItemData.data);




}
