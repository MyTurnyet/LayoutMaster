package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.MongoCarTypeRepository;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
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

    public List<CarType> allCarTypes()
    {
        return carTypeRepository.findAll();
    }

    @Override
    public void saveCarTypeToDatabase(CarType carTypeToSave)
    {
        carTypeRepository.save(carTypeToSave);
    }


}
