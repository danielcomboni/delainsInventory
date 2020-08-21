package com.delains.ui.item.categories;

import com.delains.dao.item.ItemAndCategoryDAO;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Category;
import com.delains.model.items.Item;
import com.delains.model.items.ItemAndCategory;
import com.delains.ui.invoker.StageForAlerts;

import com.delains.ui.table.TableCellWrapping;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.math.BigDecimal;

public class ItemAndCategoryFrame extends BorderPane {

    private static ComboBox<Category> categoryComboBox;

    private static TableView<Item> itemTableView;

    private static TableView<ItemAndCategory> tableView;

    public  ItemAndCategoryFrame(){

        setId( "main_borderpane" );
        getStylesheets().add( ItemAndCategoryFrame.class.getResource( "/com/delains/ui/item/categories/categoriesFrame.css" ).toExternalForm() );

        // combobox with all the categories
        categoryComboBox = new ComboBox<>();
        CategoryData.setConverter(categoryComboBox);

        HBox boxTop = new HBox(10);
        boxTop.setPadding(new Insets(10));
        boxTop.getChildren().add(new Label("choose category:  "));
        boxTop.getChildren().add(categoryComboBox);
        setTop(boxTop);

        itemTableView = new TableView<>();
        itemTableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        itemTableView.setTableMenuButtonVisible( true );
        itemTableView.setEditable(true);

        itemTableView.setItems(ItemAndCategoryData.dataForCategorySetting);

        TableColumn<Item,String> columnItemName = new TableColumn<>("Item");
        columnItemName.setCellFactory(param -> new TableCellWrapping<Item, String>().wrapCellText(columnItemName));
        columnItemName.setCellValueFactory(param -> {

            StringProperty nameProperty = param.getValue().itemNameProperty();
            StringProperty packageProperty = param.getValue().packageNameProperty();
            ObjectProperty<BigDecimal> volumeProperty = param.getValue().packageVolumeProperty();

            String itemStr = nameProperty.getValue() + " - " + packageProperty.getValue() + " - " + NumberFormatting.formatToEnglish(volumeProperty.getValue().toString());

            return new SimpleStringProperty(itemStr) ;
        });
        itemTableView.getColumns().add(columnItemName);

        TableColumn<Item, Void> columnAssignCategory = new TableColumn<>("assign category");

        addButtonToColumn(columnAssignCategory);
        itemTableView.getColumns().add(columnAssignCategory);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        tableView.setTableMenuButtonVisible( true );
        tableView.setItems(ItemAndCategoryData.data);

        TableColumn<ItemAndCategory, String> columnItem = new TableColumn<>("Item");
        columnItem.setCellFactory(param -> new TableCellWrapping<ItemAndCategory,String>().wrapCellText(columnItem));
        columnItem.setCellValueFactory(param -> param.getValue().getItem().packageNameProperty());
        tableView.getColumns().add(columnItem);

        TableColumn<ItemAndCategory, String> columnSetCategory = new TableColumn<>("Category assigned");
        columnSetCategory.setCellValueFactory(param -> param.getValue().getCategory().nameProperty());
        tableView.getColumns().add(columnSetCategory);

        TableColumn<ItemAndCategory, Void> columnDeleteCategory = new TableColumn<>("Delete");
        addButtonToColumnDelete(columnDeleteCategory);
        tableView.getColumns().add(columnDeleteCategory);

        SplitPane splitPane = new SplitPane();
        setCenter(splitPane);

        splitPane.getItems().addAll(itemTableView, tableView);


    }

    private static void addButtonToColumn(TableColumn<Item, Void> tableColumn){

        Callback<TableColumn<Item,Void>, TableCell<Item, Void>> callback = param -> new TableCell<Item, Void>(){
            JFXButton button = new JFXButton("set category");

            {
                button.setAlignment(Pos.CENTER);

                button.setOnAction(e -> {

                    TableRow row = this.getTableRow();

                    // get the params
                    Category category;
                    Item item = itemTableView.getItems().get(row.getIndex());

                    // pick a category
                    category = categoryComboBox.getSelectionModel().getSelectedItem();
                    if (!AddItemANdCategory.checkIfCategoryAlreadySet(item)){

                        StageForAlerts.inform("warning", item.getItemName() + " is already categorised");

                    }
                    else
                        AddItemANdCategory.addItemAndCategory(category, item);

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if ( empty ) {
                    setGraphic( null );
                } else {
                    setGraphic( button );
                }
            }
        };

        tableColumn.setCellFactory(callback);

    }


    private static void addButtonToColumnDelete(TableColumn<ItemAndCategory, Void> tableColumn){

        Callback<TableColumn<ItemAndCategory,Void>, TableCell<ItemAndCategory, Void>> callback = param -> new TableCell<ItemAndCategory, Void>(){

            JFXButton button = new JFXButton("Delete");

            {
                button.setAlignment(Pos.CENTER);

                button.setOnAction(e -> {

                    TableRow row = this.getTableRow();
                    ItemAndCategory itemAndCategory = tableView.getItems().get(row.getIndex());

                    delete(itemAndCategory);

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if ( empty ) {
                    setGraphic( null );
                } else {
                    setGraphic( button );
                }
            }
        };

        tableColumn.setCellFactory(callback);

    }


    private static void delete(ItemAndCategory itemAndCategory){
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {

                        ItemAndCategoryDAO.deleteItemAndCategory(itemAndCategory.getId());
                        ItemAndCategoryData.data.remove(itemAndCategory);

                        return null;
                    }
                };
            }
        };

        service.start();

    }

}
