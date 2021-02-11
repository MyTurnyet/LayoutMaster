package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.models.data.CarTypeDto;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import dev.paigewatson.layoutmaster.models.rollingstock.ConvertsToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/models")
public class CarTypeController
{

    private final CarTypeService carTypeService;

    public CarTypeController(@Autowired CarTypeService carTypeService)
    {
        this.carTypeService = carTypeService;
    }

    @GetMapping(path = "/aar")
    public List<AARDesignation> getAARDesignations()
    {
        return carTypeService.allAARDesignations();
    }

    @GetMapping(path = "/types")
    public List<CarTypeDto<AARType>> getAllCarTypes()
    {
        final List<CarType> carTypeList = carTypeService.allCarTypes();
        final List<CarTypeDto<AARType>> carTypeDtoList = carTypeList.stream().map(carType ->
        {
            return carType.getDto();
        }).collect(Collectors.toList());
        return carTypeDtoList;
    }

    @PostMapping(path = "/types/add")
    public void addNewCarType(@RequestBody CarTypeDto<? extends CarType> carTypeDto)
    {
        final CarType carType = carTypeDto.getEntity();
        carTypeService.saveCarTypeToDatabase(carType);
    }

    @GetMapping(path = "/types/aar/{aarType}")
    public CarTypeDto<? extends CarType> getCarTypeByAAR(@PathVariable(value = "aarType") String expectedType)
    {
        final AARDesignation aarDesignation = AARDesignation.valueOf(expectedType);
        final CarType carTypeForAAR = carTypeService.carTypeForAAR(aarDesignation);
        return carTypeForAAR.getDto();
    }

    @GetMapping(path = "/types/goods/{goodsType}")
    public List<CarTypeDto<AARType>> getCarTypesThatCarry(@PathVariable(value = "goodsType") String expectedGoods)
    {
        final List<CarType> carTypeList = carTypeService.carTypesThatCarryGoodsType(GoodsType.valueOf(expectedGoods));
        final List<CarTypeDto<AARType>> carTypeDtoList = carTypeList.stream().map(ConvertsToDto::getDto).collect(Collectors.toList());
        return carTypeDtoList;
    }
}
