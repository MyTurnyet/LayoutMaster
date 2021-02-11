package dev.paigewatson.layoutmaster.models.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.paigewatson.layoutmaster.models.NullableEntity;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface EntityDto<T> extends NullableEntity, CastAsEntity<T>
{
}
