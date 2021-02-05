package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.data.models.FreightCarDto;

import java.util.List;

public class FreightCarServiceFake implements FreightCarService
{
    @Override
    public List<FreightCarDto> allFreightCars()
    {
        return null;
    }

    @Override
    public List<FreightCarDto> allFreightCarsByAARType(String aarType)
    {
        return null;
    }

    @Override
    public List<FreightCarDto> allFreightCarsThatCarry(String goodsType)
    {
        return null;
    }

    @Override
    public List<FreightCarDto> allFreightCarsByRoadName(String roadName)
    {
        return null;
    }

    @Override
    public FreightCarDto saveFreightCarToDatabase(FreightCarDto freightCarToSave)
    {
        return null;
    }
}
