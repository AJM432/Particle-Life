package com.example.particlelife;

import javafx.scene.paint.Color;

public final class Constants {



    private Constants() {
        // restrict instantiation
    }

    public static final int numSpecies = 6;

    // TODO: ensure this is the same size as colorList
    public static final double[][] attractionMatrix = {{-1, 5, 0, -5, 3, 6},
                                                        {-7, -2, 3, 1, -1, -6},
                                                        {3, -5, 4, 6, 2, -7},
                                                        {-5, -2, -3, -7, 3, 5},
                                                        {-5, -2, -3, -7, 2, 5},
                                                        {-5, -2, -3, -7, 3, 5}};
    public static final int numParticles = 1500;
    public static final int particleSize = 1;
    public static final double forceRange = 70;
    public static final double tHalf = 0.0001; // after how much time exactly half the velocity will be lost to friction
    public static final int phaseSize = 10;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;
}