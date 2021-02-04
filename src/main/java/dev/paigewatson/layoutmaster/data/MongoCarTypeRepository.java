package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoCarTypeRepository extends MongoRepository<CarTypeDto, String>
{
    List<CarTypeDto> findAllByCarriedGoodsContains(String carriedGood);

    CarTypeDto findByAarTypeEquals(String aarType);
}
