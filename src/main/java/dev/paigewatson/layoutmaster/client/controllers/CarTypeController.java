package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/models")
public class CarTypeController
{

    private final CarTypeService carTypeService;

    public CarTypeController(@Autowired CarTypeService carTypeService)
    {
        this.carTypeService = carTypeService;
    }

    @GetMapping(value = "/aar")
    public List<AARDesignation> getAARDesignations()
    {
        return carTypeService.allAARDesignations();
    }

    @GetMapping("/cartypes")
    public List<CarTypeDto> getAllCarTypes()
    {
        return carTypeService.allCarTypes();
    }

    @PostMapping("/cartypes")
    public void addNewCarType(@RequestBody CarTypeDto carType)
    {
        carTypeService.saveCarTypeToDatabase(carType);
    }

    @GetMapping("/cartypes/aar/{aarType}")
    public CarTypeDto getCarTypeByAAR(@PathVariable(value = "aarType") String expectedType)
    {
        return carTypeService.carTypeForAAR(expectedType);
    }
}
