package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.MongoCarTypeRepository;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
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

    private CarType getEntity(CarTypeDto carTypeDto)
    {
        ArrayList<GoodsType> goods = new ArrayList<>();
        for (String carriedGood : carTypeDto.carriedGoods)
        {
            goods.add(GoodsType.valueOf(carriedGood));
        }
        return new CarType(carTypeDto.id, AARDesignation.valueOf(carTypeDto.aarType), goods);
    }


    private List<CarTypeDto> getCarTypeDtoList()
    {
        final List<CarTypeDto> carTypeDtoList = carTypeRepository.findAll();
        return carTypeDtoList;
    }

    @Override
    public void saveCarTypeToDatabase(CarTypeDto carTypeToSave)
    {
//        final CarTypeDto carTypeWithMatchingAARType = carTypeRepository.findByAarTypeEquals(carTypeToSave.aarType);

        carTypeRepository.save(carTypeToSave);
    }

    @Override
    public List<CarTypeDto> allCarTypes()
    {
        return getCarTypeDtoList();
    }

    @Override
    public CarTypeDto carTypeWithAAR(String expectedAARType)
    {
        return carTypeRepository.findByAarTypeEquals(expectedAARType);
    }
}
