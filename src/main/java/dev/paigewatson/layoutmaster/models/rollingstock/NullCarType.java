package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;

public class NullCarType implements CarType
{
    @Override
    public boolean isNull()
    {
        return true;
    }

    @Override
    public boolean canCarry(GoodsType goodsType)
    {
        return false;
    }

    @Override
    public boolean isOfType(AARDesignation designation)
    {
        return false;
    }

    @Override
    public String displayName()
    {
        return "";
    }

    @Override
    public CarType saveToDatabase(CarTypeDAL carTypeDAL)
    {
        return this;
    }

}
