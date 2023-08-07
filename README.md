# pizza-store-spring-cloud
Building a global multi-region application with Spring Cloud and YugabyteDB

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