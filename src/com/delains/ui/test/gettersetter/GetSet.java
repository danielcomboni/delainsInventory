package com.delains.ui.test.gettersetter;

import com.delains.ui.test.auto.Address;
import com.delains.ui.test.auto.AutoCompleteTextField;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GetSet extends Application {


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

        Test test = new Test();
        test.setStr("test...");
        System.out.println(test.getStr());

        pane.getChildren().add( field );
        Scene scene = new Scene( pane );
        primaryStage.setScene( scene );
        primaryStage.show();

    }

    public static void main( String [ ] args ) {
        launch( args );
    }

    public class Test {

        private SimpleStringProperty str = new SimpleStringProperty();

        public Test(String  str) {
            this.str = new SimpleStringProperty(str);
        }

        public Test(){

        }

        public final String getStr() {
            return str.get();
        }

        public final void setStr(String str) {
            this.str.set(str);
        }
    }
}




