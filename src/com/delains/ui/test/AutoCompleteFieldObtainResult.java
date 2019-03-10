package com.delains.ui.test;

public class AutoCompleteFieldObtainResult {

	private AutoCompleteFieldObtainResult() {

	}

	private static String fieldResultObtained;

	public static String getFieldResultObtained() {
		return fieldResultObtained;
	}

	public static void setFieldResultObtained( String fieldResultObtained ) {
		AutoCompleteFieldObtainResult.fieldResultObtained = fieldResultObtained;
	}

}
