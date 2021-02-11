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

import static dev.paigewatson.layoutmaster.helpers.EntityCreator.boxCar;
import static dev.paigewatson.layoutmaster.helpers.EntityCreator.flatcar;
import static dev.paigewatson.layoutmaster.helpers.EntityCreator.gondola;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Aggregates;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Logs;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Lumber;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.MetalScraps;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Paper;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.FC;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.GS;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        public void should_returnAllInCollection()
        {
            //assign
            UUID boxcarUUID = UUID.randomUUID();
            AARType boxcarType = new AARType(boxcarUUID, XM, Arrays.asList(Ingredients, Logs));
            UUID gondolaUUID = UUID.randomUUID();
            AARType gondolaType = new AARType(gondolaUUID, GS, Arrays.asList(MetalScraps, Aggregates));
            UUID flatcarUUID = UUID.randomUUID();
            AARType flatcarType = new AARType(flatcarUUID, FC, Arrays.asList(Logs, Lumber));

            when(mongoTemplate.findAll(AARType.class, collectionName))
                    .thenReturn(Arrays.asList(boxcarType, flatcarType, gondolaType));

            //act
            final List<CarType> allCarTypes = carTypeMongoDAL.findAll();

            //assert
            assertThat(allCarTypes.size()).isEqualTo(3);
            verify(mongoTemplate).findAll(AARType.class, collectionName);
        }

        @Test
        public void should_deleteAllRecordsInCollection()
        {
            carTypeMongoDAL.deleteAll();
            verify(mongoTemplate).remove(new Query(), collectionName);
        }

        @Test
        public void should_deleteRecord_ById()
        {
            final CarType boxcarType = new AARType(XM, Arrays.asList(Ingredients, Logs));
            carTypeMongoDAL.delete(boxcarType);
            verify(mongoTemplate).remove(boxcarType, collectionName);
        }

        @Test
        public void should_removeCarType_fromDatabase()
        {
            //assign
            final CarType boxcarType = boxCar();

            //act
            carTypeMongoDAL.delete(boxcarType);

            //assert
            verify(mongoTemplate).remove(boxcarType, collectionName);
        }

        @Test
        public void should_insertCarType()
        {
            //assign
            final CarType boxcarType = boxCar();

            //act
            carTypeMongoDAL.insertCarType(boxcarType);
            //assert
            verify(mongoTemplate).insert(boxcarType, collectionName);
        }

        @Test
        public void should_removeExistingThen_insertCarType()
        {
            //assign
            final CarType boxcarTypeToAdd = boxCar();

            //act
            carTypeMongoDAL.insertCarType(boxcarTypeToAdd);
            //assert
            verify(mongoTemplate).findAndRemove(query(where("aarDesignation").is(XM)), AARType.class, collectionName);
            verify(mongoTemplate).insert(boxcarTypeToAdd, collectionName);
        }

        @Test
        public void should_queryWhereAARType_isXM()
        {
            //assign
            final AARType boxcarType = new AARType(XM, Arrays.asList(Ingredients, Logs));
            final Query query = query(where("aarDesignation").is(XM));
            when(mongoTemplate.findOne(query, AARType.class, collectionName)).thenReturn(boxcarType);

            //act
            final CarType byAarType = carTypeMongoDAL.findByAarType(XM);

            //assert
            verify(mongoTemplate).findOne(query, AARType.class, collectionName);
            assertThat(byAarType).isEqualTo(boxcarType);
        }

        @Test
        public void should_returnNullCarType_whenNoCarTypeByAAR_exists()
        {
            //assign
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
        private CarType boxcarType;
        private CarType boxcarType2;
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
            boxcarType = boxCar(boxcarUUID);
            boxcarType2 = new AARType(UUID.randomUUID(), XM, Arrays.asList(Paper, Parts, Logs));
        }

        @Test
        public void should_SaveCarTypes_ToDatabase()
        {
            //assign
            //act
            carTypeMongoDAL.insertCarType(boxcarType2);
            //assert
            final List<AARType> allExistingCarTypes = mongoTemplate.findAll(AARType.class);
            assertThat(allExistingCarTypes.size()).isEqualTo(1);
        }

        @Test
        public void should_replaceExistingWhenCalling_insertCarTypes_ToDatabase()
        {
            //assign
            insertCarTypesForTesting();
            //act
            carTypeMongoDAL.insertCarType(boxcarType2);
            //assert
            final List<AARType> allExistingCarTypes = mongoTemplate.findAll(AARType.class);
            assertThat(allExistingCarTypes.size()).isEqualTo(3);
        }

        @Test
        public void should_findByAARType()
        {
            //assign
            insertCarTypesForTesting();

            //act
            final CarType byAarType = carTypeMongoDAL.findByAarType(XM);

            //assert
            assertThat(byAarType.toString()).isEqualTo(boxcarType.toString());

        }

        @Test
        public void should_returnAllTypesThatCanCarry_Parts()
        {
            //assign
            insertCarTypesForTesting();

            //act
            final List<CarType> allByCarTypesThatCanCarry = carTypeMongoDAL.findAllByCarTypesThatCanCarry(Parts);

            //assert
            assertThat(allByCarTypesThatCanCarry.size()).isEqualTo(2);
            assertThat(allByCarTypesThatCanCarry.stream().allMatch(aarType -> aarType.canCarry(Parts))).isTrue();
        }

        @Test
        public void should_returnEmptyListOf_AllTypesThatCanCarry_Paper()
        {
            //assign
            insertCarTypesForTesting();

            //act
            final List<CarType> allByCarTypesThatCanCarry = carTypeMongoDAL.findAllByCarTypesThatCanCarry(Paper);

            //assert
            assertThat(allByCarTypesThatCanCarry.size()).isEqualTo(0);
        }

        private void insertCarTypesForTesting()
        {
            final AARType boxcar = boxCar(boxcarUUID);
            final AARType gondola = gondola();
            final AARType flatcar = flatcar();
            final List<AARType> aarTypes = Arrays.asList(boxcar, gondola, flatcar);
            for (AARType aarType : aarTypes)
            {
                mongoTemplate.insert(aarType, collectionName);
            }
        }
    }
}