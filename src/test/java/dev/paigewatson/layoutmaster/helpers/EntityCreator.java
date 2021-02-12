package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Aggregates;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Logs;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Lumber;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.MetalScraps;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.ScrapMetal;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.FC;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.GS;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;

public class EntityCreator
{
    public static AARType getCarType(AARDesignation aarDesignation)
    {
        UUID boxcarUUID = UUID.randomUUID();
        return getCarType(aarDesignation, boxcarUUID);
    }

    public static AARType getCarType(AARDesignation aarDesignation, UUID uuid)
    {
        return getLoadedCarType(uuid, aarDesignation, Collections.emptyList());
    }

    public static AARType getLoadedCarType(UUID uuid, AARDesignation aarDesignation, List<GoodsType> carriedGoodsList)
    {
        return new AARType(uuid, aarDesignation, carriedGoodsList);
    }

    public static AARType boxcarType()
    {
        return boxcarType(UUID.randomUUID());
    }

    public static AARType boxcarType(UUID uuid)
    {
        return getLoadedCarType(uuid, XM, Arrays.asList(Ingredients, Logs, Parts));
    }

    public static AARType gondolaType()
    {
        return gondolaType(UUID.randomUUID());

    }

    public static FreightCar gondola(String roadName, int roadNumber)
    {
        return gondola(UUID.randomUUID(), roadName, roadNumber);
    }

    public static FreightCar gondola(UUID uuid, String roadName, int roadNumber)
    {
        return gondola(uuid, roadName, roadNumber, UUID.randomUUID());
    }

    public static FreightCar gondola(UUID uuid, String roadName, int roadNumber, UUID carTypeUUID)
    {
        return new FreightCar(uuid, roadName, roadNumber, gondolaType(carTypeUUID));
    }

    public static FreightCar boxcar()
    {
        return boxcar(UUID.randomUUID(), "PNWR", 1234);
    }

    public static AARType gondolaType(UUID uuid)
    {
        return getLoadedCarType(uuid, GS, Arrays.asList(ScrapMetal, MetalScraps, Logs, Aggregates));

    }

    public static AARType flatcarType()
    {
        return flatcarType(UUID.randomUUID());

    }

    public static AARType flatcarType(UUID uuid)
    {
        return getLoadedCarType(uuid, FC, Arrays.asList(Logs, Lumber, Parts));

    }

    public static FreightCar flatcar(String roadName, int roadNumber)
    {
        return flatcar(UUID.randomUUID(), roadName, roadNumber);
    }

    public static FreightCar flatcar(UUID flatcarUUID, String roadName, int roadNumber)
    {
        return new FreightCar(flatcarUUID, roadName, roadNumber, flatcarType());
    }

    public static FreightCar flatcar(UUID flatcarUUID, String roadName, int roadNumber, UUID carTypeUUID)
    {
        return new FreightCar(flatcarUUID, roadName, roadNumber, flatcarType(carTypeUUID));
    }

    public static FreightCar boxcar(UUID uuid, String roadName, int roadNumber)
    {
        return boxcar(uuid, roadName, roadNumber, UUID.randomUUID());
    }

    public static FreightCar boxcar(UUID uuid, String roadName, int roadNumber, UUID carTypeUUID)
    {
        return new FreightCar(uuid, roadName, roadNumber, boxcarType(carTypeUUID));
    }

}
