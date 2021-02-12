package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class RollingStockMongoDAL implements RollingStockDAL
{
    private final MongoTemplate mongoTemplate;
    private final String collectionName = "FreightCars";

    public RollingStockMongoDAL(@Autowired MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<RollingStock> getAllRollingStock()
    {
        final List<FreightCar> allReturnedFreightCars = mongoTemplate.findAll(FreightCar.class, collectionName);
        return Collections.unmodifiableList(allReturnedFreightCars);
    }

    @Override
    public void deleteAll()
    {
        mongoTemplate.remove(new Query(), collectionName);
    }

    @Override
    public void delete(RollingStock rollingStockToDelete)
    {
        mongoTemplate.remove(rollingStockToDelete, collectionName);
    }

    @Override
    public List<RollingStock> getAllOfAARDesignation(AARDesignation aarDesignation)
    {
        final List<FreightCar> freightCarsByAARList = mongoTemplate.find(
                query(
                        where("carType.aarDesignation").is(aarDesignation.name())
                ),
                FreightCar.class,
                collectionName
        );
        return Collections.unmodifiableList(freightCarsByAARList);
    }

    @Override
    public List<RollingStock> getAllOfRoadName(String roadName)
    {
        final List<FreightCar> freightCarList = mongoTemplate.find(
                query(
                        where("roadName").is(roadName)
                ),
                FreightCar.class,
                collectionName
        );
        return Collections.unmodifiableList(freightCarList);
    }

    @Override
    public RollingStock insertRollingStock(RollingStock rollingStockToSave)
    {
        return mongoTemplate.insert(rollingStockToSave, collectionName);
    }
}
