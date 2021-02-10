package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;

import java.util.List;

public interface CarTypeService
{
    List<AARDesignation> allAARDesignations();

//    CarType saveCarTypeToDatabase(CarType carTypeToSave);
//
//    List<CarTypeDto> allCarTypes();
//
//    CarTypeDto carTypeForAAR(String expectedAARType);
//
//    List<CarTypeDto> carTypesThatCarryGoodsType(String expectedGoodsType);
}
