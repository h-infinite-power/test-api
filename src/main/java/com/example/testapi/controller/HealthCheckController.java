package com.example.testapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-api")
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Service is up and running");
    }

    @GetMapping("/echo")
    public ResponseEntity<String> echoParam(@RequestParam(required = false) String message) {
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.ok("파라미터가 없습니다");
        }
        return ResponseEntity.ok(message);
    }

    @PostMapping("/echo")
    public ResponseEntity<String> echoBody(@RequestBody(required = false) String message) {
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.ok("파라미터가 없습니다");
        }
        return ResponseEntity.ok(message);
    }
}
