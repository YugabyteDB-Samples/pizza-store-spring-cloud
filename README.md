# pizza-store-spring-cloud
Building a global multi-region application with Spring Cloud and YugabyteDB

# Local Deployment

Commands:
```shell
# Add new order
http POST localhost:8080/order id==1 location==Sydney

# Update order status
http PUT localhost:8080/order id==1 status==Baking

# Get all orders
http GET localhost:8080/orders
http GET localhost:8080/orders location==Sydney

# Get a specific order
http GET localhost:8080/order id==1
http GET localhost:8080/order id==1 location==Sydney

```