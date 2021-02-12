package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.NullableEntity;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;

public interface RollingStock extends NullableEntity, ConvertsToDto<FreightCarDto>
{
    boolean isAARType(AARDesignation expectedAARDesignation);

    boolean isCarrying(GoodsType goodsType);

    void load(GoodsType goodsType);
}
