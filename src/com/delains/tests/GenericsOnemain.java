package com.delains.tests;

import java.math.BigDecimal;

public class GenericsOnemain {

	public static void main( String [ ] args ) {

		TestTwo testTwo = new TestTwo();

		TestThree testThree = new TestThree();
		testThree.setId( BigDecimal.ONE );
		testThree.setName( "three" );

		TestOne < TestTwo > testOne = new TestOne <>();

		
		testOne.setT( testTwo );
		testOne.getT().setId( BigDecimal.ONE );
		testOne.getT().setName( "one" );
		TestOne < TestThree > testTh = new TestOne <>();

		System.out.println( "one: " + testOne );
		System.out.println( "two: " + testTh );

	}

}
