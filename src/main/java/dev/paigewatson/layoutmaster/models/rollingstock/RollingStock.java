package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.NullableEntity;

public interface RollingStock extends NullableEntity, ConvertsToDto<FreightCar>
{
    boolean isAARType(AARDesignation expectedAARDesignation);
}
