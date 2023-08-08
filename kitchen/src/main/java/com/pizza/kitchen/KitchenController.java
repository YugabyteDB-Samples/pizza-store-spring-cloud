package com.pizza.kitchen;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KitchenController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/order")
    public ResponseEntity<Order> addNewOrder(
            @RequestParam("id") int id,
            @RequestParam("location") String location) {

        Timestamp orderTime = Timestamp.from(Instant.now());

        jdbcTemplate.update("INSERT INTO pizza_order values(?, 'Ordered', ?, ?)",
                (prepStmt) -> {
                    prepStmt.setObject(1, id);
                    prepStmt.setString(2, location);
                    prepStmt.setTimestamp(3, Timestamp.from(Instant.now()));
                });

        return ResponseEntity.ok(new Order(id, "Ordered", location, orderTime));
    }

    @PutMapping("/order")
    public ResponseEntity<Object> updateOrder(
            @RequestParam("id") int id,
            @RequestParam("status") String status,
            @RequestParam(name = "location", required = false) String location) {

        int updatedRows = 0;

        if (Objects.nonNull(location))
            updatedRows = jdbcTemplate.update(
                    "UPDATE pizza_order SET status = ? WHERE id = ? and location = ?::store_location",
                    (prepStmt) -> {
                        prepStmt.setString(1, status);
                        prepStmt.setObject(2, id);
                        prepStmt.setObject(3, location);
                    });
        else
            updatedRows = jdbcTemplate.update("UPDATE pizza_order SET status = ? WHERE id = ?",
                    (prepStmt) -> {
                        prepStmt.setString(1, status);
                        prepStmt.setObject(2, id);
                    });

        return updatedRows == 0
                ? new ResponseEntity<>("Order is not found", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(new OrderStatus(id, status));
    }

    @GetMapping("/order")
    public ResponseEntity<Object> getOrder(
            @RequestParam("id") int id,
            @RequestParam(name = "location", required = false) String location) {

        List<Order> orders;

        if (Objects.nonNull(location))
            orders = jdbcTemplate.query(
                    "SELECT * FROM pizza_order WHERE id = ? and location = ?::store_location",
                    (prepStmt) -> {
                        prepStmt.setInt(1, id);
                        prepStmt.setString(2, location);
                    },
                    new OrderRowMapper());
        else
            orders = jdbcTemplate.query(
                    "SELECT * FROM pizza_order WHERE id = ?",
                    (prepStmt) -> {
                        prepStmt.setInt(1, id);
                    },
                    new OrderRowMapper());

        return orders.size() == 0
                ? new ResponseEntity<>("Order is not found", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(orders.get(0));
    }

    @DeleteMapping("/orders")
    public ResponseEntity<Object> deleteOrders() {
        jdbcTemplate.execute("TRUNCATE pizza_order");

        return ResponseEntity.ok("Orders have been deleted");
    }
}
