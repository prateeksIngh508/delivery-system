package com.lucidity.deliverysystem.controllers;

import com.lucidity.deliverysystem.models.DeliveryRequestBody;
import com.lucidity.deliverysystem.models.DeliveryResponseBody;
import com.lucidity.deliverysystem.services.DeliveryOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryOptimizationController {
    @Autowired
    DeliveryOptimizationService deliveryOptimizationService;


    @PostMapping("/deliver")
    public DeliveryResponseBody deliver(@RequestBody DeliveryRequestBody deliveryRequestBody) {

        return deliveryOptimizationService.findOptimalPathForTwoOrder(deliveryRequestBody.getDeliveryRequests(), deliveryRequestBody.getDeliveryExecutive());
    }
}