package com.delains.dao.imageicon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.imageicon.ImageIcon;

public class ImageIconDAOInsertion {

	private static LinkedHashMap < String, String > itemInsertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "image", "BLOB" );
		map.put( "business_title", "VARCHAR(255)" );

		return map;
	}

	public static void newImage( ImageIcon icon ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( itemInsertionDefinition(), "image_and_title" ) );

			FileInputStream fis = new FileInputStream( icon.getFile() );

			preparedStatement.setBinaryStream( 1, ( InputStream ) fis, ( int ) icon.getFile().length() );
			preparedStatement.setString( 2, icon.getBusinessTitle() );

			preparedStatement.executeUpdate();

			fis.close();

		} catch ( SQLException | IOException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
