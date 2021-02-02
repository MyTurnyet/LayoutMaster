package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;

import java.util.Arrays;
import java.util.List;

public class CarTypeServiceFake implements CarTypeService
{
    private List<CarTypeDto> returnedCarTypeDTOs;
    private CarTypeDto savedDTOEntity;

    @Override
    public List<AARDesignation> allAARDesignations()
    {
        return Arrays.asList(AARDesignation.class.getEnumConstants());
    }

    @Override
    public void saveCarTypeToDatabase(CarTypeDto carTypeToSave)
    {
        this.savedDTOEntity = carTypeToSave;
    }

    @Override
    public List<CarTypeDto> allCarTypes()
    {
        return this.returnedCarTypeDTOs;
    }

    public void setReturnedCarTypeDTOs(List<CarTypeDto> returnedCarTypeDTOs)
    {
        this.returnedCarTypeDTOs = returnedCarTypeDTOs;
    }

    public CarTypeDto savedDtoEntity()
    {
        return this.savedDTOEntity;
    }
}
