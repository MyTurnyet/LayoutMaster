package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.data.CarTypeDto;
import dev.paigewatson.layoutmaster.models.data.NullCarTypeDto;
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
    public AARDesignation displayName()
    {
        return AARDesignation.NULL;
    }

    @Override
    public CarType saveToDatabase(CarTypeDAL carTypeDAL)
    {
        return null;
    }

    @Override
    public CarTypeDto getDto()
    {
        return new NullCarTypeDto();
    }

    @Override
    public String toString()
    {
        return "NullCarType{" +
                "id=''" +
                ", aarDesignation=''" +
                ", carriedGoodsList=''" +
                "}";
    }

}
