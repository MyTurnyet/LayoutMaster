package dev.paigewatson.layoutmaster.models.data;

import dev.paigewatson.layoutmaster.models.NullableEntity;

public interface EntityDto<T> extends NullableEntity, CastAsEntity<T>
{
}
