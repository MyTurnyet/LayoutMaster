package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.data.MongoCarTypeRepository;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;

public class MongoCarTypeRepositoryFake extends MongoRepositoryFake<CarType> implements MongoCarTypeRepository
{
    @Override
    public CarType findTopByCarriedGoodsList()
    {
        return null;
    }
}
