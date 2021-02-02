package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.MongoCarTypeRepository;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        final List<CarTypeDto> carTypeDtoList = carTypeRepository.findAll();
        ArrayList<CarType> carTypeList = new ArrayList<>();
        for (CarTypeDto carTypeDto : carTypeDtoList)
        {
            carTypeList.add(carTypeDto.getEntity());
        }
        return carTypeList;
    }

    @Override
    public void saveCarTypeToDatabase(CarType carTypeToSave)
    {
        carTypeToSave.saveToRepository(carTypeRepository);
    }


}
