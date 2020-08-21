package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Category;
import com.delains.model.items.ItemAndCategory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class ItemAndCategoryDAOInsert {

    private static LinkedHashMap< String, String > itemAndCategoryInsertionDefinition() {

        LinkedHashMap < String, String > map = new LinkedHashMap <>();
        map.put( "item_id", "INTEGER" );
        map.put( "category_id", "INTEGER" );

        return map;
    }


    static ItemAndCategory newItemAndCategory(ItemAndCategory itemAndCategory) {

        PreparedStatement preparedStatement;
        preparedStatement = null;
        Connection connection;
        connection = DBUtils.connect();

        try {

            preparedStatement = connection
                    .prepareStatement(DBUtils.getInsertCommandString(itemAndCategoryInsertionDefinition(), "item_and_category"));

            preparedStatement.setBigDecimal(1, itemAndCategory.getItem().getId());
            preparedStatement.setBigDecimal(2, itemAndCategory.getCategory().getId());

            long i = preparedStatement.executeUpdate();

            if (i > 0 ){

                BigDecimal id;
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if ( rs != null && rs.next() ) {
                    id = rs.getBigDecimal( 1 );
                    itemAndCategory.setId(id);
                }

                if (rs != null) {
                    rs.close();
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnections(connection, preparedStatement, null);
        }
        return itemAndCategory;
    }


}
