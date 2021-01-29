package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class CarType
{
    private final CarTypeDesignation carTypeDesignation;
    private final ArrayList<GoodsType> carriedGoodsList;
    @Id
    private String id;

    public CarType(CarTypeDesignation carTypeDesignation, ArrayList<GoodsType> carriedGoodsList)
    {
        this.carTypeDesignation = carTypeDesignation;
        this.carriedGoodsList = carriedGoodsList;
    }

    public boolean isOfType(CarTypeDesignation expectedTypeDesignation)
    {
        return expectedTypeDesignation == carTypeDesignation;
    }

    public boolean canCarry(GoodsType expectedGoodsType)
    {
        return carriedGoodsList.contains(expectedGoodsType);
    }

    @Override
    public String toString()
    {
        return "CarType{" +
                "id='" + id + '\'' +
                ", carTypeDesignation=" + carTypeDesignation +
                ", carriedGoodsList=" + carriedGoodsList +
                '}';
    }

    public String displayName()
    {
        return carTypeDesignation.name();
    }
}
