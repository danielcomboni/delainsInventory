package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.ItemAndCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class ItemAndCategoryDAOUpdate {

    private static LinkedHashMap< String, String > mapsForUpdatingItemsItemAndCategory() {

        LinkedHashMap < String, String > map = new LinkedHashMap <>();
        map.put( "item_id", "INTEGER" );
        map.put( "category_id", "INTEGER" );

        return map;
    }


    static ItemAndCategory updateItemAndCategory(ItemAndCategory itemAndCategory) {

        PreparedStatement preparedStatement;
        preparedStatement = null;
        Connection connection;
        connection = DBUtils.connect();

        try {

            preparedStatement = connection
                    .prepareStatement(DBUtils.getUpdateCommandString("item_and_category",mapsForUpdatingItemsItemAndCategory(), "WHERE id=?"));

            preparedStatement.setBigDecimal(1, itemAndCategory.getItem().getId());
            preparedStatement.setBigDecimal(2, itemAndCategory.getCategory().getId());
            preparedStatement.setBigDecimal(3, itemAndCategory.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnections(connection, preparedStatement, null);
        }
        return itemAndCategory;
    }


}
