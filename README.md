# Pizza Store With Spring Cloud and YugabyteDB

This project provides a functional skeleton for a pizza store implemented with the Spring Cloud framework and YugabyteDB.

The project comes with two microservices that support various REST API endpoints for client requests:
1. The Kitchen service (see the `kitchen` directory) - allows customers to place pizza orders.
2. The Tracker service (see the `tracker` directory) - lets customers check their order status.

![pizza_store_spring_cloud_v2](https://github.com/YugabyteDB-Samples/pizza-store-spring-cloud/assets/1537233/21d77111-41cf-4f11-9d2c-b1a3f32c4289)

Both microservices register with the [Spring Discovery Service](https://spring.io/projects/spring-cloud-netflix)(aka. Spring Cloud Netflix). This allows all the registered services to connect and communicate with each other directly using only their names.

The client interacts with the microservices via [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway). Following the provided routes configuration, the gateway resolves user requests and forwards them to respective services. The gateway also registers with the Discovery Service to benefit from the automatic discovery of the registered Kitchen and Tracker services.

YugabyteDB is a database that can scale horizontally, withstand various outages, and pin pizza orders to required locations. The application supports stretched and geo-partitioned YugabyteDB clusters.

# Local Deployment

Kitchen service commands:
```shell
# Add new order
http POST localhost:8081/order id==1 location==Sydney

# Update order status
http PUT localhost:8081/order id==1 status==Baking

# Get all orders
http GET localhost:8081/orders
http GET localhost:8081/orders location==Sydney

# Get a specific order
http GET localhost:8081/order id==1
http GET localhost:8081/order id==1 location==Sydney
```

Tracker service commands:
```shell
http GET localhost:8082/status id==1
```

Requests through the Spring Cloud Gateway:
```shell
http GET localhost:8080/tracker/status id==1
http GET localhost:8080/kitchen/order id==1
```

## Docker

Start in Docker and buld the image:
```shell
docker-compose up --build
```

or just start in Docker if you already have an image:
```shell
docker-compose up
```
