package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Category;
import com.delains.model.items.Item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class CategoryDAOUpdate {

    private static LinkedHashMap< String, String > mapsForUpdatingCategory() {

        LinkedHashMap < String, String > map = new LinkedHashMap <>();
        map.put( "name", "name" );

        return map;

    }


    public static Category updateCategory(Category category, BigDecimal idOfCategory ) {

        Category category1 = new Category();

        PreparedStatement preparedStatement = null;
        Connection connection = DBUtils.connect();

        try {

            preparedStatement = connection.prepareStatement(
                    DBUtils.getUpdateCommandString( "category", mapsForUpdatingCategory(), "WHERE id=?" ) );

            category.setId( idOfCategory );

            preparedStatement.setString( 1, category.getName() );
            preparedStatement.setBigDecimal( 2, category.getId() );

            int result = preparedStatement.executeUpdate();

            if (result > 0){
                category1 = category;
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnections( connection, preparedStatement, null );
        }
        return category1;
    }


}
