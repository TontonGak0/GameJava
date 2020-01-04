package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application; 
import javafx.scene.Scene; 
import javafx.scene.layout.Pane; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Rectangle; 
import javafx.stage.Stage; 
import view.ViewManager;

public class Main extends Application {
    @Override 
    public void start(Stage primaryStage) throws Exception { 
        try {
        	
            ViewManager manager = new ViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.show();
           
    } catch(Exception e) {
        e.printStackTrace();
        }
    }
    public static void main(String... args) { 
        launch(args); 
    } 
}