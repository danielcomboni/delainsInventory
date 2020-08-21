package com.delains.ui.test;

import com.delains.dao.item.ItemHibernation;
import com.delains.model.items.Item;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AutoMainTest extends Application {

	@Override
	public void start( Stage primaryStage ) throws Exception {
		// TODO Auto-generated method stub

		StackPane pane = new StackPane();

		// new AutoCompleteTextField().getEntries().addAll(Arrays.asList("AA",
		// "AB", "AC","BCA"));

		// AutoCompleteTextField textField = new AutoCompleteTextField();


		TextField field = new TextField();
		AutocompletionlTextField textField = new AutocompletionlTextField();

		field = textField;

		ObservableList < String > strings = FXCollections.observableArrayList();
		// strings.addAll("AA", "AB", "AC", "BCA");
		strings.addAll( "Abacate", "Abacaxi", "Ameixa", "Amora", "Araticum", "Atemoia", "Avocado", "Banana prata",
				"Caju", "Cana descascada", "Caqui", "Caqui Fuyu", "Carambola", "Cereja", "Coco verde", "Figo",
				"Figo da Índia", "Framboesa", "Goiaba", "Graviola", "Jabuticaba", "Jambo", "Jambo rosa", "Jambolão",
				"Kino (Kiwano)", "Kiwi", "Laranja Bahia", "Laranja para suco", "Laranja seleta", "Laranja serra d’água",
				"Laranjinha kinkan", "Lichia", "Lima da pérsia", "Limão galego", "Limão Taiti", "Maçã argentina",
				"Maçã Fuji", "Maçã gala", "Maçã verde", "Mamão formosa", "Mamão Havaí", "Manga espada", "Manga Haden",
				"Manga Palmer", "Manga Tommy", "Manga Ubá", "Mangostim", "Maracujá doce", "Maracujá para suco",
				"Melancia", "Melancia sem semente", "Melão", "Melão Net", "Melão Orange", "Melão pele de sapo",
				"Melão redinha", "Mexerica carioca", "Mexerica Murcote", "Mexerica Ponkan", "Mirtilo", "Morango",
				"Nectarina", "Nêspera ou ameixa amarela", "Noni", "Pera asiática", "Pera portuguesa", "Pêssego",
				"Physalis", "Pinha", "Pitaia", "Romã", "Tamarilo", "Tamarindo", "Uva red globe", "Uva rosada",
				"Uva Rubi", "Uva sem semente", "Abobora moranga", "Abobrinha italiana", "Abobrinha menina", "Alho",
				"Alho descascado", "Batata baroa ou cenoura amarela", "Batata bolinha", "Batata doce", "Batata inglesa",
				"Batata yacon", "Berinjela", "Beterraba", "Cebola bolinha", "Cebola comum", "Cebola roxa", "Cenoura",
				"Cenoura baby", "Couve flor", "Ervilha", "Fava", "Gengibre", "Inhame", "Jiló", "Massa de alho",
				"Maxixe", "Milho", "Pimenta biquinho fresca", "Pimenta de bode fresca", "Pimentão amarelo",
				"Pimentão verde", "Pimentão vermelho", "Quiabo", "Repolho", "Repolho roxo", "Tomate cereja",
				"Tomate salada", "Tomate sem acidez", "Tomate uva", "Vagem", "Agrião", "Alcachofra", "Alface",
				"Alface americana", "Almeirão", "Brócolis", "Broto de alfafa", "Broto de bambu", "Broto de feijão",
				"Cebolinha", "Coentro", "Couve", "Espinafre", "Hortelã", "Mostarda", "Rúcula", "Salsa", "Ovos brancos",
				"Ovos de codorna", "Ovos vermelhos" );

		// textField.getEntries().addAll(Arrays.asList("AA", "AB", "AC", "BCA"));

		ObservableList < Item > items = ItemHibernation.findAllItemsObservableList();

		// ObservableList < String > strings = FXCollections.observableArrayList();

		for ( Item item : items ) {
			strings.add( item.getItemName().concat( " - " ).concat( item.getItemDescription() ).concat( " - " )
					.concat((String) item.getPackageVolume().toString()) );
		}

		textField.getEntries().addAll( strings );
		pane.getChildren().add( field );
		Scene scene = new Scene( pane );
		primaryStage.setScene( scene );
		primaryStage.show();

	}

	public static void main( String [ ] args ) {
		launch( args );
	}

}