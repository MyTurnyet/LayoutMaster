package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.helpers.CarTypeDALFake;
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.SheetMetal;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.FC;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.GS;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarTypeServiceTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {

        private CarTypeService service;
        private CarTypeDALFake carTypeDALFake;

        @BeforeEach
        public void beforeEachTestRuns()
        {
            carTypeDALFake = new CarTypeDALFake();
            service = new MongoCarTypeService(carTypeDALFake);
        }

        @Test
        public void should_returnAllAARDesignations()
        {
            //assign
            carTypeDALFake.setReturnAARDesignationsList(Arrays.asList(XM, GS, FC));
            //act
            List<AARDesignation> aarDesignations = service.allAARDesignations();
            //assert
            assertThat(aarDesignations.size()).isEqualTo(3);
        }


        @Test
        public void should_returnListOfAllExistingCarTypesFromDatabase()
        {
            //assign
            //assign

            final List<GoodsType> carriedGoods = Collections.singletonList(SheetMetal);
            final CarType boxcarType = TestAARTypeCreator.boxcarType();
            List<CarType> returnedCarTypes = Collections.singletonList(boxcarType);
            carTypeDALFake.setReturnedEntityList(returnedCarTypes);

            //act
            List<CarType> allCarTypesList = service.allCarTypes();
            //assert
            assertThat(allCarTypesList.size()).isEqualTo(1);
            assertThat(allCarTypesList.get(0)).isEqualTo(boxcarType);
        }

        @Test
        public void should_updateOnSaveCarTypeToRepository()
        {
            //assign
            final CarType boxcarType = TestAARTypeCreator.boxcarType();
            final CarType existingBoxcarType = TestAARTypeCreator.boxcarType();
            carTypeDALFake.setCurrentSavedEntity(existingBoxcarType);

            //act
            final CarType returnedCarType = service.saveCarTypeToDatabase(boxcarType);
            //assert
            assertThat(carTypeDALFake.savedEntity()).isEqualTo(boxcarType);
            assertThat(returnedCarType).isEqualTo(boxcarType);
        }

        @Test
        public void should_insertOnSaveCarTypeToRepository()
        {
            //assign
            final CarType boxcarType = TestAARTypeCreator.boxcarType();

            //act
            final CarType returnedCarTypeDto = service.saveCarTypeToDatabase(boxcarType);
            //assert
            assertThat(carTypeDALFake.savedEntity().toString()).isEqualTo(returnedCarTypeDto.toString());
        }

        @Test
        public void should_getExistingCarTypeByAAR()
        {
            //assign
            final CarType existingCarType = TestAARTypeCreator.gondolaType();
            carTypeDALFake.setEntityToReturn(existingCarType);
            //act
            final CarType carTypeForAAR = service.carTypeForAAR(GS);
            //assert

            assertThat(carTypeForAAR).isEqualTo(existingCarType);
        }

        @Test
        public void should_getNullCarType_whenNonExistent_AAR()
        {
            //assign
            //act
            final CarType carTypeForAAR = service.carTypeForAAR(GS);
            //assert

            assertThat(carTypeForAAR.isNull()).isTrue();
        }

        @Test
        public void should_getCarTypesThatCarry_goods()
        {
            //assign
            final CarType boxcarType = TestAARTypeCreator.boxcarType();
            final CarType gondolaCarType = TestAARTypeCreator.gondolaType();
            carTypeDALFake.setReturnedEntityList(Arrays.asList(boxcarType, gondolaCarType));
            //act
            final List<CarType> carTypesThatCarryGoods = service.carTypesThatCarryGoodsType(Parts);
            //assert
            assertThat(carTypesThatCarryGoods.size()).isEqualTo(2);
            assertThat(carTypesThatCarryGoods.stream().anyMatch(carType -> carType == boxcarType));
            assertThat(carTypesThatCarryGoods.stream().anyMatch(carType -> carType == gondolaCarType));
        }
    }
}