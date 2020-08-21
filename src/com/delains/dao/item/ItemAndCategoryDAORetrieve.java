package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Category;
import com.delains.model.items.Item;
import com.delains.model.items.ItemAndCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemAndCategoryDAORetrieve {

    static ObservableList<ItemAndCategory> findAllItemAndCategory(){
        List<ItemAndCategory> itemAndCategories = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        Connection connection = DBUtils.connect();
        ResultSet resultSet = null;

        try {

            preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("item_and_category", ""));
            resultSet = preparedStatement.executeQuery();

            Map<BigDecimal,Item> itemMap  = ItemHibernation.mapOfItemsToThierId();
            Map<BigDecimal, Category> categoryMap = CategoryDAORetrieve.categoryMap();

            while (resultSet.next()) {

                BigDecimal id = resultSet.getBigDecimal("id");
                BigDecimal itemId = resultSet.getBigDecimal("item_id");
                BigDecimal categoryId = resultSet.getBigDecimal("category_id");

                Item item = itemMap.get(itemId);
                Category category = categoryMap.get(categoryId);

                itemAndCategories.add(new ItemAndCategory(id, category, item));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnections(connection, preparedStatement, resultSet);
        }


        return FXCollections.observableArrayList(itemAndCategories);
    }

}
