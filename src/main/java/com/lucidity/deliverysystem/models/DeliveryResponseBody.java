package com.lucidity.deliverysystem.models;

public class DeliveryResponseBody {
    String path;
    double minTIme;

    public DeliveryResponseBody(String path, double minTIme) {
        this.path = path;
        this.minTIme = minTIme;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getMinTIme() {
        return minTIme;
    }

    public void setMinTIme(double minTIme) {
        this.minTIme = minTIme;
    }

    @Override
    public String toString() {
        return "DeliveryResponseBody{" +
                "path='" + path + '\'' +
                ", minTIme=" + minTIme +
                '}';
    }
}
