package com.pizza.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TrackerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/status")
    public ResponseEntity<String> getOrderStatus(@RequestParam("id") int orderId) {
        try {
            String status = jdbcTemplate.queryForObject(
                    "SELECT status FROM pizza_order WHERE id = ?",
                    String.class, orderId);

            return ResponseEntity.ok(status);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>("Order is not found", HttpStatus.NOT_FOUND);
        }
    }
}
