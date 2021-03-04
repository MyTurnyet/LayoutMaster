package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;

import java.util.List;

public interface FreightCarService
{
    List<RollingStock> allFreightCars();

    List<RollingStock> allFreightCarsByAARType(AARDesignation aarDesignation);

    void delete(RollingStock rollingStockToDelete);

    List<RollingStock> allFreightCarsByRoadName(String roadName);

    RollingStock saveFreightCarToDatabase(RollingStock freightCarToSave);
}
