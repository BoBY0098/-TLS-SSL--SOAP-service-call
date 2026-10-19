package com.example.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SoapController {

    private final SoapClient soapClient;

    @GetMapping("/sayHello")
    public String sayHello() {
        return soapClient.sayHello();
    }
}
