package dev.paigewatson.layoutmaster.data.models;

import dev.paigewatson.layoutmaster.data.NullableEntity;

public class NullGoodsTypeDto extends GoodsTypeDto implements NullableEntity
{
    @Override
    public boolean isNull()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
