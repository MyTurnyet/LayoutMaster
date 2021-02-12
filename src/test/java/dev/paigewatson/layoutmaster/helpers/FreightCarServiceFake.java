package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;

import java.util.List;

public class FreightCarServiceFake implements FreightCarService
{
    public RollingStock savedFreightCar;
    private List<RollingStock> returnedFreightCarsList;

    @Override
    public List<RollingStock> allFreightCars()
    {
        return returnedFreightCarsList;
    }

    @Override
    public List<RollingStock> allFreightCarsByAARType(AARDesignation aarDesignation)
    {
        return returnedFreightCarsList;
    }

    @Override
    public void delete(RollingStock rollingStockToDelete)
    {

    }

    @Override
    public List<RollingStock> allFreightCarsByRoadName(String roadName)
    {
        return returnedFreightCarsList;
    }

    @Override
    public RollingStock saveFreightCarToDatabase(RollingStock freightCarToSave)
    {
        savedFreightCar = freightCarToSave;
        return savedFreightCar;
    }

    public void setReturnedFreightCars(List<RollingStock> returnedFreightCarsList)
    {
        this.returnedFreightCarsList = returnedFreightCarsList;
    }
}
