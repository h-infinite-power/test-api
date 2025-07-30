package com.example.testapi.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {
    
    @GetMapping("/")
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}
