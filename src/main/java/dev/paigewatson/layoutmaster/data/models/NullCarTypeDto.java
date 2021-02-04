package dev.paigewatson.layoutmaster.data.models;

import dev.paigewatson.layoutmaster.data.NullableEntity;

public class NullCarTypeDto extends CarTypeDto implements NullableEntity
{
    public NullCarTypeDto()
    {
    }

    @Override
    public boolean isNull()
    {
        return true;
    }
}
