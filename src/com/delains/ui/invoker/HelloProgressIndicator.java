package com.delains.ui.invoker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloProgressIndicator extends Application {

	 public static void main(String[] args) {
	    launch(args);
	 }

	 @Override
	 public void start(Stage stage) throws Exception {

	    Pane root = new Pane();
	    Scene scene = new Scene(root, 200, 200);

	    ProgressIndicator pi = new ProgressIndicator();
	    
	    for(int i=0; i<10;i++) {
	    	pi.setProgress(i);
	    }
	    
	    // changing color with css
	    pi.setStyle(" -fx-progress-color: red;");
	    // changing size without css
	    pi.setMinWidth(150);
	    pi.setMinHeight(150);

	    root.getChildren().add(pi);


	    stage.setScene(scene);
	    stage.show();
	 }
	}