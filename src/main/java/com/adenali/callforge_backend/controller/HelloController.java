package com.adenali.callforge_backend.controller;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from backend 🚀";
    }
}