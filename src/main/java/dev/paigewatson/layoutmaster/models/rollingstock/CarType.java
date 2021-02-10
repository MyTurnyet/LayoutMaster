package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.data.NullableEntity;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;

public interface CarType extends NullableEntity
{
    boolean canCarry(GoodsType goodsType);

    boolean isOfType(AARDesignation designation);

    AARDesignation displayName();

    CarType saveToDatabase(CarTypeDAL carTypeDAL);
}
