package com.hutech.organizations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class OrganizationController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Organizations Service!";
    }
}
