package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
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

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Logs;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.GS;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class CarTypeDALTests
{
    private final String collectionName = "AARTypes";

    @Nested
    @Tag("Unit")
    public class UnitTests
    {
        private final MongoTemplate mongoTemplate;
        private final CarTypeMongoDAL carTypeMongoDAL;

        public UnitTests()
        {

            mongoTemplate = mock(MongoTemplate.class);
            carTypeMongoDAL = new CarTypeMongoDAL(mongoTemplate);
        }

        @Test
        public void should_deleteAllRecordsInCollection()
        {
            carTypeMongoDAL.deleteAll();
            verify(mongoTemplate).remove(new Query(), collectionName);
        }

        @Test
        public void should_queryWhereAARType_isXM()
        {
            //assign
            final AARType boxcarType = new AARType(XM, Arrays.asList(Ingredients, Logs));
            final Query query = query(where("aarDesignation").is(XM));
            when(mongoTemplate.findOne(query, AARType.class)).thenReturn(boxcarType);

            //act
            final CarType byAarType = carTypeMongoDAL.findByAarType(XM);

            //assert
            verify(mongoTemplate).findOne(any(), any());
            assertThat(byAarType).isEqualTo(boxcarType);
        }

        @Test
        public void should_returnNullCarType_whenNoCarTypeByAAR_exists()
        {
            //assign
            final CarType boxcarType = new AARType(XM, Arrays.asList(Ingredients, Logs));
            final Query query = query(where("aarDesignation").is(XM));
            when(mongoTemplate.findOne(query, CarType.class)).thenReturn(null);

            //act
            final CarType byAarType = carTypeMongoDAL.findByAarType(XM);

            //assert
            assertThat(byAarType.isNull()).isTrue();
        }
    }

    @DataMongoTest()
    @ExtendWith(SpringExtension.class)
    @Tag("Mongo")
    @Nested
    public class DataTests
    {
        private final CarTypeDAL carTypeMongoDAL;
        private final MongoTemplate mongoTemplate;
        private AARType boxcarType;
        private AARType gondolaType;
        private UUID gondolaUUID;
        private UUID boxcarUUID;


        public DataTests(@Autowired MongoTemplate mongoTemplate)
        {
            carTypeMongoDAL = new CarTypeMongoDAL(mongoTemplate);
            this.mongoTemplate = mongoTemplate;
        }

        @BeforeEach
        public void setUp()
        {
            mongoTemplate.remove(new Query(), collectionName);
            boxcarUUID = UUID.randomUUID();
            boxcarType = new AARType(boxcarUUID, XM, Arrays.asList(Ingredients, Logs));
            gondolaUUID = UUID.randomUUID();
            gondolaType = new AARType(gondolaUUID, GS, Arrays.asList(Logs));
        }

        @Test
        public void should_saveListOf_CarTypes_ToTheDatabase()
        {
            //assign
            //act
            carTypeMongoDAL.insertMultipleCarTypes(Arrays.asList(boxcarType, gondolaType));
            //assert
            final List<AARType> allExistingCarTypes = mongoTemplate.findAll(AARType.class);
            assertThat(allExistingCarTypes.size()).isEqualTo(2);
        }

        @Test
        public void should_findByAARType()
        {
            //assign
            insertCarTypesForTesting();

            //act
            final CarType byAarType = carTypeMongoDAL.findByAarType(XM);

            //assert
            assertThat(byAarType).isEqualTo(boxcarType);

        }

        private void insertCarTypesForTesting()
        {
            final List<AARType> aarTypes = Arrays.asList(boxcarType, gondolaType);
            aarTypes.forEach(mongoTemplate::insert);
        }
    }
}