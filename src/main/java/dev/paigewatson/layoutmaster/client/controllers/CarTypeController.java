package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.data.models.AARTypeDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "/aar", produces = "application/json")
    public ResponseEntity<List<AARDesignation>> getAARDesignations()
    {
        return new ResponseEntity<>(carTypeService.allAARDesignations(), HttpStatus.OK);
    }

    @GetMapping(path = "/types", produces = "application/json")
    public ResponseEntity<List<CarType>> getAllCarTypes()
    {
        return new ResponseEntity<>(carTypeService.allCarTypes(), HttpStatus.OK);
    }

    @PostMapping(path = "/types", consumes = "application/json")
    public void addNewCarType(@RequestBody AARType carType)
    {
        carTypeService.saveCarTypeToDatabase(carType);
    }

    @GetMapping(path = "/types/aar/{aarType}", produces = "application/json")
    public ResponseEntity<CarType> getCarTypeByAAR(@PathVariable(value = "aarType") String expectedType)
    {
        final AARDesignation aarDesignation = AARDesignation.valueOf(expectedType);
        final ResponseEntity<CarType> responseEntity = new ResponseEntity<CarType>(carTypeService.carTypeForAAR(aarDesignation), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(path = "/types/goods/{goodsType}", produces = "application/json")
    public List<AARTypeDto> getCarTypesThatCarry(@PathVariable(value = "goodsType") String expectedGoods)
    {
        return Collections.emptyList();
//        return carTypeService.carTypesThatCarryGoodsType(expectedGoods);
    }
}
