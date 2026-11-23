package com.typeb.webapp.controller;

import com.typeb.webapp.dto.ErrorResponse;
import com.typeb.webapp.service.HelloWorldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for GET /hello-world.
 *
 * <p>Query parameter: {@code name} (required, non-empty).
 *
 * <ul>
 *   <li>Name starts with A–M → 200 OK  {@code { "message": "Hello <Name>" }}</li>
 *   <li>Name starts with N–Z → 400 Bad Request {@code { "error": "Invalid Input" }}</li>
 *   <li>Name missing or empty  → 400 Bad Request {@code { "error": "Invalid Input" }}</li>
 * </ul>
 */
@RestController
public class HelloWorldController {

    private final HelloWorldService helloWorldService;

    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<?> helloWorld(
            @RequestParam(required = false) String name) {

        if (!helloWorldService.isValidName(name)) {
            return ResponseEntity.badRequest().body(ErrorResponse.INVALID_INPUT);
        }

        String message = helloWorldService.buildGreeting(name);
        return ResponseEntity.ok(Map.of("message", message));
    }
}
