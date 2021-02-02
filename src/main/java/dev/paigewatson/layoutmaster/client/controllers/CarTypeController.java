package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.data.CarTypeRepository;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/models")
public class CarTypeController
{
    private final CarTypeRepository carTypeRepository;

    public CarTypeController(@Autowired CarTypeRepository carTypeRepository)
    {

        this.carTypeRepository = carTypeRepository;
    }

    @GetMapping(value = "/aar")
    public List<AARDesignation> getAARDesignations()
    {
        final AARDesignation[] AARDesignations = AARDesignation.class.getEnumConstants();
        return Arrays.asList(AARDesignations.clone());
    }

    @GetMapping("/cartypes")
    public Object getAllCarTypes()
    {
        return carTypeRepository.findAll();
    }

    @PostMapping("/cartypes")
    public void addNewCarType(@RequestBody CarType carType)
    {
        carTypeRepository.save(carType);
    }
}
