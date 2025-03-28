package com.api.authentification.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/any-endpoint")
    public String hello() {
        return "Hello from test!";
    }
}
