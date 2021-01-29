package models.goods;

/**
 * RequiredGood
 * Know what type it needs
 */
public class RequiredGood implements DeliverableGood
{
    private final GoodsType neededGoodsType;
    private final boolean isAssigned;

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
        return goodsType == neededGoodsType && !this.isAssigned;
    }
}
