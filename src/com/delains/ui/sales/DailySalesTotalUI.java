package com.delains.ui.sales;

import com.delains.dao.pos.DailyTotalSalesDAORetrieve;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.sales.DailyTotalSales;
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

public class DailySalesTotalUI  extends TableView<DailyTotalSales> {

    public DailySalesTotalUI(){

        this.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        this.setTableMenuButtonVisible(true);

        TableColumn<DailyTotalSales, String> columnDate = new TableColumn<>("Date");
        columnDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDate()));

        this.getColumns().add(columnDate);

        TableColumn<DailyTotalSales, String> columnTotalSales = new TableColumn<>("Total sales");
        columnTotalSales.setMinWidth(100);
        columnDate.setMinWidth(100);
        columnTotalSales.setCellValueFactory(param -> {
            String totalStr = NumberFormatting.formatToEnglish(param.getValue().getTotalSales().toString());
            return new SimpleStringProperty(totalStr);
        });

        this.getColumns().add(columnTotalSales);

        addButtonToTable(this);


        Service<Void> service;
        service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        getItems().clear();
                        ObservableList<DailyTotalSales> dailyTotalSales = FXCollections.observableArrayList();
                        dailyTotalSales.setAll(DailyTotalSalesDAORetrieve.FindDailyTotalSales());
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

    private static void addButtonToTable( TableView < DailyTotalSales > tableView ) {
        TableColumn < DailyTotalSales, Void > colBtn;
        colBtn = new TableColumn <>( "Print" );
        colBtn.setMinWidth(100);
        Callback < TableColumn < DailyTotalSales, Void >, TableCell< DailyTotalSales, Void >> cellFactory = new Callback < TableColumn < DailyTotalSales, Void >, TableCell < DailyTotalSales, Void > >() {
            @Override
            public TableCell < DailyTotalSales, Void > call( final TableColumn < DailyTotalSales, Void > param ) {
                return new TableCell < DailyTotalSales, Void >() {

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

                                            DailyTotalSales data = getTableView().getItems().get( getIndex() );
                                            String  values = "the total sales for date: " + data.getDate().concat("\n\n" +
                                                    "\n\n");
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
