package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.CarTypeRepository;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MongoCarTypeService implements CarTypeService
{
    private final CarTypeRepository carTypeRepository;

    public MongoCarTypeService(@Autowired CarTypeRepository carTypeRepository)
    {
        this.carTypeRepository = carTypeRepository;
    }

    public List<AARDesignation> allAARDesignations()
    {
        return Arrays.asList(AARDesignation.class.getEnumConstants());
    }

    public List<CarType> allCarTypes()
    {
        return carTypeRepository.findAll();
    }
}
