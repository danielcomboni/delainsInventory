package com.delains.ui.item.categories;

import com.delains.model.items.Category;
import com.delains.ui.GeneralDialog;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.utils.FontAwesomeUtils;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

public class CategoriesFrame extends BorderPane {

    public static TableView<Category> tableView;

    private static TextField fieldOne;
    private static ObservableList<TextField> fields = FXCollections.observableArrayList();

    public CategoriesFrame(){

        setId( "main_borderpane" );

        getStylesheets().add( CategoriesFrame.class.getResource( "/com/delains/ui/item/categories/categoriesFrame.css" ).toExternalForm() );

        tableView = new TableView<>();
        tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        tableView.setTableMenuButtonVisible( true );

        TableColumn<Category, String> columnName = new TableColumn<>("Category");
        columnName.setCellValueFactory(param -> param.getValue().nameProperty());
        tableView.getColumns().add(columnName);

        TableColumn<Category,Void> columnEdit = new TableColumn<>("Edit");
        addButtonToColumnEdit(columnEdit);

        TableColumn<Category, Void> columnDelete = new TableColumn<>("Delete");
        addButtonToColumnDelete(columnDelete);

        tableView.setItems(CategoryData.data);

        setCenter(tableView);

        // create fields
        hBoxTop = new VBox(10);
        hBoxTop.setPadding(new Insets(10));
        fieldOne = new TextField();
        ComponentWidth.setWidthOfTextField( fieldOne, 300 );
        fieldOne.setPromptText("category");

        Label label = new Label("create categories");
        hBoxTop.getChildren().add(label);
        hBoxTop.getChildren().add(new Separator());

        JFXButton buttonAddField = new JFXButton("add more", FontAwesomeUtils.fontAwesomeAdd());
        buttonAddField.setButtonType( JFXButton.ButtonType.RAISED );

        HBox box = new HBox(10);
        box.setPadding(new Insets(10));
        box.getChildren().add(buttonAddField);

        JFXButton buttonSave = new JFXButton("save", FontAwesomeUtils.fontAwesomeSave());
        buttonSave.setButtonType(JFXButton.ButtonType.RAISED);
        box.getChildren().add(buttonSave);

//        hBoxTop.getChildren().addAll(box);

        String theStyleColor = "-fx-background-color: #0d3a2f;";
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(new Separator());
        borderPane.getBottom().setStyle(theStyleColor);
        borderPane.setTop(box);

        hBoxTop.getChildren().add(fieldOne);
        ScrollPane scrollPane = new ScrollPane();
        hBoxTop.setStyle(theStyleColor);

        scrollPane.setContent(hBoxTop);
        scrollPane.setStyle(theStyleColor);

        borderPane.setCenter(scrollPane);
        borderPane.setStyle(theStyleColor);
        setRight(borderPane);

        getRight().setStyle(theStyleColor);

        buttonAddField.setOnAction(e -> addFields());

        buttonSave.setOnAction( event ->{
            AddCategory.newCategory(fields);
            removeFields();
        });

    }

    private VBox hBoxTop;

    private boolean noFieldMustBeEmpty(){

        boolean isFieldEmpty = false;

        for (TextField field : fields){
            if ( field.isVisible() && field.getText().trim().isEmpty()){
                isFieldEmpty = true;
                break;
            }
        }
        return isFieldEmpty;
    }

    private void addFields()  {

        if (!fields.contains(fieldOne)){
            fields.add(fieldOne);
        }

        if (!noFieldMustBeEmpty()){

            TextField textField = new TextField();
            textField.setPromptText("another category");

//            JFXButton button = new JFXButton("", new ImageView(new Image( "/com/delains/ui/images/remove.png",32,28,false,true )));
            JFXButton button = new JFXButton("", new FontAwesomeIconView(FontAwesomeIcon.REMOVE));

            HBox box = new HBox(10);
            box.setPadding(new Insets(10));
            box.getChildren().addAll(textField,button);

            hBoxTop.getChildren().add(box);
            ComponentWidth.setWidthOfTextField( textField, 300 );
            fields.add(textField);

            button.setButtonType( JFXButton.ButtonType.RAISED );

            button.setOnAction(event -> {

                fields.remove(box.getChildren().get(0));
                box.getChildren().removeAll(box.getChildren().get(0));
                hBoxTop.getChildren().remove(box);

            });

        }

        else {

            StageForAlerts.discontinue("warning", "values can not be empty");

        }

    }

    private void removeFields(){

        GeneralDialog.closeDialog();

    }

    private static void addButtonToColumnEdit(TableColumn<Category,Void> tableColumn){

        Callback<TableColumn<Category,Void>, TableCell<Category,Void>> cellCallback = param -> new TableCell<Category, Void>(){

             JFXButton btn = new JFXButton( "edit",FontAwesomeUtils.fontAwesomeUpdate() );

            {

                btn.setAlignment(Pos.CENTER);

                btn.setOnAction(event -> {

                    TableRow row = this.getTableRow();

                    Category category = tableView.getItems().get(row.getIndex());

                    HBox box = UpdateCategory.updateCategory(category);

                    GeneralDialog.showDialog("", "change category name from: " + category.getName(), box  );


                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if ( empty ) {
                    setGraphic( null );
                } else {
                    setGraphic( btn );
                }
            }
        };

        tableColumn.setCellFactory(cellCallback);
        tableView.getColumns().add(tableColumn);

    }

    private static void addButtonToColumnDelete(TableColumn<Category,Void> tableColumn){

        Callback<TableColumn<Category,Void>, TableCell<Category,Void>> cellCallback;
        cellCallback = param -> new TableCell<Category, Void>(){

             JFXButton btn = new JFXButton( "delete",FontAwesomeUtils.fontAwesomeRemove() );

            {

                btn.setOnAction(event -> {

                    TableRow row = this.getTableRow();

                    Category category = tableView.getItems().get(row.getIndex());

                    DeleteCategory.deleteCategory(category);

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if ( empty ) {
                    setGraphic( null );
                } else {
                    setGraphic( btn );
                }
            }
        };

        tableColumn.setCellFactory(cellCallback);
        tableView.getColumns().add(tableColumn);

    }


}
