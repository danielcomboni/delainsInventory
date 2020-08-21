package com.delains.dao.imageicon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.delains.dao.utils.DBUtils;
import com.delains.model.imageicon.ImageIcon;

import javafx.scene.image.Image;

public class ImageIconDAORetrieve {

	public static ImageIcon getImageIconAndTitle() {

		ImageIcon icon = new ImageIcon();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "image_and_title", "" ) );
			resultSet = preparedStatement.executeQuery();

			if ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String title = resultSet.getString( "business_title" );

				InputStream is = resultSet.getBinaryStream( "image" );
				OutputStream os = new FileOutputStream( new File( "photo.jpg" ) );
				byte [ ] content = new byte [ 1024 ];
				int size = 0;
				while ( ( size = is.read( content ) ) != -1 ) {
					os.write( content, 0, size );
				}

				os.close();
				is.close();

				ImageIcon ic = new ImageIcon();
				ic.setId( id );
				ic.setBusinessTitle( title );

				Image image = new Image( "file:photo.jpg", 150, 200, true, true );

				ic.setImage( image );

				icon = ic;

			}

		} catch ( SQLException | IOException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return icon;
	}

}
