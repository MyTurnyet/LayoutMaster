package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
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

    @GetMapping(path = "/aar")
    public List<AARDesignation> getAARDesignations()
    {
        return carTypeService.allAARDesignations();
    }

    @GetMapping(path = "/types")
    public List<CarType> getAllCarTypes()
    {
        final List<CarType> carTypeList = carTypeService.allCarTypes();
        return carTypeList;
    }

    @PostMapping(path = "/types/add")
    public void addNewCarType(@RequestBody AARType carType)
    {
        carTypeService.saveCarTypeToDatabase(carType);
    }

    @GetMapping(path = "/types/aar/{aarType}")
    public CarType getCarTypeByAAR(@PathVariable(value = "aarType") String expectedType)
    {
        final AARDesignation aarDesignation = AARDesignation.valueOf(expectedType);
        final CarType carTypeForAAR = carTypeService.carTypeForAAR(aarDesignation);
        return carTypeForAAR;
    }

    @GetMapping(path = "/types/goods/{goodsType}")
    public List<CarType> getCarTypesThatCarry(@PathVariable(value = "goodsType") String expectedGoods)
    {
        final List<CarType> carTypeList = carTypeService.carTypesThatCarryGoodsType(GoodsType.valueOf(expectedGoods));
        return carTypeList;
    }
}
