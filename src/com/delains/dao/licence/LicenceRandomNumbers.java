package com.delains.dao.licence;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LicenceRandomNumbers {

	private static List < BigDecimal > actualNumbers = new ArrayList <>();

	/**
	 * Actual number is the key and the two characters make the value
	 */

	public static List < BigDecimal > getActualNumbers() {
		return actualNumbers;
	}

	public static void setActualNumbers( List < BigDecimal > actualNumbers ) {
		LicenceRandomNumbers.actualNumbers = actualNumbers;
	}

	private static Map < BigDecimal, String > numberAndLicenceMap = new LinkedHashMap <>();

	public static Map < BigDecimal, String > getNumberAndLicenceMap() {
		return numberAndLicenceMap;
	}

	public static void setNumberAndLicenceMap( Map < BigDecimal, String > numberAndLicenceMap ) {
		LicenceRandomNumbers.numberAndLicenceMap = numberAndLicenceMap;
	}

	public static String getTheGeneratedbarcode( char monthDeterminingChar ) {

		Random r = new SecureRandom();
		int barcodeInteger = r.nextInt( 1_000_000 );

		int len = String.valueOf( barcodeInteger ).length();

		String forOneMonth = String.valueOf( barcodeInteger );

		char one = forOneMonth.charAt( 0 );

		if ( getActualNumbers().parallelStream().noneMatch( e -> e.doubleValue() == barcodeInteger )
				&& ( len > 5 && len < 7 ) && ( one == monthDeterminingChar ) ) {

			if ( getNumberAndLicenceMap() == null ) {
				Map < BigDecimal, String > numberAndLicenceMap = new LinkedHashMap <>();

				numberAndLicenceMap.put( new BigDecimal( barcodeInteger ),
						generateLicenceKeyHashed( String.valueOf( barcodeInteger ) ) );

				setNumberAndLicenceMap( numberAndLicenceMap );

			} else {

				getNumberAndLicenceMap().put( new BigDecimal( barcodeInteger ),
						generateLicenceKeyHashed( String.valueOf( barcodeInteger ) ) );

			}

			return String.valueOf( barcodeInteger );

		} else {
			return getTheGeneratedbarcode( monthDeterminingChar );

		}

	}

	private static List < String > licence = new ArrayList <>();

	public static List < String > getLicence() {
		return licence;
	}

	public static void setLicence( List < String > licence ) {
		LicenceRandomNumbers.licence = licence;
	}

	public static String generateLicenceKeyHashed( String numberToHash ) {
		MessageDigest md = null;
		StringBuilder sb = new StringBuilder();

		try {
			md = MessageDigest.getInstance( "MD5" );
			md.update( numberToHash.getBytes() );

			byte [ ] bytes = md.digest();
			for ( int i = 0; i < bytes.length; i++ ) {
				sb.append( Integer.toString( ( bytes [ i ] & 0xff ) + 0 * 100, 32 ).substring( 1 ) );
				getLicence().add( sb.toString() );
			}

		} catch ( NoSuchAlgorithmException e ) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static BigDecimal addToRandomNumber( char monthDeterminingChar ) {
		String strVal = getTheGeneratedbarcode( monthDeterminingChar );
		getActualNumbers().add( new BigDecimal( strVal ) );
		return new BigDecimal( strVal );
	}

	public static void main( String [ ] args ) {

// char c = '5';
		//
		// for ( int i = 0; i < 10000; i++ ) {
		// addToRandomNumber( c );
		// }
		//
		// for ( Map.Entry < BigDecimal, String > map :
		// getNumberAndLicenceMap().entrySet() ) {
// );
		// }
		//
//
// ) );

	}

}
