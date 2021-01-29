package models;

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


    public void markAssignedToDeliver()
    {
        isAssigned = true;
    }

    public boolean isAssigned()
    {
        return isAssigned;
    }
}
