package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.RollingStockDAL;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoFreightCarService implements FreightCarService
{
    private final RollingStockDAL rollingStockDAL;


    public MongoFreightCarService(@Autowired RollingStockDAL rollingStockDAL)
    {
        this.rollingStockDAL = rollingStockDAL;
    }

    @Override
    public List<RollingStock> allFreightCars()
    {
        return rollingStockDAL.getAllRollingStock();
    }

    @Override
    public List<RollingStock> allFreightCarsByAARType(AARDesignation aarDesignation)
    {
        return rollingStockDAL.getAllOfAARDesignation(aarDesignation);
    }

    @Override
    public void delete(RollingStock rollingStockToDelete)
    {
        rollingStockDAL.delete(rollingStockToDelete);
    }

    @Override
    public List<RollingStock> allFreightCarsByRoadName(String roadName)
    {
        return rollingStockDAL.getAllOfRoadName(roadName);
    }

    @Override
    public RollingStock saveFreightCarToDatabase(RollingStock freightCarToSave)
    {
        return rollingStockDAL.insertRollingStock(freightCarToSave);
    }
}
