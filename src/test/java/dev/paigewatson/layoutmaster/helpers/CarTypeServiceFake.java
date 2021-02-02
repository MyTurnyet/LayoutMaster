package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarTypeServiceFake implements CarTypeService
{
    private ArrayList<CarType> returnedCarTypes;

    @Override
    public List<AARDesignation> allAARDesignations()
    {
        return Arrays.asList(AARDesignation.class.getEnumConstants());
    }

    @Override
    public List<CarType> allCarTypes()
    {
        return returnedCarTypes;
    }

    @Override
    public void saveCarTypeToDatabase(CarType carTypeToSave)
    {

    }

    public void setReturnedCarTypes(ArrayList<CarType> returnedCarTypes)
    {

        this.returnedCarTypes = returnedCarTypes;
    }
}
