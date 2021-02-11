package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.models.data.FreightCarDto;

import java.util.List;

public interface FreightCarService
{
    List<FreightCarDto> allFreightCars();

    List<FreightCarDto> allFreightCarsByAARType(String aarType);

    List<FreightCarDto> allFreightCarsThatCarry(String goodsType);

    List<FreightCarDto> allFreightCarsByRoadName(String roadName);

    FreightCarDto saveFreightCarToDatabase(FreightCarDto freightCarToSave);
}
