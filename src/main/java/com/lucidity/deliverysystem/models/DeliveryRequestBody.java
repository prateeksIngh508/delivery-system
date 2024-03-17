package com.lucidity.deliverysystem.models;

import java.util.List;

public class DeliveryRequestBody {

    List<DeliveryRequest> deliveryRequests;
    DeliveryExecutive deliveryExecutive;

    public DeliveryRequestBody(List<DeliveryRequest> deliveryRequests, DeliveryExecutive deliveryExecutive) {
        this.deliveryRequests = deliveryRequests;
        this.deliveryExecutive = deliveryExecutive;
    }

    public List<DeliveryRequest> getDeliveryRequests() {
        return deliveryRequests;
    }

    public void setDeliveryRequests(List<DeliveryRequest> deliveryRequests) {
        this.deliveryRequests = deliveryRequests;
    }

    public DeliveryExecutive getDeliveryExecutive() {
        return deliveryExecutive;
    }

    public void setDeliveryExecutive(DeliveryExecutive deliveryExecutive) {
        this.deliveryExecutive = deliveryExecutive;
    }

}
