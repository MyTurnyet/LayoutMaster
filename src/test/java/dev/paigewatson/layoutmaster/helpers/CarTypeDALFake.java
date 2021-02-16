package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType;

import java.util.Collections;
import java.util.List;

public class CarTypeDALFake implements CarTypeDAL
{
    private CarType carTypeSavedEntity;
    private CarType returnedEntity = new NullCarType();
    private List<CarType> returnedCarTypes = Collections.emptyList();
    private List<AARDesignation> aarDesignationList;

    @Override
    public List<CarType> findAllByCarTypesThatCanCarry(GoodsType expectedGoods)
    {
        return returnedCarTypes;
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

    @Override
    public List<CarType> getAllCarTypes()
    {
        return returnedCarTypes;
    }

    @Override
    public List<AARDesignation> getAllAARDesignations()
    {
        return aarDesignationList;
    }

    public CarType savedEntity()
    {
        return carTypeSavedEntity;
    }

    public void setCurrentSavedEntity(CarType currentSavedEntity)
    {

        this.carTypeSavedEntity = currentSavedEntity;
    }

    public void setEntityToReturn(CarType returnedEntity)
    {
        this.returnedEntity = returnedEntity;
    }

    public void setReturnedEntityList(List<CarType> returnedCarTypes)
    {
        this.returnedCarTypes = returnedCarTypes;
    }

    public void setReturnAARDesignationsList(List<AARDesignation> aarDesignationList)
    {
        this.aarDesignationList = aarDesignationList;
    }
}
