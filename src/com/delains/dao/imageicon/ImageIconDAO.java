package com.delains.dao.imageicon;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.imageicon.ImageIcon;

public class ImageIconDAO {

	private static LinkedHashMap < String, String > tableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "image", "BLOB" );
		map.put( "business_title", "VARCHAR(255)" );

		return map;
	}

	private static void createTable() {
		DBUtils.apiToCreateTable( tableDefinitions(), "image_and_title" );
	}

	public static void newIconAndTitle( ImageIcon icon ) {
		createTable();
		// icon2.getBusinessTitle() == null
		if ( getImageIconAndTitle().getBusinessTitle() != null ) {
			updateImageIconAndTitle( icon, BigDecimal.ONE );
		} else {
			ImageIconDAOInsertion.newImage( icon );
		}

	}

	private static void updateImageIconAndTitle( ImageIcon icon, BigDecimal id ) {
		ImageIconDAOUpdate.updateIconAndTitle( icon, id );
	}

	public static ImageIcon getImageIconAndTitle() {
		createTable();
		return ImageIconDAORetrieve.getImageIconAndTitle();
	}

}
