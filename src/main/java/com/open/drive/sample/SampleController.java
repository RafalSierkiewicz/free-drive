package com.open.drive.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping
    public String hello(){
        return "Hello world";
    }
}
