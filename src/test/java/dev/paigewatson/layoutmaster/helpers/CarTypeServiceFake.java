package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;

import java.util.Collections;
import java.util.List;

public class CarTypeServiceFake implements CarTypeService
{
    private List<CarType> returnedCarTypeList;
    private CarType savedEntity;
    private CarType returnedCarTypeWithAAR;
    private List<AARDesignation> returnedAARTypes = Collections.emptyList();

    @Override
    public List<AARDesignation> allAARDesignations()
    {
        return returnedAARTypes;
    }


    @Override
    public CarType saveCarTypeToDatabase(CarType carTypeToSave)
    {
        this.savedEntity = carTypeToSave;
        return carTypeToSave;
    }

    @Override
    public List<CarType> allCarTypes()
    {
        return this.returnedCarTypeList;
    }

    @Override
    public CarType carTypeForAAR(AARDesignation expectedAARDesignation)
    {
        return this.returnedCarTypeWithAAR;
    }

    @Override
    public List<CarType> carTypesThatCarryGoodsType(GoodsType expectedGoodsType)
    {
        return returnedCarTypeList;
    }

    public void setReturnedCarTypeList(List<CarType> returnedCarTypeList)
    {
        this.returnedCarTypeList = returnedCarTypeList;
    }

    public CarType savedDtoEntity()
    {
        return this.savedEntity;
    }

    public void setReturnedCarTypeWithAAR(CarType carType)
    {
        returnedCarTypeWithAAR = carType;
    }

    public void setReturnAARTypes(List<AARDesignation> returnedAARTypes)
    {
        this.returnedAARTypes = returnedAARTypes;
    }
}
