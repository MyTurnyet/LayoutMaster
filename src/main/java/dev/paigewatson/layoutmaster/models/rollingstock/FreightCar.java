package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;

import java.util.ArrayList;

/**
 * FreightCar
 * Should know it's state
 * road name, road number, car type, what goods it can carry
 * if loaded or not
 */

public class FreightCar
{
    private final String roadName;
    private final int roadNumber;
    private final CarType carType;
    private final ArrayList<GoodsType> goodsThatCanBeCarried;
    private GoodsType currentlyCarriedGoods = GoodsType.EMPTY;

    public FreightCar(String roadName, int roadNumber, CarType carType, ArrayList<GoodsType> goodsThatCanBeCarried)
    {
        this.roadName = roadName;
        this.roadNumber = roadNumber;
        this.carType = carType;
        this.goodsThatCanBeCarried = goodsThatCanBeCarried;
    }

    public boolean canCarry(GoodsType expectedGood)
    {
        return goodsThatCanBeCarried.contains(expectedGood);
    }

    public boolean isLoaded()
    {
        return currentlyCarriedGoods != GoodsType.EMPTY;
    }

    public void load(GoodsType goodsToLoad)
    {
        if (!goodsThatCanBeCarried.contains(goodsToLoad)) return;
        this.currentlyCarriedGoods = goodsToLoad;
    }

    public String displayName()
    {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(carType.name());
        stringBuilder.append(" - ");
        stringBuilder.append(roadName);
        stringBuilder.append(" ");
        stringBuilder.append(roadNumber);
        return stringBuilder.toString();
    }

    @Override
    public String toString()
    {
        return "FreightCar{" +
                "roadName='" + roadName + '\'' +
                ", roadNumber=" + roadNumber +
                ", carType=" + carType +
                ", goodsThatCanBeCarried=" + goodsThatCanBeCarried +
                ", currentlyCarriedGoods=" + currentlyCarriedGoods +
                '}';
    }
}
