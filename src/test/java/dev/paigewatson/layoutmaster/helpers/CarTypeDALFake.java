package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;

import java.util.List;

public class CarTypeDALFake implements CarTypeDAL
{
    private CarType carTypeSavedEntity;
    private CarType returnedEntity;

    @Override
    public List<AARType> findAllByCarTypesThatCanCarry(GoodsType expectedGoods)
    {
        return null;
    }

    @Override
    public CarType findByAarType(AARDesignation aarDesignation)
    {
        return returnedEntity;
    }

    @Override
    public void deleteAll()
    {

    }

    @Override
    public CarType insertCarType(CarType carTypeToSave)
    {
        carTypeSavedEntity = carTypeToSave;
        return carTypeToSave;
    }

    @Override
    public void delete(CarType carTypeToDelete)
    {

    }

    public CarType savedEntity()
    {
        return carTypeSavedEntity;
    }

    public void entityToReturnIs(CarType returnedEntity)
    {
        this.returnedEntity = returnedEntity;
    }
}
