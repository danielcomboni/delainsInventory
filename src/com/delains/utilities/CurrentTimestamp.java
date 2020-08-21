package com.delains.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CurrentTimestamp {

	public static String getDateTimeEndAtMinutes() {

		LocalDateTime dateTime = LocalDateTime.now();

		LocalDate localDate = LocalDate.now();

		String hours = null;
		if ( dateTime.getHour() < 10 ) {
			hours = "0" + dateTime.getHour();
		} else {
			hours = String.valueOf( dateTime.getHour() );
		}

		String minutes = null;
		if ( dateTime.getMinute() < 10 ) {
			minutes = "0" + dateTime.getMinute();
		} else {
			minutes = String.valueOf( dateTime.getMinute() );
		}

		return localDate + " " + hours + ":" + minutes;

	}

	public static String getLocalDateOnlyWithoutTimeAttached() {
		return LocalDate.now().toString();
	}

	public static String getDateTimeEndAtSecond() {
		LocalDateTime dateTime = LocalDateTime.now();

		LocalDate localDate = LocalDate.now();

		String hours = null;
		if ( dateTime.getHour() < 10 ) {
			hours = "0" + dateTime.getHour();
		} else {
			hours = String.valueOf( dateTime.getHour() );
		}

		String minutes = null;
		if ( dateTime.getMinute() < 10 ) {
			minutes = "0" + dateTime.getMinute();
		} else {
			minutes = String.valueOf( dateTime.getMinute() );
		}

		String seconds = null;
		if ( dateTime.getSecond() < 10 ) {
			seconds = "0" + dateTime.getSecond();
		} else {
			seconds = String.valueOf( dateTime.getSecond() );
		}

		return localDate + " " + hours + ":" + minutes + ":" + seconds;

	}

	public static String concatLocalDateToTimeWithoutSecondsAttached( LocalDate localDate ) {

		LocalDateTime dateTime = LocalDateTime.now();

		String hours = null;
		if ( dateTime.getHour() < 10 ) {
			hours = "0" + dateTime.getHour();
		} else {
			hours = String.valueOf( dateTime.getHour() );
		}

		String minutes = null;
		if ( dateTime.getMinute() < 10 ) {
			minutes = "0" + dateTime.getMinute();
		} else {
			minutes = String.valueOf( dateTime.getMinute() );
		}

		return localDate + " " + hours + ":" + minutes;

	}

	public static String concatLocalDateToTimeWithSecondsAttached( LocalDate localDate ) {

		LocalDateTime dateTime = LocalDateTime.now();

		String hours = null;
		if ( dateTime.getHour() < 10 ) {
			hours = "0" + dateTime.getHour();
		} else {
			hours = String.valueOf( dateTime.getHour() );
		}

		String minutes = null;
		if ( dateTime.getMinute() < 10 ) {
			minutes = "0" + dateTime.getMinute();
		} else {
			minutes = String.valueOf( dateTime.getMinute() );
		}

		String seconds = null;
		if ( dateTime.getSecond() < 10 ) {
			seconds = "0" + dateTime.getSecond();
		} else {
			seconds = String.valueOf( dateTime.getSecond() );
		}

		return localDate + " " + hours + ":" + minutes + ":" + seconds;

	}

	public static String getDateWithoutTimeAttached() {

		LocalDate localDate = LocalDate.now();

		return localDate.toString();

	}

}
