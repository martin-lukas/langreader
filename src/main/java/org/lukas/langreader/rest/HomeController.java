package org.lukas.langreader.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public String sendHello() {
        return "Server is UP";
    }
}
