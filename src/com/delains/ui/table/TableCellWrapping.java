package com.delains.ui.table;

import com.delains.model.purchases.Purchase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;

public class TableCellWrapping<T,S> {

    public TableCell<T,S> wrapCellText(TableColumn<T,S> column){
        TableCell < T, S > cell = new TableCell <>();
        Text text = new Text();
        text.getStyleClass().add( "text-id" );
        cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
        text.wrappingWidthProperty().bind( column.widthProperty() );
        text.textProperty().bind((ObservableValue<? extends String>) cell.itemProperty());
        cell.setGraphic( text );

        return cell;
    }

}
