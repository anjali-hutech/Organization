package com.hutech.drivers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @GetMapping("/hello")
    public String helloDriver() {
        return "Hello from Drivers Service!";
    }
}