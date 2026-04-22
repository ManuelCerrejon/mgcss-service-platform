package com.mgcss.serviceplatform.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String hello() {
        return "Hello";
}
}