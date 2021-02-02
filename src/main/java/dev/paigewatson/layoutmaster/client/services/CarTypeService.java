package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;

import java.util.List;

public interface CarTypeService
{
    List<AARDesignation> allAARDesignations();

    void saveCarTypeToDatabase(CarTypeDto carTypeToSave);

    List<CarTypeDto> allCarTypes();
}
