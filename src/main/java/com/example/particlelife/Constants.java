package com.example.particlelife;

import javafx.scene.paint.Color;

public final class Constants {



    private Constants() {
        // restrict instantiation
    }

    public static final double tHalf = 0.1; // after how much time exactly half the velocity will be lost to friction
    public static final Color[] colorList = {Color.WHITE, Color.RED, Color.GREEN};
    public static final double[][] attractionMatrix = {{-0.1, 0.5, 0.9}, {-0.7, -0.2, 0.3}, {0.3, -0.5, 0.4}}; // TODO: ensure this is the same size as colorList
    public static final int numParticles = 1000;
    public static final int particleSize = 1;
    public static final double forceRange = 100;
}