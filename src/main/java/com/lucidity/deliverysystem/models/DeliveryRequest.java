package com.lucidity.deliverysystem.models;

public class DeliveryRequest {

    double preparationTime;
    Restaurant restaurant;
    Customer customer;

    public DeliveryRequest(int preparationTime, Restaurant restaurant, Customer customer) {
        this.preparationTime = preparationTime;
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(double preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}