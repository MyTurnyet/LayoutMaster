package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.MongoCarTypeRepository;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.data.models.NullCarTypeDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MongoCarTypeService implements CarTypeService
{
    private final MongoCarTypeRepository carTypeRepository;

    public MongoCarTypeService(@Autowired MongoCarTypeRepository carTypeRepository)
    {
        this.carTypeRepository = carTypeRepository;
    }

    public List<AARDesignation> allAARDesignations()
    {
        return Arrays.asList(AARDesignation.class.getEnumConstants());
    }

    private List<CarTypeDto> getCarTypeDtoList()
    {
        return carTypeRepository.findAll();
    }

    @Override
    public CarTypeDto saveCarTypeToDatabase(CarTypeDto carTypeToSave)
    {
        final CarTypeDto carTypeWithMatchingAARType = carTypeForAAR(carTypeToSave.aarType);
        if (carTypeWithMatchingAARType.isNull())
        {
            return carTypeRepository.insert(carTypeToSave);
        }
        carTypeWithMatchingAARType.carriedGoods = carTypeToSave.carriedGoods;
        carTypeRepository.save(carTypeWithMatchingAARType);
        return carTypeWithMatchingAARType;
    }

    @Override
    public List<CarTypeDto> allCarTypes()
    {
        return getCarTypeDtoList();
    }

    @Override
    public CarTypeDto carTypeForAAR(String expectedAARType)
    {
        final CarTypeDto carTypeByAAR = carTypeRepository.findByAarTypeEquals(expectedAARType);
        return carTypeByAAR == null ? new NullCarTypeDto() : carTypeByAAR;
    }
}
