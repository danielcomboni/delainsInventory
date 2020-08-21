package com.delains.ui.item.categories;

import com.delains.dao.item.CategoryDAO;
import com.delains.model.items.Category;
import com.delains.ui.invoker.StageForAlerts;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DeleteCategory {

    public static void deleteCategory(Category category){

        StageForAlerts.discontinue("confirm", "Are you sure you want to delete this category, " + category.getName() +" ?");
        if (StageForAlerts.isDecide()){


            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call()  {

                            CategoryDAO.deleteCategory(category.getId());

                            CategoryData.data.remove(category);

                            return null;
                        }
                    };
                }
            };
            service.start();

        }

    }

}
