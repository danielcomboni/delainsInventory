package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAORetrieve {

    public static ObservableList<Category> findAllCategories() {

        List < Category > categories = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        Connection connection = DBUtils.connect();
        ResultSet resultSet = null;

        try {

            preparedStatement = connection.prepareStatement( DBUtils.getRetrievingCommandString( "category", "" ) );
            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {

                BigDecimal id = resultSet.getBigDecimal( "id" );
                String name = resultSet.getString("name");

                Category category = new Category(id,name);

                categories.add( category );

            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnections( connection, preparedStatement, resultSet );
        }

        return FXCollections.observableArrayList(categories);
    }

    static Map<BigDecimal,Category> categoryMap(){

        Map<BigDecimal,Category> map = new LinkedHashMap<>();

        for(Category category : findAllCategories()){
            map.put(category.getId(), category);
        }

        return map;
    }

}
