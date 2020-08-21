package com.delains.ui.test.auto;

import com.delains.dao.item.ItemHibernation;
import com.delains.model.items.Item;
import com.delains.ui.test.AutocompletionlTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

class Testttt{

    private static void collection(){
        List<Address> entries = new ArrayList<>();

        entries.add(new Address(50, "Main Street", "Oakville", "Ontario"));
        entries.add(new Address(3, "Fuller Road", "Toronto", "Ontario"));

        AutoCompleteTextField<Address> text = new AutoCompleteTextField(entries);

        text.getEntryMenu().setOnAction(e ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event ->
            {
                if (text.getLastSelectedObject() != null)
                {
                    text.setText(text.getLastSelectedObject().toString());
}
            });
        });

    }
}


public class Starter extends Application {

    @Override
    public void start(Stage primaryStage ) throws Exception {
        // TODO Auto-generated method stub

        StackPane pane = new StackPane();

        ArrayList<Address> entries = new ArrayList<>();

        entries.add(new Address(50, "Main Street", "Oakville", "Ontario"));
        entries.add(new Address(3, "Fuller Road", "Toronto", "Ontario"));

        AutoCompleteTextField<Address> text = new AutoCompleteTextField(entries);


        TextField field = text;


        // ObservableList < String > strings = FXCollections.observableArrayList();



        text.getEntryMenu().setOnAction(e ->
        {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event ->
            {
                if (text.getLastSelectedObject() != null)
                {
                    text.setText(text.getLastSelectedObject().toString());
}
            });
        });

        pane.getChildren().add( field );
        Scene scene = new Scene( pane );
        primaryStage.setScene( scene );
        primaryStage.show();

    }

    public static void main( String [ ] args ) {
        launch( args );
    }

}

