package com.example.ecommerce.api.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/currencies", produces = "application/json")
@CrossOrigin(origins = "*")
public class CurrencyController {


}
