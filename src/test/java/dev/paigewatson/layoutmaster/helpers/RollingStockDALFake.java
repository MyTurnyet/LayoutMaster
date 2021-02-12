package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.data.RollingStockDAL;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;

import java.util.List;

public class RollingStockDALFake implements RollingStockDAL
{
    public RollingStock savedEntity;
    public RollingStock deletedEntity;
    private List<RollingStock> returnedFreightCarsList;
    private int deleteAllCalledCount;

    @Override
    public List<RollingStock> getAllRollingStock()
    {
        return returnedFreightCarsList;
    }

    @Override
    public void deleteAll()
    {
        deleteAllCalledCount++;
    }

    @Override
    public void delete(RollingStock rollingStockToDelete)
    {
        deletedEntity = rollingStockToDelete;
    }

    @Override
    public List<RollingStock> getAllOfAARDesignation(AARDesignation aarDesignation)
    {
        return returnedFreightCarsList;
    }

    @Override
    public List<RollingStock> getAllOfRoadName(String roadName)
    {
        return returnedFreightCarsList;
    }

    @Override
    public RollingStock insertRollingStock(RollingStock rollingStockToSave)
    {
        savedEntity = rollingStockToSave;
        return savedEntity;
    }

    public void setReturnedValuesList(List<RollingStock> returnedFreightCarsList)
    {

        this.returnedFreightCarsList = returnedFreightCarsList;
    }

    public int deleteAllCalledCount()
    {
        return deleteAllCalledCount;
    }
}
