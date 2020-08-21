package com.delains.ui.sales;

import com.delains.dao.pos.DailyTotalSalesDAORetrieve;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.sales.DailyTotalSalesByItem;
import com.delains.ui.sales.receiptprinting.PrinterService;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class DailySalesTotalUIByItem extends TableView<DailyTotalSalesByItem> {

    public DailySalesTotalUIByItem(){

        this.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        this.setTableMenuButtonVisible(true);

        TableColumn<DailyTotalSalesByItem, String> columnDate = new TableColumn<>("Date");
        columnDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDate()));

        this.getColumns().add(columnDate);

        TableColumn<DailyTotalSalesByItem, String> columnTotalSales = new TableColumn<>("Total sales");
        columnTotalSales.setMinWidth(100);
        columnDate.setMinWidth(100);
        columnTotalSales.setCellValueFactory(param -> {
            String totalStr = NumberFormatting.formatToEnglish(param.getValue().getTotalSales().toString());
            return new SimpleStringProperty(totalStr);
        });

        this.getColumns().add(columnTotalSales);

        TableColumn<DailyTotalSalesByItem, String> columnItem = new TableColumn<>("Item");
        columnItem.setCellValueFactory( param ->  (


                new SimpleStringProperty(

                        param.getValue().itemProperty().getValue().itemNameProperty().getValue()
                        +
                                "-"
                        +
                                param.getValue().itemProperty().getValue().packageNameProperty().getValue()
                        +
                                "-"
                        +
                                NumberFormatting.formatToEnglish(param.getValue().itemProperty().getValue().packageVolumeProperty().getValue().toString())


                )



                ) );
        this.getColumns().add(columnItem);

        addButtonToTable(this);


        Service<Void> service;
        service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        getItems().clear();
                        ObservableList<DailyTotalSalesByItem> dailyTotalSales = FXCollections.observableArrayList();
                        dailyTotalSales.setAll(DailyTotalSalesDAORetrieve.FindDailyTotalSalesByItem());
                        setItems(dailyTotalSales);
                        return null;
                    }
                };
            }
        };
        service.start();


        JFXButton buttonPrintOne = new JFXButton("Print selected");

        HBox box = new HBox();
        box.getChildren().addAll(buttonPrintOne);

    }

    private static void addButtonToTable( TableView < DailyTotalSalesByItem > tableView ) {
        TableColumn < DailyTotalSalesByItem, Void > colBtn = new TableColumn <>( "Print" );
        colBtn.setMinWidth(100);
        Callback< TableColumn < DailyTotalSalesByItem, Void >, TableCell< DailyTotalSalesByItem, Void >> cellFactory = new Callback < TableColumn < DailyTotalSalesByItem, Void >, TableCell < DailyTotalSalesByItem, Void > >() {
            @Override
            public TableCell < DailyTotalSalesByItem, Void > call( final TableColumn < DailyTotalSalesByItem, Void > param ) {
                return new TableCell < DailyTotalSalesByItem, Void >() {

                    private final Button btn = new Button( "print" );

                    {
                        btn.setOnAction( ( ActionEvent event ) -> {


                            Service<Void> service;
                            service = new Service<Void>() {
                                @Override
                                protected Task<Void> createTask() {
                                    return new Task<Void>() {
                                        @Override
                                        protected Void call() {

                                            DailyTotalSalesByItem data = getTableView().getItems().get( getIndex() );
                                            String  values = "the total sales for item : " + data.getItem().getItemName()

                                            .concat(" - ").concat(data.getItem().getPackageName()).concat(" - ")
                                                    .concat(data.getItem().getPackageVolume().toString()).concat(" - ")
                                                    .concat(data.getItem().getUnitOfMeasurement());

                                            values = values.concat(data.getDate().concat("\n\n" +
                                                    "\n\n"));


                                            values = values.concat("Shs " + NumberFormatting.formatToEnglish(data.getTotalSales().toString()));

                                            values = values.concat("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                                            PrinterJob job = PrinterJob.createPrinterJob();

                                            new PrinterService().printString(job.getPrinter().getName(), values);

                                            job.endJob();
                                            return null;
                                        }
                                    };
                                }
                            };
                            service.start();

                        } );
                    }

                    @Override
                    public void updateItem( Void item, boolean empty ) {
                        super.updateItem( item, empty );
                        if ( empty ) {
                            setGraphic( null );
                        } else {
                            setGraphic( btn );
                        }
                    }
                };
            }
        };

        colBtn.setCellFactory( cellFactory );

        tableView.getColumns().add( colBtn );

    }

}
