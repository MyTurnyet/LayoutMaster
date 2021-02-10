package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class CarTypeMongoDAL implements CarTypeDAL
{
    private final MongoTemplate mongoTemplate;

    private final String collectionName = "AARTypes";

    public CarTypeMongoDAL(@Autowired MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<AARType> findAllByCarTypesThatCanCarry(GoodsType expectedGoods)
    {
        final List<AARType> carTypesThatCarryGoods = mongoTemplate
                .find(query(
                        where("carriedGoodsList")
                                .is(expectedGoods.toString())),
                        AARType.class);
        return carTypesThatCarryGoods;
    }

    @Override
    public CarType findByAarType(AARDesignation aarDesignation)
    {
        final AARType returnedCarType = mongoTemplate.findOne(
                query(
                        where("aarDesignation")
                                .is(aarDesignation)),
                AARType.class,
                collectionName
        );
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
    public CarType insertCarType(CarType carTypeToSave)
    {
        mongoTemplate.findAndRemove(
                query(
                        where("aarDesignation")
                                .is(carTypeToSave.displayName())),
                AARType.class,
                collectionName);
        return mongoTemplate.insert(carTypeToSave, collectionName);
    }

    @Override
    public void delete(CarType carTypeToDelete)
    {
        mongoTemplate.remove(carTypeToDelete, collectionName);
    }

    @Override
    public List<CarType> findAll()
    {
        final List<AARType> allAARTypes = mongoTemplate.findAll(AARType.class, collectionName);
        return Collections.unmodifiableList(allAARTypes);
    }
}
