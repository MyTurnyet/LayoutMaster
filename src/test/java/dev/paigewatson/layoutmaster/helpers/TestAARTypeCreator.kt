package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;

import java.util.Arrays;
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

public class TestAARTypeCreator
{
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

    public static AARType gondolaType(UUID uuid)
    {
        return getLoadedCarType(uuid, GS, Arrays.asList(ScrapMetal, MetalScraps, Logs, Aggregates));
    }

    public static AARType flatcarType()
    {
        return flatcarType(UUID.randomUUID());
    }

    static AARType flatcarType(UUID uuid)
    {
        return getLoadedCarType(uuid, FC, Arrays.asList(Logs, Lumber, Parts));
    }
}
