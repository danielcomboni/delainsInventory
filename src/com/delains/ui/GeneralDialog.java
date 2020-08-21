package com.delains.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;

public class GeneralDialog {

    private static ObservableList<Dialog<String>> dialogs = FXCollections.observableArrayList();

    private static Dialog<String> dialog;

    public static void showDialog(String title, String headerText, Node node){



        dialog = new Dialog<>();

        dialog.setResizable(true);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);

        dialog.getDialogPane().setContent(new GeneralDialogBorderPane(node));

        dialog.initModality(Modality.APPLICATION_MODAL);

        dialogs.add(dialog);

        ButtonType buttonClose = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(buttonClose);
        dialog.showAndWait();

    }

    public static void closeDialog(){

       if (dialog.isShowing()){
           dialog.close();
           dialogs.remove(dialog);
       }

    }

    private static class GeneralDialogBorderPane extends BorderPane {

        GeneralDialogBorderPane(Node node){
            this.setId( "main_borderpane" );
            getStylesheets().add( GeneralDialog.class.getResource( "general-dialog.css" ).toExternalForm() );
            setCenter(node);
        }
    }

}
