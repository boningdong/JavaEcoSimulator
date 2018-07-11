package edu.ucsb.boning.game;
import edu.ucsb.boning.display.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class BinaryEcosystem extends Application {

    // Game Settings
    private final int DEAULT_WIDTH = 800;
    private final int DEAULT_HEIGHT = 600;

    // Game Display
    private GameScene graphic = new GameScene(DEAULT_WIDTH,DEAULT_HEIGHT);

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Binary Ecosystem Simulator");
        primaryStage.setScene(graphic.getScene());
        primaryStage.show();

        // Demo draw
        graphic.getGraphicContext().fillOval(20, 30, 10, 10);
        //graphic.getGraphicContext().clearRect(0, 0, DEAULT_WIDTH, DEAULT_HEIGHT);
    }

    public static void main(String[] args){
        launch(args);
    }
}
