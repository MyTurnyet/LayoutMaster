package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class CarTypeMongoDAL implements CarTypeDAL
{
    private final MongoTemplate mongoTemplate;

    @Value("{spring.data.mongodb.database}")
    private final String collectionName = "";

    public CarTypeMongoDAL(@Autowired MongoTemplate mongoTemplate)
    {

        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<AARType> findAllByCarTypesThatCanCarry(GoodsType expectedGoods)
    {
        return mongoTemplate.find(
                Query.query(where(expectedGoods.toString()).in("carriedGoods")), AARType.class
        );
    }

    @Override
    public CarType findByAarType(AARDesignation aarDesignation)
    {
        final Query query = new Query();
        query.addCriteria(where("aarDesignation").is(aarDesignation));
        final AARType returnedCarType = mongoTemplate.findOne(query, AARType.class);
        if (returnedCarType == null)
        {
            return new NullCarType();
        }
        return returnedCarType;
    }

    @Override
    public void deleteAll()
    {
        mongoTemplate.remove(new Query(), collectionName);
    }

    @Override
    public List<CarType> insertMultipleCarTypes(List<CarType> carTypeList)
    {
        carTypeList.forEach(mongoTemplate::insert);
        return carTypeList;
    }
}
