package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;

import java.util.List;

public interface RollingStockDAL
{
    List<RollingStock> getAllRollingStock();

    void deleteAll();

    void delete(RollingStock rollingStockToDelete);

    List<RollingStock> getAllOfAARDesignation(AARDesignation aarDesignation);

    List<RollingStock> getAllOfRoadName(String roadName);

    RollingStock insertRollingStock(RollingStock rollingStockToSave);
}
