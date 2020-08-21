package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CategoryDAOInsert {

    private static LinkedHashMap< String, String > categoryInsertionDefinition() {

        LinkedHashMap < String, String > map = new LinkedHashMap <>();
        map.put( "name", "VARCHAR(255)" );
        return map;
    }

    static ObservableList<Category> newCategory(ObservableList<Category> categories) {

        PreparedStatement preparedStatement;
        preparedStatement = null;
        Connection connection;
        connection = DBUtils.connect();

        List< Category > categoriesReturned = new ArrayList<>();

        try {

            preparedStatement = connection
                    .prepareStatement(DBUtils.getInsertCommandString(categoryInsertionDefinition(), "category"));

            // clear batch
            preparedStatement.clearBatch();

            // add to batch
            for ( Category category: categories) {

                preparedStatement.setString(1, category.getName());
                preparedStatement.addBatch();
                categoriesReturned.add(category);

            }

            // execute batch
            int[] insertions = preparedStatement.executeBatch();

            // get generated keys
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            // set generated keys to each insertion result
            int index = 0;
            while (resultSet.next()){
                BigDecimal id = resultSet.getBigDecimal(1);
                Category category = categoriesReturned.get(index);
                category.setId(id);
                categoriesReturned.set(index, category );
                index++;
            }

            // close result set
            if (resultSet != null) {
                resultSet.close();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnections(connection, preparedStatement, null);
        }

        return FXCollections.observableArrayList(categoriesReturned);
    }

    static Category newCategory(Category category) {

        PreparedStatement preparedStatement;
        preparedStatement = null;
        Connection connection;
        connection = DBUtils.connect();

        try {

            preparedStatement = connection
                    .prepareStatement(DBUtils.getInsertCommandString(categoryInsertionDefinition(), "category"));

            preparedStatement.setString(1, category.getName());

            long i = preparedStatement.executeUpdate();

            if (i > 0 ){

                BigDecimal id;
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if ( rs != null && rs.next() ) {
                    id = rs.getBigDecimal( 1 );
                    category.setId(id);
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
        return category;
    }



}
