package com.delains.dao.item;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

import com.delains.model.items.Item;

public class GenerateBarcode {

	private static Map < BigDecimal, Item > getItemsToCompareBarcodes() {
		return ItemHibernation.mapOfItemsToThierId();
	}

	public static String getTheGeneratedbarcode() {

		Random r = new SecureRandom();
		int barcodeInteger = r.nextInt( 1_000_000 );

		if ( getItemsToCompareBarcodes().entrySet().parallelStream()
				.noneMatch( e -> e.getValue().getBarcode().equals( String.valueOf( barcodeInteger ) ) ) ) {

			return String.valueOf( barcodeInteger );

		} else {

			return getTheGeneratedbarcode();

		}

	}

	public static void main( String [ ] args ) {
		System.out.println( getTheGeneratedbarcode() );
	}

}
