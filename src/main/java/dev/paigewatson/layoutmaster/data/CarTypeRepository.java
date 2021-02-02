package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarTypeRepository extends MongoRepository<CarType, String>
{
    CarType findTopByCarriedGoodsList();
}
