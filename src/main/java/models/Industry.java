package models;

import java.util.ArrayList;

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
