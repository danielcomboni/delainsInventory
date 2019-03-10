package com.delains.ui.combobox;

import com.delains.utilities.methods.MethodReflection;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class ComboBoxGeneric < T > {

	private ComboBox < T > comboBox;

	public ComboBox < T > getComboBox() {
		return comboBox;
	}

	public void setComboBox( ComboBox < T > comboBox ) {
		this.comboBox = comboBox;
	}

	public ComboBoxGeneric( T t ) {

		comboBox = new ComboBox <>();

	}

	public ComboBoxGeneric( ComboBox < T > comboBox ) {
		super();
		this.comboBox = comboBox;
	}

	public void populateComboBox( T t, ObservableList < T > list, String getterNameString ) {

		MethodReflection < T > methodReflection = new MethodReflection <>( t );

		comboBox.setItems( list );

		comboBox.setConverter( new StringConverter < T >() {

			@Override
			public String toString( T object ) {

				String name = methodReflection.getterNameString( t, getterNameString );

				return name;

			}

			@Override
			public T fromString( String string ) {

				T item = comboBox.getItems().parallelStream()
						.filter( e -> methodReflection.getterNameString( t, getterNameString ).equals( string ) )
						.findFirst().orElse( null );

				// T item = comboBox.getItems().stream().filter( it -> it.getItemName().equals(
				// string ) ).findFirst()
				// .orElse( null );
				System.out.println( "item:..." + item );
				return item;

			}
		} );

		comboBox.valueProperty().addListener( ( obs, oldVal, newVal ) -> {
			if ( newVal != null ) {
				System.out.println( "selected:..." + methodReflection.getterNameString( newVal, getterNameString ) );
				// System.out.println( "selected:..." + newVal + " ID:..." + newVal.getId() );
			}
		} );

	}

}
