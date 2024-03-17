Introduction

This document guides delivery executives on using the delivery optimization service to receive optimized delivery routes for two orders. The service leverages the Haversine formula to calculate distances and recommends the fastest path to fulfill both orders efficiently.

Prerequisites

A device with internet access and a command-line interface (CLI) tool like cURL.
Familiarity with basic cURL commands and JSON formatting.
API Endpoint

The delivery optimization service is accessible at the following HTTP endpoint:

```http://localhost:8080/deliver ```


This example assumes no authentication is required. If your service uses authentication, refer to the specific documentation.
Request Format

The service expects a POST request with the following JSON data structure in the request body:

**JSON**
```
{
  {
  "deliveryRequests": [
    {
      "restaurant": {
        "id": (restaurant ID),
        "x": (restaurant X-coordinate),
        "y": (restaurant Y-coordinate)
      },
      "preparationTime": (preparation time in minutes),
      "customer": {
        "id": (customer ID),
        "x": (customer X-coordinate),
        "y": (customer Y-coordinate)
      }
    },
    {
      "restaurant": {
        "id": (restaurant ID),
        "x": (restaurant X-coordinate),
        "y": (restaurant Y-coordinate)
      },
      "preparationTime": (preparation time in minutes),
      "customer": {
        "id": (customer ID),
        "x": (customer X-coordinate),
        "y": (customer Y-coordinate)
      }
    }
  ],
  "deliveryExecutive": {
    "id": (delivery executive ID),
    "x": (delivery executive X-coordinate),
    "y": (delivery executive Y-coordinate),
    "speed": (delivery executive speed in km/hr)
  }
}
```

## Explanation of Fields

The request body expects a JSON object with the following structure:

**deliveryRequests (array):** An array containing information about two delivery orders.

* Each object within the array represents a single delivery order and has the following properties:

    * **restaurant (object):** An object containing details about the restaurant associated with the order.
        * `id (number)`: The unique identifier for the restaurant.
        * `x (number)`: The X-coordinate of the restaurant's location.
        * `y (number)`: The Y-coordinate of the restaurant's location.
    * **preparationTime (number):** The estimated time (in minutes) it takes for the restaurant to prepare the order.
    * **customer (object):** An object containing details about the customer receiving the order.
        * `id (number)`: The unique identifier for the customer.
        * `x (number)`: The X-coordinate of the customer's location.
        * `y (number)`: The Y-coordinate of the customer's location.

**deliveryExecutive (object):** An object containing information about the delivery executive who will fulfill the orders.

* `id (number)`: The unique identifier for the delivery executive.
* `x (number)`: The X-coordinate of the delivery executive's current location.
* `y (number)`: The Y-coordinate of the delivery executive's current location.
* `speed (number)`: The average speed (in kilometers per hour) of the delivery executive.


## Example cURL Request

The following cURL command demonstrates how to send a request to the delivery optimization service with sample data:

**Bash**
```
curl --location 'http://localhost:8080/deliver' \
--header 'Content-Type: application/json' \
--data '{
    "deliveryRequests": [
        {
            "restaurant": {
                "id": 1,
                "x": 3,
                "y": 0
            },
            "preparationTime": 1,
            "customer": {
                "id": 1,
                "x": 6,
                "y": 0
            }
        },
        {
            "restaurant": {
                "id": 2,
                "x": 2,
                "y": 0
            },
            "preparationTime": 20,
            "customer": {
                "id": 2,
                "x": 4,
                "y": 0
            }
        }
    ],
    "deliveryExecutive": {
        "id": 1,
        "x": 0,
        "y": 0,
        "speed": 20
    }
}'
```

## Response Format

The service responds with a JSON object containing the following information:

**JSON**
```
{
"path": "(optimized delivery path in sequence of restaurant and customer IDs)",
"totalTime": (total time required to complete both deliveries in minutes)
}
```

## Example Response

**JSON**
```
{
"path": "R1-C1-R2-C2",
"totalTime": 25
}
```