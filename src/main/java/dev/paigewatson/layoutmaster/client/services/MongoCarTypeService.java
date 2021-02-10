package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MongoCarTypeService implements CarTypeService
{
    private final CarTypeDAL carTypeDAL;

    public MongoCarTypeService(@Autowired CarTypeDAL carTypeDAL)
    {
        this.carTypeDAL = carTypeDAL;
    }

    public List<AARDesignation> allAARDesignations()
    {
        return Arrays.asList(AARDesignation.class.getEnumConstants());
    }

//    @Override
//    public CarType saveCarTypeToDatabase(CarType carTypeToSave)
//    {
//        final CarType carTypeWithMatchingAARType = carTypeForAAR(carTypeToSave.aarType);
//        if (carTypeWithMatchingAARType.isNull())
//        {
//            return carTypeDAL.insertCarType(carTypeToSave);
//        }
//        carTypeDAL.insertCarType(carTypeToSave);
//        return carTypeToSave;
//    }

//    @Override
//    public List<CarTypeDto> allCarTypes()
//    {
//        return carTypeDAL.findAll();
//    }

//    @Override
//    public CarTypeDto carTypeForAAR(String expectedAARType)
//    {
//        final CarTypeDto carTypeByAAR = carTypeDAL.findByAarTypeEquals(expectedAARType);
//        return carTypeByAAR == null ? new NullCarTypeDto() : carTypeByAAR;
//    }
//
//    @Override
//    public List<CarTypeDto> carTypesThatCarryGoodsType(String expectedGoodsType)
//    {
//        return carTypeDAL.findAllByCarriedGoodsContains(expectedGoodsType);
//    }
}
