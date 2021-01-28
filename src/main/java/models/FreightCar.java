package models;

import java.util.ArrayList;

public class FreightCar
{
    private final String roadName;
    private final CarType carType;
    private final ArrayList<GoodsType> goodsThatCanBeCarried;
    private GoodsType currentlyCarriedGoods = GoodsType.EMPTY;

    public FreightCar(String roadName, CarType carType, ArrayList<GoodsType> goodsThatCanBeCarried)
    {
        this.roadName = roadName;
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

    public void load(GoodsType goodsToLoad) throws Exception
    {
        if (!goodsThatCanBeCarried.contains(goodsToLoad))
            throw new Exception("This car cannot carry " + goodsToLoad.name());
        this.currentlyCarriedGoods = goodsToLoad;
    }

    public String displayName()
    {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(carType.name());
        stringBuilder.append(" - ");
        stringBuilder.append(roadName);
        return stringBuilder.toString();
    }
}
