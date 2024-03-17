package com.lucidity.deliverysystem.models;

public class DeliveryExecutive {
    private double x, y;
    private int id;
    private double speed;

    public DeliveryExecutive(double x, double y, int id, double speed) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.speed = speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }
}
