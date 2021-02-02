package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.helpers.MongoCarTypeRepositoryFake;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.SheetMetal;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarTypeServiceTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {

        private MongoCarTypeRepositoryFake repositoryFake;
        private CarTypeService service;

        @BeforeEach
        public void beforeEachTestRuns()
        {
            repositoryFake = new MongoCarTypeRepositoryFake();
            service = new MongoCarTypeService(repositoryFake);
        }

        @Test
        public void should_returnAllAARDesignations()
        {
            //assign
            //act
            List<AARDesignation> aarDesignations = service.allAARDesignations();
            //assert
            assertThat(aarDesignations.size()).isEqualTo(23);
        }


        @Test
        public void should_returnListOfAllExistingCarTypesFromDatabase()
        {
            //assign
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarTypeDto carTypeDto = new CarTypeDto("FOOO!", "XM", Arrays.asList("SheetMetal"));
            List<CarTypeDto> returnedCarTypes = Arrays.asList(carTypeDto);
            repositoryFake.setReturnedValuesList(returnedCarTypes);

            //act
            List<CarType> allCarTypesList = service.allCarTypes();
            //assert
            assertThat(allCarTypesList.size()).isEqualTo(1);
            assertThat(allCarTypesList.get(0).canCarry(SheetMetal)).isTrue();
            assertThat(allCarTypesList.get(0).isOfType(XM)).isTrue();
        }

        @Test
        public void should_saveCarTypeToRepository()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarTypeDto carTypeDto = new CarTypeDto("", "XM", Arrays.asList("Ingredients"));

            final CarType boxCarType = new CarType(XM, carriedGoodsList);

            //act
            service.saveCarTypeToDatabase(boxCarType);
            //assert
            assertThat(repositoryFake.savedEntity.id).isEqualTo(carTypeDto.id);
            assertThat(repositoryFake.savedEntity.aarType).isEqualTo(carTypeDto.aarType);
            assertThat(repositoryFake.savedEntity.carriedGoods).isEqualTo(carTypeDto.carriedGoods);
        }
    }
}