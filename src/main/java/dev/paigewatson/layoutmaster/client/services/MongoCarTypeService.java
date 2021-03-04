package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return carTypeDAL.getAllAARDesignations();
    }

    @Override
    public CarType saveCarTypeToDatabase(CarType carTypeToSave)
    {
        carTypeDAL.insertCarType(carTypeToSave);
        return carTypeToSave;
    }

    @Override
    public List<CarType> allCarTypes()
    {
        return carTypeDAL.getAllCarTypes();
    }

    @Override
    public CarType carTypeForAAR(AARDesignation expectedAARDesignation)
    {
        return carTypeDAL.findByAarType(expectedAARDesignation);
    }

    @Override
    public List<CarType> carTypesThatCarryGoodsType(GoodsType expectedGoodsType)
    {
        return carTypeDAL.findAllByCarTypesThatCanCarry(expectedGoodsType);
    }
}
