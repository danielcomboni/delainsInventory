package com.delains.dao.item;

import com.delains.dao.utils.DBUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDAODelete {

    public static void deleteCategory(BigDecimal id) {
        PreparedStatement preparedStatement = null;
        Connection connection = DBUtils.connect();

        try {

            preparedStatement = connection
                    .prepareStatement(DBUtils.getDeleteCommandString("category", "WHERE id=?"));

            preparedStatement.setBigDecimal(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnections(connection, preparedStatement, null);
        }
    }

}
