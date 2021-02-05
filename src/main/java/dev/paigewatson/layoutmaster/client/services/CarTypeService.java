package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;

import java.util.List;

public interface CarTypeService
{
    List<AARDesignation> allAARDesignations();

    CarTypeDto saveCarTypeToDatabase(CarTypeDto carTypeToSave);

    List<CarTypeDto> allCarTypes();

    CarTypeDto carTypeForAAR(String expectedAARType);
}
