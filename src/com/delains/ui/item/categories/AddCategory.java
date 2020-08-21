package com.delains.ui.item.categories;

import com.delains.dao.item.CategoryDAO;
import com.delains.model.items.Category;
import com.delains.ui.invoker.StageForAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class AddCategory {

    public static void newCategory(ObservableList<TextField> fields){

        // make sure all fields are not empty
        for (TextField field : fields){

            if (field.getText().trim().isEmpty()){

                StageForAlerts.discontinue("category value(s) can not be empty", "Please make sure the category value(s) are provided");
                return;

            }

        }

        // set values for batch processing
        ObservableList<Category> categories = FXCollections.observableArrayList();

        for(TextField field : fields){
            categories.add(new Category(BigDecimal.ZERO,field.getText().trim()));
        }

        categories.stream().forEach(category -> {
            System.out.println("each: " + category.getName());
        });


        // make sure the values are not the same and/or not already existing
        Set<String> categoryNamesToSave = new HashSet<>();
        for (Category category : categories){
            categoryNamesToSave.add(category.getName());
        }
        categoryNamesToSave.stream().forEach(s -> {
            System.out.println("to: " + s);
        });


        ObservableList<String> categoryNamesAlreadySaved = FXCollections.observableArrayList();

        if (!CategoryData.data.isEmpty()){

            for (Category category : CategoryData.data){
                categoryNamesAlreadySaved.add(category.getName());
            }

        }



        for (String name : categoryNamesAlreadySaved){
            if (categoryNamesToSave.contains(name)){
                StageForAlerts.discontinue("warning", "The category " + name + " is already existing");
                return;
            }
        }


        // save to DB
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {

                        ObservableList<Category>  categories1 = CategoryDAO.newCategories(categories);
                        CategoryData.data.addAll(categories1);

                        return null;
                    }
                };
            }
        };

        service.start();

    }

}
