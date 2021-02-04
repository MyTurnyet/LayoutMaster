package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.data.MongoCarTypeRepository;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;

import java.util.List;

public class MongoCarTypeRepositoryFake extends MongoRepositoryFake<CarTypeDto> implements MongoCarTypeRepository
{

    private CarTypeDto existingCarTypeDto;

    @Override
    public List<CarTypeDto> findAllByCarriedGoodsContains(String carriedGood)
    {
        return null;
    }

    @Override
    public CarTypeDto findByAarTypeEquals(String aarType)
    {
        return existingCarTypeDto;
    }

    public void findByAarReturned(CarTypeDto existingCarTypeDto)
    {
        this.existingCarTypeDto = existingCarTypeDto;
    }
}
