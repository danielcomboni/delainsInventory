package com.delains.ui.item.categories;

import com.delains.dao.item.CategoryDAO;
import com.delains.model.items.Category;
import com.delains.ui.invoker.StageForAlerts;
import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class UpdateCategory {

    public static HBox updateCategory(Category category){

        HBox box = new HBox(10);
        box.setPadding(new Insets(10));

        TextField field = new TextField();
        field.setText(category.getName());
        box.getChildren().add(field);

        JFXButton button = new JFXButton("save changes");
        button.setStyle("-fx-text-fill: black;\n" +
                "\t-fx-font-family: \"Arial Narrow\";\n" +
                "\t-fx-font-weight: bold;\n" +
                "\t-fx-background-color: linear-gradient(#61a2b1, #2A5058);\n" +
                "\t-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        box.getChildren().add(button);

        button.setOnAction( event -> {

            button.setDisable(false);

            if (field.getText().trim().isEmpty()){
                StageForAlerts.discontinue("warning","category can not be empty");
                button.setDisable(false);
                return;
            }

            Category newCategory = new Category();
            newCategory.setId(category.getId());
            newCategory.setName(field.getText().trim());

            button.setDisable(true);

            saveChanges(newCategory, category);

        });
        return box;
    }

    private static void saveChanges(Category newCategory, Category categoryPrevious){
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {

                        Category category1 = CategoryDAO.updateCategory(newCategory);
                        System.out.println("new cat: " + category1);
                        CategoryData.data.set(CategoryData.data.indexOf(categoryPrevious), category1);

                        return null;
                    }
                };
            }
        };
        service.start();
    }

}
