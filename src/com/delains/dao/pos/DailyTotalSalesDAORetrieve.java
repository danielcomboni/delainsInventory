package com.delains.dao.pos;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;
import com.delains.model.sales.DailyTotalSales;
import com.delains.model.sales.DailyTotalSalesByItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DailyTotalSalesDAORetrieve {

    public static List<DailyTotalSales> FindDailyTotalSales(){
        List <DailyTotalSales> totalSales = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        Connection connection = DBUtils.connect();
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement( "SELECT SUM(cost) AS totalSales, strftime('%Y-%m-%d',date) as date FROM pos where quantity > 0 GROUP BY strftime('%Y-%m-%d',date)");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String date = resultSet.getString("date");
                BigDecimal dailyTotalSales = resultSet.getBigDecimal("totalSales");
                totalSales.add(new DailyTotalSales(date,dailyTotalSales));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnections( connection, preparedStatement, resultSet );
        }
        return totalSales;
    }

    public static List<DailyTotalSalesByItem> FindDailyTotalSalesByItem(){
        List <DailyTotalSalesByItem> totalSales = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        Connection connection = DBUtils.connect();
        ResultSet resultSet = null;



        try {

            preparedStatement = connection.prepareStatement( "SELECT SUM(cost) AS totalSales, strftime('%Y-%m-%d',date) as date, item_id as item FROM pos where quantity > 0 GROUP BY strftime('%Y-%m-%d',date), item_id");
            resultSet = preparedStatement.executeQuery();

            Map<BigDecimal, Item> itemMap = ItemHibernation.mapOfItemsToThierId();

            while (resultSet.next()){

                String date = resultSet.getString("date");
                BigDecimal dailyTotalSales = resultSet.getBigDecimal("totalSales");
                BigDecimal itemId = resultSet.getBigDecimal("item");

                totalSales.add(new DailyTotalSalesByItem(date,dailyTotalSales,itemMap.get(itemId)));

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DBUtils.closeConnections( connection, preparedStatement, resultSet );
        }
        return totalSales;
    }


}
