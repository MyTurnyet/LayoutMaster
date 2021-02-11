package dev.paigewatson.layoutmaster.data.models;

import dev.paigewatson.layoutmaster.data.NullableEntity;

public interface EntityDto<T> extends NullableEntity, CastAsEntity<T>
{
}
