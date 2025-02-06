package com.hutech.vehicles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @GetMapping("/hello")
    public String helloVehicle() {
    	System.out.println("In vehicle service");
        return "Hello from Vehicles Service!";
    }
}
