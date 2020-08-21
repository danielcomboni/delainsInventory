package com.delains.ui.threading;

import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.test.table.FileChooserSample;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ThreadTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private TableView<Person> table = new TableView<Person>();

    private static Alert alert;

    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Jacob"));

    final HBox hb = new HBox();

    private Service<String> backgroundThread;

    Alert al;

    private Person add(String s){

        System.out.println("trying to add");
        backgroundThread = new Service<String>() {
            @Override
            protected Task<String> createTask() {

                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        data.add(new Person(s));
                        return null;
                    }
                };
            }
        };

        data.add(new Person(String.valueOf(s)));
        return new Person(s);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(550);

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol);

        final Button addButton = new Button("Add");

        Label label = new Label("Test label");
        final ProgressBar progressBar = new ProgressBar(0);
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("title");
        alert.setHeaderText("content");
        // alert.setContentText("C:/MyFile.txt");
        al = alert;
//        Optional<ButtonType> option = alert.showAndWait();

        /*
        if (option.get() == ButtonType.OK){
            decide = true;
        }else{
            decide = false;
        }*/


        addButton.setOnAction(e ->{

            al.show();

            backgroundThread = new Service<String>() {
                @Override
                protected Task<String> createTask() {
                    return new Task<String>() {

                        @Override
                        protected String call() throws Exception {

                            for (int i = 0; i <= 1_000_000_0; i++){

                                add(String.valueOf(i));
                                updateMessage("i : " + i);
                              //  updateValue(String.valueOf(i));

                            }

                            return backgroundThread.getValue();

                        }
                    };
                }
            };

            backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    System.out.println("done");
                    progressBar.progressProperty().unbind();
                    progressBar.setProgress(0);
                    al.close();
                }
            });

            label.textProperty().bind(backgroundThread.valueProperty());
            al.getDialogPane().setContent(progressBar);
            progressBar.progressProperty().bind(backgroundThread.progressProperty());
            backgroundThread.start();

        });

        hb.getChildren().addAll(label , addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


    public static class Person {

        private final SimpleStringProperty firstName;

        private Person(String fName) {
            this.firstName = new SimpleStringProperty(fName);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }


    }

}
