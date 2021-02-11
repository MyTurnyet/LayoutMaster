package dev.paigewatson.layoutmaster.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class RollingStockMongoDAL implements RollingStockDAL
{
    private final MongoTemplate mongoTemplate;
    private final String collectionName = "FreightCars";

    public RollingStockMongoDAL(@Autowired MongoTemplate mongoTemplate)
    {
        this.mongoTemplate = mongoTemplate;
    }
}
