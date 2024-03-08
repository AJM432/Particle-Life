package com.example.particlelife;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Particle extends Circle {
    private int _specie; // color index
    private Point2D _position;
    private Point2D _velocity;
    private Point2D _acceleration;
    public Particle(double x, double y, double vx, double vy, int specie){
        setPosition(x, y);
        setVelocity(vx, vy);
        setAcceleration(0, 0);
        setSpecie(specie); // number representing color from color array map
        setRadius(Constants.particleSize);
    }

    public double distanceTo(Particle p){
        return sqrt(pow(getCenterX() - p.getCenterX(), 2) + pow(getCenterY() - p.getCenterY(), 2));
    }
    public Point2D getForce(Particle p){
        double distance = distanceTo(p);
        if(distance == 0 | distance > Constants.forceRange){
            return Point2D.ZERO;
        }
        double normalizedDistance = distance/Constants.forceRange;
        double attraction = Constants.attractionMatrix[getSpecie()][p.getSpecie()];
        Point2D unitVector = p._position.subtract(_position);
        unitVector = unitVector.multiply(1/unitVector.magnitude());
        double force = forceFunction(normalizedDistance, attraction);
        return unitVector.multiply(force*Constants.forceRange); //TODO: optimize by multiplying forceRange at very end :)
    }

    public double forceFunction(double distance, double attraction){
        double beta = 0.3;
        if(distance < beta){
            return distance/beta -1;
        }
        else if(beta < distance & distance < 1){
            return attraction*(1-Math.abs(2*distance-1-beta)/(1-beta));
        }
        else{
            return 0;
        }
    }


    // setters
    public void setPosition(double x, double y){
        _position = new Point2D(x, y);
        setCenterX(x);
        setCenterY(y);
    }
    public void setVelocity(double vx, double vy){
        _velocity = new Point2D(vx, vy);
    }
    public void setAcceleration(double ax, double ay){
        _acceleration = new Point2D(ax, ay);
    }
    public void setSpecie(int specie){
        _specie = specie;
    }
    
    // getters
    public double getVx(){
        return _velocity.getX();
    }
    public double getVy(){
        return _velocity.getY();
    }
    public int getSpecie(){
        return _specie;
    }

    public void updateAcceleration(Particle b) {
        Point2D incrementalAcceleration = getForce(b);
        _acceleration = _acceleration.add(incrementalAcceleration);
    }

    public void update(double delta){

        double friction = Math.pow((1.0/2.0), delta/Constants.tHalf);
        _velocity = _velocity.multiply(friction);
        _velocity = _velocity.add(_acceleration.multiply(delta));
        _position = _position.add(_velocity.multiply(delta).add(_acceleration.multiply((1.0/2.0)*delta*delta)));
        setCenterX(_position.getX());
        setCenterY(_position.getY());
    }
}
