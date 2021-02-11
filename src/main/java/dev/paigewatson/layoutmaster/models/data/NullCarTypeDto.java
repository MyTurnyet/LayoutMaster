package dev.paigewatson.layoutmaster.models.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType;

public class NullCarTypeDto implements CarTypeDto<NullCarType>
{
    public NullCarTypeDto()
    {
    }

    @Override
    public boolean isNull()
    {
        return true;
    }

    @Override
    @JsonIgnore
    public NullCarType getEntity()
    {
        return new NullCarType();
    }
}
