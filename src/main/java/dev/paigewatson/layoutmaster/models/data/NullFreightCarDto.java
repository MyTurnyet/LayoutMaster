package dev.paigewatson.layoutmaster.models.data;

public class NullFreightCarDto extends FreightCarDto
{

    public NullFreightCarDto()
    {
//        this.carTypeDto = new NullCarTypeDto();
    }

    @Override
    public boolean isNull()
    {
        return true;
    }
}
