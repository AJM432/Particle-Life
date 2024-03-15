package com.example.particlelife;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ParticleLife extends Application
{
    private double scaleValue = 1.0;


    public static double[][] attractionMatrix;
    // delta time setup
    long previousTime = System.nanoTime();

    // constants
    Particle[] particles = new Particle[Constants.numParticles];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage.setTitle("Particle Life");


        Group root = new Group();
        Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT, Color.BLACK);
        scene.setOnScroll(new ZoomHandler(root));
        scene.setOnMouseClicked(new MouseMiddleClickHandler(root));

        Random rand = new Random();

        Color[] colorList = new Color[Constants.numSpecies];
        for(int i=0; i < Constants.numSpecies; i++){
            colorList[i] = Color.color(Math.random(), Math.random(), Math.random());
        }
        attractionMatrix = new double[Constants.numSpecies][Constants.numSpecies];
        for(int i=0; i < Constants.numSpecies; i++) {
            for (int j = 0; j < Constants.numSpecies; j++) {
                attractionMatrix[i][j] = rand.nextInt(20)  - 10;
            }
        }


        for(int i=0; i < Constants.numParticles; i++){
            double x = rand.nextDouble((Constants.WIDTH -100) - 100) + 100;
            double y = rand.nextDouble((Constants.HEIGHT- 100) - 100) +100;
            //formula for random range = rand(max-min+1) + min
            int specie = rand.nextInt(Constants.numSpecies);
            particles[i] = new Particle(x, y, 0, 0, specie);
            particles[i].setFill(colorList[specie]);
            root.getChildren().add(particles[i]);
        }

        stage.setScene(scene);
        stage.show();


        AnimationTimer animator = new AnimationTimer(){

            @Override
            public void handle(long arg0) {
                long currentTime = System.nanoTime();
                long elapsedNanos = currentTime - previousTime;
                double deltaTime = elapsedNanos / 1_000_000_000.0; /* to seconds *///                if(delta >= 1){

                for(int i=0; i < Constants.numParticles; i++) {
                    Particle a = particles[i];
                    a.update(deltaTime, particles);
                }

                previousTime = currentTime;
            }
        };

        animator.start();

    }
    // Event handler class for zooming
    private class ZoomHandler implements EventHandler<ScrollEvent> {
        final private Group root;

        public ZoomHandler(Group root) {
            this.root = root;
        }

        @Override
        public void handle(ScrollEvent event) {
            double zoomIntensity = 0.2;
            double scrollDelta = event.getDeltaY();

            double mouseX = event.getX();
            double mouseY = event.getY();

            Point2D pivot = root.sceneToLocal(mouseX, mouseY);

            if (scrollDelta > 0) {
                // Zoom in
                scaleValue = Math.max(0.1, scaleValue - zoomIntensity);
            } else {
                // Zoom out
                scaleValue = Math.min(10, scaleValue + zoomIntensity);
            }

            // Apply scaling transformation to the root node
            root.setScaleX(scaleValue);
            root.setScaleY(scaleValue);

            // Adjust translate to keep the pivot point stationary
            Point2D newPivot = root.sceneToLocal(mouseX, mouseY);
            double deltaX = (-pivot.getX() + newPivot.getX()) * scaleValue;
            double deltaYUpdated = (-pivot.getY() + newPivot.getY()) * scaleValue;

            root.setTranslateX(root.getTranslateX() + deltaX);
            root.setTranslateY(root.getTranslateY() + deltaYUpdated);

            event.consume(); // Consume the event to prevent it from being handled further
        }
    }
    // Event handler class for mouse middle-click
    private class MouseMiddleClickHandler implements EventHandler<MouseEvent> {
        final private Group root;

        public MouseMiddleClickHandler(Group root) {
            this.root = root;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.MIDDLE) {
                // Reset scale and translation
                scaleValue = 1.0;
                root.setScaleX(scaleValue);
                root.setScaleY(scaleValue);
                root.setTranslateX(0);
                root.setTranslateY(0);
            }
        }
    }
}
