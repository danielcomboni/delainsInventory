package com.delains.ui.item.threading;

import com.delains.model.items.Item;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.util.function.Function;

public class ServiceTaskItem {


    private static Alert al;

    public Alert showProgress(){
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("title");
        alert.setHeaderText("content");
        // alert.setContentText("C:/MyFile.txt");
        return alert;
    }


    private Service<Item> backgroundThreadSave;
    private Service<Item> backgroundThreadUpdate;
    private Service<ObservableList<Item>> backgroundThreadGetList;
    private Service<BigDecimal> backgroundThreadDelete;

    public Item createItemTask(Function<Item, Item> itemFunction, Item item){
        backgroundThreadSave = new Service<Item>() {
            @Override
            protected Task<Item> createTask() {
                return new Task<Item>() {

                    @Override
                    protected Item call() throws Exception {


                        return null;
                    }
                };
            }
        };
        return item;
    }


}
