package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.data.models.NullCarTypeDto;

public class NullFreightCarDto extends FreightCarDto
{

    public NullFreightCarDto()
    {
        this.carTypeDto = new NullCarTypeDto();
    }

    @Override
    public boolean isNull()
    {
        return true;
    }
}
