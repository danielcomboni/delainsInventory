package com.delains.model.sales;

import com.delains.ui.sales.DailySalesTotalUI;
import com.delains.ui.sales.DailySalesTotalUIByItem;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DailyTotalSalesMainUI extends BorderPane {

    public DailyTotalSalesMainUI() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(10));

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        this.setCenter(vBox);

        // add general total sales
        vBox.getChildren().add(new VBox(10, new Label("total sales for all items"), new BorderPane(new DailySalesTotalUI())));

        // add total sales by item
        vBox.getChildren().add(new VBox(10, new Label("total sales for each item"), new BorderPane(new DailySalesTotalUIByItem())));

        // add total sales by category

    }
}
