package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.ItemAndCategory;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class ItemAndCategoryDAO {

    private static LinkedHashMap< String, String > itemANdCategoryTableDefinitions() {

        LinkedHashMap < String, String > map = new LinkedHashMap <>();
        map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
        map.put( "item_id", "INTEGER" );
        map.put( "category_id", "INTEGER" );

        return map;

    }

    private static void createTableUsers() {
        DBUtils.apiToCreateTable( itemANdCategoryTableDefinitions(), "item_and_category" );
    }

    public static ItemAndCategory newItemAndCategory(ItemAndCategory itemAndCategory){
        createTableUsers();
        return ItemAndCategoryDAOInsert.newItemAndCategory(itemAndCategory);
    }

    public static ObservableList<ItemAndCategory> findAllItemAndCategory(){
        createTableUsers();
        return ItemAndCategoryDAORetrieve.findAllItemAndCategory();
    }

    public static ItemAndCategory updateItemAndCategory(ItemAndCategory itemAndCategory){
        return ItemAndCategoryDAOUpdate.updateItemAndCategory(itemAndCategory);
    }

    public static void deleteItemAndCategory(BigDecimal id){
        ItemAndCategoryDAODelete.deleteItemAndCategory(id);
    }

}
