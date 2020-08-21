//package com.delains.ui.processing;
//
//import javafx.application.Platform;
//import javafx.concurrent.Task;
//import javafx.scene.control.*;
//
//import javafx.scene.layout.VBox;
//
//public class Processing {
//
//    private static Dialog<String> dialog = new Dialog<String>();
//    private static Label label = new Label("");
//    private static ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
//    private static String processingMessage;
//    private static ProgressBar progressBar = new ProgressBar();
//    public static final String DEFAULT_MESSAGE = "please wait while processing....";
//
//    public static String getProcessingMessage() {
//        return processingMessage;
//    }
//
//    public static void setProcessingMessage(String processingMessage) {
//        Processing.processingMessage = processingMessage;
//    }
//
//    public static void progressDialog(boolean stillProcessing){
//        progressBar.setPrefSize(220, 100);
//
//        VBox box = new VBox();
//        box.getChildren().add(label);
//        box.getChildren().add(progressBar);
//
//        dialog.getDialogPane().setContent(box);
//        dialog.setTitle("processing");
//        dialog.setContentText("please wait while processing...");
//
//        if (!dialog.getDialogPane().getButtonTypes().contains(type)){
//
//            dialog.getDialogPane().getButtonTypes().add(type);
//
//        }
//
//        if (stillProcessing){
//            label.setText(getProcessingMessage());
//            dialog.showAndWait();
//        }else {
//            label.setText("complete");
//            dialog.close();
//        }
//
//    }
//
//    public static void startUpdateDaemonTask(Runnable runner) {
//
//        Task task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                while (true) {
//
//                    Platform.runLater(runner);
//
//                    Thread.sleep(1000);
//
//                }
//
//            }
//        };
//
//        Thread th = new Thread(task);
//        th.setDaemon(true);
//        th.start();
//        th.interrupt();
//    }
//
//    public static void closeDialog(){
//
//            dialog.close();
//
//    }
//
//}
