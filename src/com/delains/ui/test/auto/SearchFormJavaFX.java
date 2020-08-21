package com.delains.ui.test.auto;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SearchFormJavaFX  extends Application{

    @Override
    public void start(Stage ps) throws Exception {

        List<Address> entriess = new ArrayList<>();

        entriess.add(new Address(50, "Main Street", "tt", "qq"));
        entriess.add(new Address(3, "Fuller Road", "Toronto", "else where"));

      //  String[] options = {"How do I get a passport",
        //                    "How do I delete my Facebook Account",
          //                  "How can I change my password",
            //                "How do I write some code in my question :D"};

        // note that you don't need to stick to these types of containers, it's just an example
        StackPane root = new StackPane();
        GridPane container = new GridPane();
        HBox searchBox = new HBox();

        ////////////////////////////////////////////////////

        TextField text = new TextField(); 

        // add a listener to listen to the changes in the text field
        text.textProperty().addListener((observable, oldValue, newValue) -> {
            if(container.getChildren().size()>1){ // if already contains a drop-down menu -> remove it 
                container.getChildren().remove(1);
            }
            container.add(populateDropDownMenu(newValue, entriess),0,1); // then add the populated drop-down menu to the second row in the grid pane
        });

        // those buttons just for example
        // note that you can add action listeners to them ..etc
        Button close = new Button("X");
        Button search = new Button("Search");
        searchBox.getChildren().addAll(text,close,search);


        /////////////////////////////////////////////////

        // add the search box to first row
        container.add(searchBox, 0, 0);
        // the colors in all containers only for example
        container.setBackground(new Background(new BackgroundFill(Color.GRAY, null,null)));
        ////////////////////////////////////////////////

        root.getChildren().add(container);

        Scene scene = new Scene(root, 225,300);
        ps.setScene(scene);
        ps.show();


    }


    // this method searches for a given text in an array of Strings (i.e. the options)
    // then returns a VBox containing all matches
    public static VBox populateDropDownMenu(String text, List<Address> options){
        VBox dropDownMenu = new VBox();
        dropDownMenu.setBackground(new Background(new BackgroundFill(Color.GREEN, null,null))); // colors just for example
        dropDownMenu.setAlignment(Pos.CENTER); // all these are optional and up to you

        for(Address option : options){ // loop through every String in the array
            // if the given text is not empty and doesn't consists of spaces only, as well as it's a part of one (or more) of the options

           if (!text.replace(" ", "").isEmpty() && option.toString().toUpperCase().contains(text.toUpperCase())){
               Label theLabel = new Label(options.toString());
               dropDownMenu.getChildren().add(theLabel);
           }

            //if(!text.replace(" ", "").isEmpty() && option.toUpperCase().contains(text.toUpperCase())){
                //Label label = new Label(option); // create a label and set the text
                // you can add listener to the label here if you want
                // your user to be able to click on the options in the drop-down menu
             //   dropDownMenu.getChildren().add(label); // add the label to the VBox
           // }
        }

        return dropDownMenu; // at the end return the VBox (i.e. drop-down menu)
    }



    public static void main(String[] args) {
        launch();
    }

}