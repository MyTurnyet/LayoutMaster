package models.goods;

import models.goods.DeliverableGood;
import models.goods.GoodsType;

/**
 * RequiredGood
 * Know what type it needs
 */
public class RequiredGood implements DeliverableGood
{
    private final GoodsType neededGoodsType;
    private boolean isAssigned;

    public RequiredGood(GoodsType neededGoodsType)
    {
        this(neededGoodsType, false);
    }

    public RequiredGood(GoodsType neededGoodsType, boolean isAssigned)
    {
        this.isAssigned = isAssigned;
        this.neededGoodsType = neededGoodsType;
    }

    public boolean needs(GoodsType goodsType)
    {
        return goodsType == neededGoodsType;
    }


    public boolean isAssigned()
    {
        return isAssigned;
    }
}
