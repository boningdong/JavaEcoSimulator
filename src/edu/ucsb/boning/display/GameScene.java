package edu.ucsb.boning.display;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class GameScene {

    private BorderPane pane;
    private Scene scene;
    private Pane canvasFrame;
    private Canvas canvas;

    public GameScene(double width, double height) {
        // Create Canvas

        canvasFrame = new Pane();
        canvas = new Canvas(width, height);
        canvasFrame.getChildren().add(canvas);

        // Create Layout (Parent)
        pane = new BorderPane();
        pane.setCenter(canvasFrame);
        pane.setPadding(new Insets(5, 5, 5, 5));

        // Create Scene
        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    public GraphicsContext getGraphicContext() {
        return canvas.getGraphicsContext2D();
    }
}
