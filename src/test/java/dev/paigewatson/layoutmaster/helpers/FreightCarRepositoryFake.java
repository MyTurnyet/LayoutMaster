package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.data.FreightCarRepository;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;

import java.util.List;

public class FreightCarRepositoryFake extends MongoRepositoryFake<FreightCarDto> implements FreightCarRepository
{
    @Override
    public List<FreightCarDto> findAllByRoadName(String roadName)
    {
        return returnedValues;
    }

    @Override
    public List<FreightCarDto> findAllByCarTypeDto_AarType(String aarType)
    {
        return returnedValues;
    }

    @Override
    public List<FreightCarDto> findAllByCarTypeDtoCarriedGoodsContains(String goodsType)
    {
        return returnedValues;
    }
}
