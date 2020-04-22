package com.satyy.resourceserver.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceServerController {

    @GetMapping("/health")
    public String health() {
        return "Resource Server is Up!";
    }

    /**
     * Protected resource, token authentication is required to access this endpoint.
     * Based on the configuration in Resource Server Configuration.
     */
    @GetMapping("/greeting/{user-name}")
    public String greeting(@PathVariable("user-name") String userName, Principal principal) {
        System.out.println(principal.getName());
        return "Hello ".concat(userName);
    }
}
