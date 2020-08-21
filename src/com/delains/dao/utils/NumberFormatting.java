package com.delains.dao.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatting {

	private static boolean numberCorrect = false;

	public static boolean isNumberCorrect() {
		return numberCorrect;
	}

	public static void setNumberCorrect( boolean numberCorrect ) {
		NumberFormatting.numberCorrect = numberCorrect;
	}

	public static String testNumberCorrectness( String strNumber ) {

		int len = 0;
		if ( strNumber != null ) {
			len = strNumber.trim().length();
		}

		String numbersConcated = "";

		if ( len > 0 ) {

			for ( int i = 0; i < len; i++ ) {

				Character c = strNumber.charAt( i );

				if ( Character.isDigit( c ) || c.equals( Character.valueOf( '.' ) ) ) {

					numbersConcated = numbersConcated.concat( String.valueOf( c ) );

				}

			}

			if ( numbersConcated != null && numbersConcated != "" ) {
				setNumberCorrect( true );
			} else {
				setNumberCorrect( false );
			}

		}

		else {

			numbersConcated = String.valueOf( "0" );

		}

		return numbersConcated;

	}

	public static String formatToEnglish( String numberStr ) {
		NumberFormat format = NumberFormat.getInstance( Locale.ENGLISH );
		String formattedNumber = format.format( new BigDecimal( numberStr ) );

		return formattedNumber;
	}

	// public static void main(String[] args) {
	//
	// String n = "10000.12";
	//
	// BigDecimal bigDecimal = new BigDecimal(n);
	//
	// NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
	//
	// testNumberCorrectness(n);
	//
//
//
	// }

}
