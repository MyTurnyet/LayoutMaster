package dev.paigewatson.layoutmaster.data.models;

import dev.paigewatson.layoutmaster.models.rollingstock.CarTypeDto;
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
    public NullCarType getEntity()
    {
        return new NullCarType();
    }
}
