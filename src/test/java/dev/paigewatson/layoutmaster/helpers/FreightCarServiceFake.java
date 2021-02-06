package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.data.models.FreightCarDto;

import java.util.List;

public class FreightCarServiceFake implements FreightCarService
{
    private List<FreightCarDto> returnedFreightCarsList;

    @Override
    public List<FreightCarDto> allFreightCars()
    {
        return returnedFreightCarsList;
    }

    @Override
    public List<FreightCarDto> allFreightCarsByAARType(String aarType)
    {
        return returnedFreightCarsList;
    }

    @Override
    public List<FreightCarDto> allFreightCarsThatCarry(String goodsType)
    {
        return returnedFreightCarsList;
    }

    @Override
    public List<FreightCarDto> allFreightCarsByRoadName(String roadName)
    {
        return returnedFreightCarsList;
    }

    @Override
    public FreightCarDto saveFreightCarToDatabase(FreightCarDto freightCarToSave)
    {
        return null;
    }

    public void setReturnedFreightCars(List<FreightCarDto> returnedFreightCarsList)
    {
        this.returnedFreightCarsList = returnedFreightCarsList;
    }
}
