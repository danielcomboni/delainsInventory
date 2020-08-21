package com.delains.ui.invoker;

public class Refresh {

	private static int refreshingDeterminant;

	public static int getRefreshingDeterminant() {
		return refreshingDeterminant;
	}

	public static void setRefreshingDeterminant(int refreshingDeterminant) {
		Refresh.refreshingDeterminant = refreshingDeterminant;
	}

}
