package dev.paigewatson.layoutmaster.data.models;

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
