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
        return getLoadedCarType(aarDesignation, uuid, Collections.emptyList());
    }

    public static AARType getLoadedCarType(AARDesignation aarDesignation, UUID uuid, List<GoodsType> carriedGoodsList)
    {
        return new AARType(uuid, aarDesignation, carriedGoodsList);
    }

    public static AARType boxcarType()
    {
        return boxcarType(UUID.randomUUID());
    }

    public static AARType boxcarType(UUID uuid)
    {
        return getLoadedCarType(XM, uuid, Arrays.asList(Ingredients, Logs, Parts));
    }

    public static AARType gondolaType()
    {
        return gondolaType(UUID.randomUUID());

    }

    public static FreightCar gondola(String roadName, int roadNumber)
    {
        return new FreightCar(UUID.randomUUID(), roadName, roadNumber, gondolaType());
    }

    public static FreightCar boxcar()
    {
        return boxcar(UUID.randomUUID(), "PNWR", 1234);
    }

    public static AARType gondolaType(UUID uuid)
    {
        return getLoadedCarType(GS, uuid, Arrays.asList(ScrapMetal, MetalScraps, Logs, Aggregates));

    }

    public static AARType flatcarType()
    {
        return getLoadedCarType(FC, UUID.randomUUID(), Arrays.asList(Logs, Lumber, Parts));

    }

    public static FreightCar flatcar(String roadName, int roadNumber)
    {
        return new FreightCar(UUID.randomUUID(), roadName, roadNumber, flatcarType());
    }

    public static FreightCar boxcar(UUID uuid, String roadName, int roadNumber)
    {
        return new FreightCar(uuid, roadName, roadNumber, boxcarType());
    }

}
