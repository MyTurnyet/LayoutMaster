package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;

import java.util.Collections;

public class NullCarType extends BaseCarType
{
    public NullCarType()
    {
        this.aarDesignation = AARDesignation.NULL;
        this.carriedGoodsList = Collections.emptyList();
        this.id = "";
    }

    public boolean isNull()
    {
        return true;
    }


    public boolean canCarry(GoodsType goodsType)
    {
        return false;
    }

    public boolean isOfType(AARDesignation designation)
    {
        return false;
    }

    public AARDesignation displayName()
    {
        return AARDesignation.NULL;
    }

    public CarType saveToDatabase(CarTypeDAL carTypeDAL)
    {
        return null;
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
