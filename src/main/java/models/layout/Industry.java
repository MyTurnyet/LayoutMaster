package models.layout;

import models.goods.GoodsType;
import models.goods.ProducedGood;
import models.goods.RequiredGood;
import models.rollingstock.FreightCar;

import java.util.ArrayList;

/**
 * Industry
 * Should know it's name and take in goods,
 * can produce goods
 * knows what rolling stock are at it's location and can load/unload them
 * knows how many cars it can hold
 */
public class Industry
{

    private final String industryName;
    private final ArrayList<RequiredGood> acceptedGoodsList;
    private final ArrayList<ProducedGood> returnedGoodsList;
    private final ArrayList<FreightCar> carsAtIndustry;
    int maxCar = 1;

    public Industry(String industryName, ArrayList<RequiredGood> acceptedGoodsList, ArrayList<ProducedGood> returnedGoodsList)
    {
        carsAtIndustry = new ArrayList<>(maxCar);
        this.industryName = industryName;
        this.acceptedGoodsList = acceptedGoodsList;
        this.returnedGoodsList = returnedGoodsList;
    }

    public String name()
    {
        return industryName;
    }

    public boolean needs(GoodsType goodsType)
    {
        return acceptedGoodsList.stream().anyMatch(requiredGood -> requiredGood.needs(goodsType));
    }

    public boolean produces(GoodsType goodsType)
    {
        return returnedGoodsList.stream().anyMatch(producedGood -> producedGood.isOfType(goodsType));
    }

    public int emptyLocations()
    {
        return maxCar - carsAtIndustry.size();
    }

    public void setOutCar(FreightCar freightCar)
    {
        carsAtIndustry.add(freightCar);
    }
}
