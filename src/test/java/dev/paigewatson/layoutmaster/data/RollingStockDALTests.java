package dev.paigewatson.layoutmaster.data;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.Mockito.mock;

public class RollingStockDALTests
{
    private final String collectionName = "FreightCars";

    @Nested
    @Tag("Unit")
    class UnitTests
    {
        private final MongoTemplate mongoTemplate;
        private final RollingStockDAL rollingStockMongoDAL;

        public UnitTests()
        {

            mongoTemplate = mock(MongoTemplate.class);
            rollingStockMongoDAL = new RollingStockMongoDAL(mongoTemplate);
        }

        @Test
        public void should_returnAllInCollection()
        {
            //assign
//            new FreightCar(UUID.randomUUID(), "PNWR", "1234", )

            //act

            //assert

        }
    }
}