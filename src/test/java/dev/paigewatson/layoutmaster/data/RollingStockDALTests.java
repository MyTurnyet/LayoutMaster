package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.helpers.EntityCreator;
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static dev.paigewatson.layoutmaster.helpers.EntityCreator.flatcarType;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class RollingStockDALTests
{
    private final String collectionName = "FreightCars";

    @Nested
    @Tag("Unit")
    class UnitTests
    {
        private final MongoTemplate mongoTemplate;
        private final RollingStockDAL rollingStockMongoDAL;
        private FreightCar boxCar1234;
        private FreightCar boxCar1245;
        private FreightCar flatcar3345;
        private FreightCar gondola3225;

        public UnitTests()
        {

            mongoTemplate = mock(MongoTemplate.class);
            rollingStockMongoDAL = new RollingStockMongoDAL(mongoTemplate);
        }

        @BeforeEach
        public void setup()
        {
            boxCar1234 = new FreightCar(UUID.randomUUID(), "PNWR", 1234, EntityCreator.boxcarType());
            boxCar1245 = new FreightCar(UUID.randomUUID(), "BCR", 1245, EntityCreator.boxcarType());
            flatcar3345 = new FreightCar(UUID.randomUUID(), "BCR", 3345, EntityCreator.flatcarType());
            gondola3225 = new FreightCar(UUID.randomUUID(), "BNSF", 3225, EntityCreator.gondolaType());
        }

        @Test
        public void should_addRollingStockToDatabase()
        {
            when(mongoTemplate.insert(flatcar3345, collectionName)).thenReturn(flatcar3345);

            RollingStock savedRollingStock = rollingStockMongoDAL.insertRollingStock(flatcar3345);
            verify(mongoTemplate).insert(flatcar3345, collectionName);
            assertThat(savedRollingStock).isEqualTo(flatcar3345);
        }

        @Test
        public void should_returnAllInCollection()
        {
            //assign
            when(mongoTemplate.findAll(FreightCar.class, collectionName)).thenReturn(Arrays.asList(boxCar1234, boxCar1245));
            //act
            final List<RollingStock> allRollingStock = rollingStockMongoDAL.getAllRollingStock();
            //assert
            assertThat(allRollingStock.size()).isEqualTo(2);
        }

        @Test
        public void should_deleteAllDocumentsInCollection()
        {
            //assign
            rollingStockMongoDAL.deleteAll();

            //act
            //assert
            verify(mongoTemplate).remove(new Query(), collectionName);
        }

        @Test
        public void should_deleteRollingStock()
        {
            //assign
            rollingStockMongoDAL.delete(boxCar1234);

            //act
            //assert
            verify(mongoTemplate).remove(boxCar1234, collectionName);
        }

        @Test
        public void should_returnAllFreightCarsWihRoaName_BCR()
        {
            when(mongoTemplate.find(
                    query(
                            where("roadName").is("BCR")
                    ),
                    FreightCar.class,
                    collectionName
            )).thenReturn(Arrays.asList(flatcar3345, boxCar1245));
            final List<RollingStock> allOfAARDesignation = rollingStockMongoDAL.getAllOfRoadName("BCR");

            assertThat(allOfAARDesignation.size()).isEqualTo(2);
        }

        @Test
        public void should_returnAllFreightCarOfType()
        {
            when(mongoTemplate.find(
                    query(
                            where("carType.aarDesignation").is("XM")
                    ),
                    FreightCar.class,
                    collectionName
            )).thenReturn(Arrays.asList(boxCar1234, boxCar1245));
            final List<RollingStock> allOfAARDesignation = rollingStockMongoDAL.getAllOfAARDesignation(XM);

            assertThat(allOfAARDesignation.size()).isEqualTo(2);
        }
    }

    @DataMongoTest()
    @ExtendWith(SpringExtension.class)
    @Tag("Mongo")
    @Nested
    public class DataTests
    {
        private final MongoTemplate mongoTemplate;
        private final RollingStockDAL rollingStockMongoDAL;
        private UUID boxcar1234uuid;
        private FreightCar boxcar1234;
        private FreightCar boxcar1245;
        private FreightCar flatcar2234;
        private FreightCar gondola4453;

        public DataTests(@Autowired MongoTemplate mongoTemplate)
        {
            rollingStockMongoDAL = new RollingStockMongoDAL(mongoTemplate);
            this.mongoTemplate = mongoTemplate;
        }

        @BeforeEach
        public void setUp()
        {
            mongoTemplate.remove(new Query(), collectionName);
            boxcar1234uuid = UUID.randomUUID();
            boxcar1234 = new FreightCar(boxcar1234uuid, "PNWR", 1234, EntityCreator.boxcarType());
            boxcar1245 = new FreightCar(UUID.randomUUID(), "BCR", 1245, EntityCreator.boxcarType());
            flatcar2234 = new FreightCar(UUID.randomUUID(), "PNWR", 2234, flatcarType());
            gondola4453 = new FreightCar(UUID.randomUUID(), "BNSF", 4453, EntityCreator.gondolaType());

        }

        @Test
        public void should_returnAllRollingStock()
        {
            insertRollingStockForTesting();

            final List<RollingStock> allRollingStock = rollingStockMongoDAL.getAllRollingStock();
            assertThat(allRollingStock.size()).isEqualTo(4);
        }

        @Test
        public void should_returnAllFreightCars_ofTypeXM()
        {
            //assign
            insertRollingStockForTesting();

            //act
            final List<RollingStock> allOfAARDesignation = rollingStockMongoDAL.getAllOfAARDesignation(XM);
            //assert
            assertThat(allOfAARDesignation.size()).isEqualTo(2);
            assertThat(allOfAARDesignation.stream().allMatch(rollingStock -> rollingStock.isAARType(XM))).isTrue();
        }

        @Test
        public void should_returnAllFreightCars_withRoadName_PNWR()
        {
            //assign
            insertRollingStockForTesting();

            //act
            final List<RollingStock> allOfAARDesignation = rollingStockMongoDAL.getAllOfRoadName("PNWR");
            //assert
            assertThat(allOfAARDesignation.size()).isEqualTo(2);
        }

        @Test
        public void should_insertRollingStock_intoDatabase()
        {
            //act
            final RollingStock rollingStock = rollingStockMongoDAL.insertRollingStock(flatcar2234);

            final List<FreightCar> freightCarList = mongoTemplate.findAll(FreightCar.class, collectionName);
            assertThat(rollingStock.toString()).isEqualTo(flatcar2234.toString());
            assertThat(freightCarList.size()).isEqualTo(1);
            assertThat(freightCarList.get(0).toString()).isEqualTo(flatcar2234.toString());
        }

        private void insertRollingStockForTesting()
        {

            {
                final List<FreightCar> freightCarList = Arrays.asList(boxcar1234, boxcar1245, flatcar2234, gondola4453);
                for (FreightCar freightCar : freightCarList)
                {
                    mongoTemplate.insert(freightCar, collectionName);
                }
            }
        }
    }
}