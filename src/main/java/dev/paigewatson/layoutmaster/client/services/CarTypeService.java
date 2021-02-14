package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;

import java.util.List;

public interface CarTypeService
{
    List<AARDesignation> allAARDesignations();

    CarType saveCarTypeToDatabase(CarType carTypeToSave);

    List<CarType> allCarTypes();

    CarType carTypeForAAR(AARDesignation expectedAARDesignation);

    List<CarType> carTypesThatCarryGoodsType(GoodsType expectedGoodsType);
}
