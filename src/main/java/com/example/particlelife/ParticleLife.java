package com.example.particlelife;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class ParticleLife extends Application
{
    public static double[][] attractionMatrix;
    // delta time setup
    long previousTime = System.nanoTime();

    // constants
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();

//    final int WIDTH = (int)screenBounds.getWidth();

    Particle[] particles = new Particle[Constants.numParticles];

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage.setTitle("Particle Life");


        Group root = new Group();
        Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT, Color.BLACK);

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
}