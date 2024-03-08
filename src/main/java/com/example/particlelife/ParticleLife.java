package com.example.particlelife;

import java.util.Random;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class ParticleLife extends Application
        implements EventHandler <KeyEvent>
{
    // delta time setup
    long previousTime = System.nanoTime();

    // constants
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    final int WIDTH = (int)screenBounds.getWidth();
    final int HEIGHT = (int)screenBounds.getHeight();

    Particle[] particles = new Particle[Constants.numParticles];

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage){

        stage.setTitle("Particle Life");


        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

        Random rand = new Random();


        for(int i=0; i < Constants.numParticles; i++){
            double x = rand.nextDouble((WIDTH -100) - 100) + 100;
            double y = rand.nextDouble((HEIGHT- 100) - 100) +100;
            //formula for random range = rand(max-min+1) + min
//            double vx = rand.nextInt(11) - 5;
//            double vy = rand.nextInt(11) - 5;
            int specie = rand.nextInt(Constants.colorList.length);
            particles[i] = new Particle(x, y, 0, 0, specie);
            particles[i].setFill(Constants.colorList[specie]);
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
//                    tick();
//                    delta--;

                // UPDATE
                for(int i=0; i < Constants.numParticles; i++) {
                    Particle a = particles[i];
                    // reset acceleration
                    a.setAcceleration(0, 0);

                    for(int j=0; j < Constants.numParticles; j++){
                        Particle b = particles[j];
                        a.updateAcceleration(b);
                        }
                    // update position
                    a.update(deltaTime);

                    // border wrap
                    double newX = a.getCenterX();
                    double newY = a.getCenterY();

                    if(newX > WIDTH) {
                        newX = Constants.phaseSize;
                    }
                    else if(newX < 0) {
                        newX = WIDTH - Constants.phaseSize;
                    }
                    if(newY > HEIGHT) {
                        newY = Constants.phaseSize;
                    }
                    else if(newY < 0) {
                        newY = HEIGHT - Constants.phaseSize;
                    }
                    a.setPosition(newX, newY);
                }

                previousTime = currentTime;
            }
        };

        animator.start();

    }

    @Override
    public void handle(KeyEvent arg0) {
    }
}