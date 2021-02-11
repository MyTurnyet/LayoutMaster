package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FreightCarRepository extends MongoRepository<FreightCarDto, String>
{
    List<FreightCarDto> findAllByRoadName(String roadName);

    List<FreightCarDto> findAllByCarTypeDto_AarType(String aarType);

    List<FreightCarDto> findAllByCarTypeDtoCarriedGoodsContains(String goodsType);
}
