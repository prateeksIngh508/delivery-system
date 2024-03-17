package com.lucidity.deliverysystem.services;

import com.lucidity.deliverysystem.models.*;
import com.lucidity.deliverysystem.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeliveryOptimizationService {

    //Method to calculate time taken by delivery executive to travel between restaurant 1,customer 1,restaurant 2 and customer 2
    public double[][] calculateTime(Restaurant restaurant1, Customer customer1, Restaurant restaurant2, Customer customer2, double driverSpeed) {
        //latitudes for restaurant1,customer1,restaurant2 and customer2
        double[] x = {restaurant1.getX(), customer1.getX(), restaurant2.getX(), customer2.getX()};
        //longitudes for restaurant1,customer1,restaurant2 and customer2
        double[] y = {restaurant1.getY(), customer1.getY(), restaurant2.getY(), customer2.getY()};
        double[][] time = new double[4][4];
        for (int source = 0; source < 4; source++) {
            for (int destination = 0; destination < 4; destination++) {
                if (source != destination) {
                    //Finding distance by haversine formula and getting time
                    time[source][destination] = this.haversineDistance(x[source],y[source],x[destination], y[destination]) / driverSpeed;
                }
            }
        }
        return time;
    }

    //Function to find optimal path for two restaurants
    public DeliveryResponseBody findOptimalPathForTwoOrder(List<DeliveryRequest> deliveryRequests, DeliveryExecutive deliveryExecutive) {
        //Extracting restaurants coordinates, customer coordinates and preparation time for both restaurants from delivery requests
        DeliveryRequest deliveryRequest1 = deliveryRequests.get(0);
        Restaurant restaurant1 = deliveryRequest1.getRestaurant();
        Customer customer1 = deliveryRequest1.getCustomer();
        double preparationTimeRestaurant1 = deliveryRequest1.getPreparationTime();
        DeliveryRequest deliveryRequest2 = deliveryRequests.get(1);
        Restaurant restaurant2 = deliveryRequest2.getRestaurant();
        Customer customer2 = deliveryRequest2.getCustomer();
        double preparationTimeRestaurant2 = deliveryRequest2.getPreparationTime();
        //Time taken to travel by delivery executive between restaurant 1,customer 1,restaurant 2 and customer 2
        double[][] time = this.calculateTime(restaurant1, customer1, restaurant2, customer2, deliveryExecutive.getSpeed());
        //Time taken by delivery executive to reach Restaurant 1
        double driverToRestaurant1Time = Math.max(this.haversineDistance(deliveryExecutive.getX(), deliveryExecutive.getY(),restaurant1.getX(),  restaurant1.getY()) / deliveryExecutive.getSpeed(), preparationTimeRestaurant1);
        //Time taken by delivery executive to reach Restaurant 2
        double driverToRestaurant2Time = Math.max(this.haversineDistance(deliveryExecutive.getX(), deliveryExecutive.getY(),restaurant2.getX(), restaurant2.getY()) / deliveryExecutive.getSpeed(), preparationTimeRestaurant2);
        //Path with min time if delivery executive starts from restaurant 1
        DeliveryResponseBody deliveryResponseBodyR1 = this.pathTimeR1(time, driverToRestaurant1Time, preparationTimeRestaurant2);
        //Path with min time if delivery executive starts from restaurant 2
        DeliveryResponseBody deliveryResponseBodyR2 = this.pathTimeR2(time, driverToRestaurant2Time, preparationTimeRestaurant1);
        //Returning Path with min time taken
        return deliveryResponseBodyR1.getMinTIme() < deliveryResponseBodyR2.getMinTIme() ? deliveryResponseBodyR1 : deliveryResponseBodyR2;
    }

    private DeliveryResponseBody pathTimeR2(double[][] time, double driverRestaurant2time, double preparationTime) {
        //Calculating time taken for each path if delivery executive starts from restaurant 2 and returning most optimal path
        double timeForR2C2R1C1 = Math.max(driverRestaurant2time + time[Constants.R2][Constants.C2] + time[Constants.C2][Constants.R1], preparationTime) + time[Constants.R1][Constants.C1];
        double timeForR2R1C1C2 = Math.max(driverRestaurant2time + time[Constants.R2][Constants.R1], preparationTime) + time[Constants.R1][Constants.C1] + time[Constants.C1][Constants.C2];
        double timeForR2R1C2C1 = Math.max(driverRestaurant2time + time[Constants.R2][Constants.R1], preparationTime) + time[Constants.R1][Constants.C2] + time[Constants.C2][Constants.C1];
        if (timeForR2C2R1C1 < timeForR2R1C1C2 && timeForR2C2R1C1 < timeForR2R1C2C1) {
            return new DeliveryResponseBody(Constants.r2c2r1c1, timeForR2C2R1C1);
        } else if (timeForR2R1C1C2 < timeForR2R1C2C1) {
            return new DeliveryResponseBody(Constants.r2r1c1c2, timeForR2R1C1C2);
        } else {
            return new DeliveryResponseBody(Constants.r2r1c2c1, timeForR2R1C2C1);
        }
    }

    private DeliveryResponseBody pathTimeR1(double[][] time, double driverRestaurant1time, double preparationTime) {
        //Calculating time taken for each path if delivery executive starts from restaurant 1 and returning most optimal path
        double timeForR1C1R2C2 = Math.max(driverRestaurant1time + time[Constants.R1][Constants.C1] + time[Constants.C1][Constants.R2], preparationTime) + time[Constants.R2][Constants.C2];
        double timeForR1R2C1C2 = Math.max(driverRestaurant1time + time[Constants.R1][Constants.R2], preparationTime) + time[Constants.R2][Constants.C1] + time[Constants.C1][Constants.C2];
        double timeForR1R2C2C1 = Math.max(driverRestaurant1time + time[Constants.R1][Constants.R2], preparationTime) + time[Constants.R2][Constants.C2] + time[Constants.C2][Constants.C1];
        if (timeForR1C1R2C2 < timeForR1R2C1C2 && timeForR1C1R2C2 < timeForR1R2C2C1) {
            return new DeliveryResponseBody(Constants.r1c1r2c2, timeForR1C1R2C2);
        } else if (timeForR1R2C1C2 < timeForR1R2C2C1) {
            return new DeliveryResponseBody(Constants.r1r2c1c2, timeForR1R2C1C2);
        } else {
            return new DeliveryResponseBody(Constants.r1r2c2c1, timeForR1R2C2C1);
        }
    }

    //Haversine formula to find distance between two coordinates
    public double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Constants.earthRadius * c; // convert to meters
    }

}