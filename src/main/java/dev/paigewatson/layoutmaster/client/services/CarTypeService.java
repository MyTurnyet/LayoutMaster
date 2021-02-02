package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;

import java.util.List;

public interface CarTypeService
{
    List<AARDesignation> allAARDesignations();

    List<CarType> allCarTypes();

    void saveCarTypeToDatabase(CarType carTypeToSave);
}
