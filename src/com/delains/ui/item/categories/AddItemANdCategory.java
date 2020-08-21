package com.delains.ui.item.categories;

import com.delains.dao.item.ItemAndCategoryDAO;
import com.delains.model.items.Category;
import com.delains.model.items.Item;
import com.delains.model.items.ItemAndCategory;
import com.delains.ui.invoker.StageForAlerts;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class AddItemANdCategory {


    public static void addItemAndCategory(Category category, Item item){
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {

                return new Task<Void>() {

                    @Override
                    protected Void call() {

                        // check if item already exists under the ItemAndCategory

                            ItemAndCategory itemAndCategory = new ItemAndCategory(BigDecimal.ZERO, category,item);
                            ItemAndCategory itemAndCategory1 = ItemAndCategoryDAO.newItemAndCategory(itemAndCategory);

                            ItemAndCategoryData.data.add(itemAndCategory1);

                        return null;

                    }
                };
            }
        };

        service.start();
    }

    public static boolean checkIfCategoryAlreadySet(Item item){
        return ItemAndCategoryData.data.stream().noneMatch(itemAndCategory -> itemAndCategory.getItem().getId().equals(item.getId()));
    }

}
