package com.delains.dao.imageicon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.imageicon.ImageIcon;

public class ImageIconDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingItems() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "image", "image" );
		map.put( "business_title", "business_title" );

		return map;

	}

	public static void updateIconAndTitle( ImageIcon icon, BigDecimal id ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "image_and_title", mapsForUpdatingItems(), "WHERE id=?" ) );

			FileInputStream fis = new FileInputStream( icon.getFile() );

			preparedStatement.setBinaryStream( 1, ( InputStream ) fis, ( int ) icon.getFile().length() );
			preparedStatement.setString( 2, icon.getBusinessTitle() );

			preparedStatement.setBigDecimal( 3, BigDecimal.ONE );

			preparedStatement.executeUpdate();

			fis.close();

		} catch ( SQLException | IOException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
