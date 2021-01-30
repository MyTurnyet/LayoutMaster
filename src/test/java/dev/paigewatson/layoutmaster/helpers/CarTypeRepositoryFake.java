package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.data.CarTypeRepository;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;

public class CarTypeRepositoryFake extends MongoRepositoryFake<CarType> implements CarTypeRepository
{
    @Override
    public CarType findTopByCarriedGoodsList()
    {
        return null;
    }
}
