package models.layout;

import models.goods.GoodsType;

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
    private final ArrayList<GoodsType> acceptedGoodsList;
    private final ArrayList<GoodsType> returnedGoodsList;

    public Industry(String industryName, ArrayList<GoodsType> acceptedGoodsList, ArrayList<GoodsType> returnedGoodsList)
    {

        this.industryName = industryName;
        this.acceptedGoodsList = acceptedGoodsList;
        this.returnedGoodsList = returnedGoodsList;
    }

    public String Name()
    {
        return industryName;
    }

    public boolean needs(GoodsType goodsType)
    {
        return acceptedGoodsList.contains(goodsType);
    }
}
