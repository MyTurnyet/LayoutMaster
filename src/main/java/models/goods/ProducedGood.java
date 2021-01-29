package models.goods;

import models.goods.DeliverableGood;
import models.goods.GoodsType;

public class ProducedGood implements DeliverableGood
{
    private final GoodsType goodsType;
    private boolean isAssigned;

    public ProducedGood(GoodsType goodsType)
    {
        this(goodsType, false);
    }

    public ProducedGood(GoodsType goodsType, boolean isAssigned)
    {
        this.goodsType = goodsType;
        this.isAssigned = isAssigned;
    }

    public boolean isOfType(GoodsType expectedType)
    {
        return expectedType == this.goodsType;
    }

    public boolean isAssigned()
    {
        return isAssigned;
    }
}
