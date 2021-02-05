package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.data.CarTypeRepository;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;

import java.util.List;

public class CarTypeRepositoryFake extends MongoRepositoryFake<CarTypeDto> implements CarTypeRepository
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
