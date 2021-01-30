package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.data.CarTypeRepository;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public List<CarType> getAllCarTypes()
    {
        return carTypeRepository.findAll();
    }

    public void addNewCarType(AARDesignation aarDesignation, ArrayList<GoodsType> carriedGoodsList)
    {
        final CarType carType = new CarType(aarDesignation, carriedGoodsList);
        carTypeRepository.save(carType);
    }
}
