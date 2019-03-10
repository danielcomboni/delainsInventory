package com.delains.utilities.methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class MethodReflection < T > {

	private T t;

	public T getT() {
		return t;
	}

	public void setT( T t ) {
		this.t = t;
	}

	public MethodReflection( T t ) {
		this.t = t;
	}

	/*
	 * returns the setter method name of type String
	 * 
	 * it also sets the value for the returned method name of type String
	 * 
	 */
	public String setterNameString( T classObject, String setterNameString, String setValue ) {
		String setterName = null;
		try {
			Method method = classObject.getClass().getMethod( setterNameString, String.class );
			method.invoke( classObject, setValue );
			setterName = method.getName();
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
		}

		return setterName;
	}

	/*
	 * returns the getter value of type String
	 * 
	 * 
	 */
	public String getterNameString( T classObject, String getterNameString ) {
		String getterValue = null;
		try {
			Method method = classObject.getClass().getMethod( getterNameString );
			getterValue = ( String ) method.invoke( classObject );
			System.out.println( "valuuuuuuuuu: " + getterValue );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
		}
		return getterValue;
	}

	/*
	 * returns the setter method name of type BigDecimal
	 * 
	 * it also sets the value for the returned method name of type BigDecimal
	 * 
	 */
	public String setterNameBigDecimal( T classObject, String setterNameBigDecimal, BigDecimal setValue ) {
		String setterName = null;
		try {
			Method method = classObject.getClass().getMethod( setterNameBigDecimal, BigDecimal.class );
			method.invoke( classObject, setValue );
			setterName = method.getName();
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
		}
		return setterName;
	}

	/*
	 * returns the getter value of type BigDecimal
	 * 
	 * 
	 */
	public BigDecimal getterNameBigDecimal( T classObject, String getterNameBigDecimal ) {
		BigDecimal getterValue = null;
		try {
			Method method = classObject.getClass().getMethod( getterNameBigDecimal );
			getterValue = ( BigDecimal ) method.invoke( classObject );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
		}
		return getterValue;
	}

}
