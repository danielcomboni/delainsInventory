package com.delains.ui.test;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.pricing.PricingHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.pos.ItemPriceBarcode;
import com.delains.model.pricing.Pricing;
import com.delains.ui.sales.auto.AutoCompleteTextFieldBarcodeAndItemMap;
import com.delains.ui.test.auto.Address;
import com.delains.ui.test.auto.AutoCompleteTextField;
import javafx.event.Event;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.math.BigDecimal;
import java.util.*;

public class AutoCompleteFieldObtainResult {

	private AutoCompleteFieldObtainResult() {

	}

	private static List<ItemPriceBarcode> itemPriceBarcodes;

	public static List<ItemPriceBarcode> getItemPriceBarcodes() {
		return itemPriceBarcodes;
	}

	public static void setItemPriceBarcodes(List<ItemPriceBarcode> itemPriceBarcodes) {
		AutoCompleteFieldObtainResult.itemPriceBarcodes = itemPriceBarcodes;
	}

	public static void setStrValue(List<ItemPriceBarcode> itemPriceBarcodes1){

		for(int i = 0; i<itemPriceBarcodes1.size(); i++){
			Map<Pricing,String> map = new LinkedHashMap<>();

			Item it = itemPriceBarcodes1.get(i).getItem();
			Pricing pr = itemPriceBarcodes1.get(i).getPricing();

			String str = "";
			str = str.concat(

					it.getItemName()
							.concat(" ")
							.concat(it.getPackageName())
							.concat(" ")
							.concat(NumberFormatting.formatToEnglish(it.getPackageVolume().toString())));

			map.put(pr, str);
			if (i == 0){
				setStrPricingToItems(map);
			}else {
				getStrPricingToItems().put(pr, str);
			}

		}
	}

	public static void refreshItemsToSearch(){
List<ItemPriceBarcode> entries = new ArrayList<>();
		setItemPriceBarcodes(entries);
		setSearchItems();
	}

	public static List<ItemPriceBarcode> setSearchItems(){


		if (getItemPriceBarcodes() == null || getItemPriceBarcodes().isEmpty()){

			List<ItemPriceBarcode> entries = new ArrayList<>();

			for(
					Map.Entry<String,Item> entry : ItemHibernation.barcodesMappedToThierItems().entrySet()){

				Pricing pricing = PricingHibernation.mappingItemIDsAsIDOfPrice().get( entry.getValue().getId() );

				entries.add(

						new ItemPriceBarcode(new Item(

								entry.getValue().getId(), entry.getValue().getItemName(),
								entry.getValue().getItemDescription(), entry.getValue().getUnitOfMeasurement(),
								entry.getValue().getBarcode(),
								entry.getValue().getPackageName(),
								(BigDecimal) entry.getValue().getPackageVolume()

						), pricing)

				);

			}

			setItemPriceBarcodes(entries);

			setStrValue(getItemPriceBarcodes());

			return getItemPriceBarcodes();

		}else {


			setStrValue(getItemPriceBarcodes());
			return getItemPriceBarcodes();
		}





	}

	private static Map<Pricing, String> strPricingToItems;

	public static Map<Pricing, String> getStrPricingToItems() {
		return strPricingToItems;
	}

	public static void setStrPricingToItems(Map<Pricing, String> strPricingToItems) {
		AutoCompleteFieldObtainResult.strPricingToItems = strPricingToItems;
	}

	private static String fieldResultObtained;

	public static String getFieldResultObtained() {
		return fieldResultObtained;
	}

	public static void setFieldResultObtained( String fieldResultObtained ) {
		AutoCompleteFieldObtainResult.fieldResultObtained = fieldResultObtained;
	}

	public static String[] words(){
		return new String[]{"Daniel","Danilo","David"};
	}

	public static void search(TextField inputField){

		Collection<Item> items = ItemHibernation.barcodesMappedToThierItems().values();
		List<Item> itemList = new LinkedList<>(items);

		List<Address> entries = new ArrayList<>();

		entries.add(new Address(50, "Main Street", "Oakville", "Ontario"));
		entries.add(new Address(3, "Fuller Road", "Toronto", "Ontario"));

		AutoCompleteTextFieldBarcodeAndItemMap<Item> text = new AutoCompleteTextFieldBarcodeAndItemMap<>(itemList);

		inputField = text;

		// ObservableList < String > strings = FXCollections.observableArrayList();

		text.getEntryMenu().setOnAction(e ->
		{
			((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event ->
			{
				if (text.getLastSelectedObject() != null)
				{
					text.setText(text.getLastSelectedObject().toString());
}
			});
		});

	}

}
