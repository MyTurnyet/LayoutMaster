package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    @GetMapping("/types")
    public List<CarType> getAllCarTypes()
    {
        return carTypeService.allCarTypes();
    }

    @PostMapping("/types")
    public void addNewCarType(@RequestBody CarTypeDto carType)
    {
//        carTypeService.saveCarTypeToDatabase(carType);
    }

    @GetMapping("/types/aar/{aarType}")
    public CarType getCarTypeByAAR(@PathVariable(value = "aarType") AARDesignation expectedType)
    {
        return carTypeService.carTypeForAAR(expectedType);
    }

    @GetMapping("/types/goods/{goodsType}")
    public List<CarTypeDto> getCarTypesThatCarry(@PathVariable(value = "goodsType") String expectedGoods)
    {
        return Collections.emptyList();
//        return carTypeService.carTypesThatCarryGoodsType(expectedGoods);
    }
}
