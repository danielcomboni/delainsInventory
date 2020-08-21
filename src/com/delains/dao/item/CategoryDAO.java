package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Category;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class CategoryDAO {

    private static LinkedHashMap< String, String > categoryTableDefinitions() {

        LinkedHashMap < String, String > map = new LinkedHashMap <>();
        map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
        map.put( "name", "VARCHAR(255)" );

        return map;

    }

    private static void createTableUsers() {
        DBUtils.apiToCreateTable( categoryTableDefinitions(), "category" );
    }

    public static Category newCategory(Category category){
        createTableUsers();
        return CategoryDAOInsert.newCategory(category);
    }

    public static ObservableList<Category> newCategories(ObservableList<Category> categories){
        createTableUsers();
        return CategoryDAOInsert.newCategory(categories);
    }

    public static ObservableList<Category> findAllCategories(){
        createTableUsers();
        return CategoryDAORetrieve.findAllCategories();
    }

    public static Category updateCategory(Category category){
        return CategoryDAOUpdate.updateCategory(category, category.getId());
    }

    public static void deleteCategory(BigDecimal id){
        CategoryDAODelete.deleteCategory(id);
    }

}
