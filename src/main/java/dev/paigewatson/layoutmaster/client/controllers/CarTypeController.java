package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.models.rollingstock.CarTypeDesignation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/models")
public class CarTypeController
{
    @GetMapping(value = "/cartypes")
    public List<CarTypeDesignation> getAllCarTypes()
    {
        final CarTypeDesignation[] carTypeDesignations = CarTypeDesignation.class.getEnumConstants();
        return Arrays.asList(carTypeDesignations.clone());
    }
}
