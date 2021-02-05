package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.helpers.MongoCarTypeRepositoryFake;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

            final List<String> carriedGoods = Collections.singletonList("SheetMetal");
            final CarTypeDto carTypeDto = new CarTypeDto("XM", carriedGoods);
            List<CarTypeDto> returnedCarTypes = Collections.singletonList(carTypeDto);
            repositoryFake.setReturnedValuesList(returnedCarTypes);

            //act
            List<CarTypeDto> allCarTypesList = service.allCarTypes();
            //assert
            assertThat(allCarTypesList.size()).isEqualTo(1);
            assertThat(allCarTypesList.get(0).carriedGoods).isEqualTo(carriedGoods);
            assertThat(allCarTypesList.get(0).aarType).isEqualTo("XM");
//            assertThat(allCarTypesList.get(0).id).isEqualTo("FOOO!");

        }

        @Test
        public void should_returnAllCarTypesAsDTOs()
        {
            //assign
            final CarTypeDto carTypeDto = new CarTypeDto("XM", Collections.singletonList("SheetMetal"));
            List<CarTypeDto> returnedCarTypes = Collections.singletonList(carTypeDto);
            repositoryFake.setReturnedValuesList(returnedCarTypes);


            //act
            final List<CarTypeDto> allCarTypesAsDTOs = service.allCarTypes();

            //assert
            assertThat(allCarTypesAsDTOs.size()).isEqualTo(1);
            assertThat(allCarTypesAsDTOs.get(0)).isEqualTo(carTypeDto);
        }

        @Test
        public void should_updateOnSaveCarTypeToRepository()
        {
            //assign
            final CarTypeDto carTypeDto = new CarTypeDto("XM", Arrays.asList("Parts", "Paper"));
            final CarTypeDto existingCarTypeDto = new CarTypeDto("XM", Collections.singletonList("Ingredients"));
            repositoryFake.findByAarReturned(existingCarTypeDto);

            //act
            final CarTypeDto returnedCarTypeDto = service.saveCarTypeToDatabase(carTypeDto);
            //assert
            assertThat(repositoryFake.savedEntity.toString()).isEqualTo(returnedCarTypeDto.toString());
            assertThat(returnedCarTypeDto.getId()).isEqualTo(existingCarTypeDto.getId());
        }

        @Test
        public void should_insertOnSaveCarTypeToRepository()
        {
            //assign
            final CarTypeDto carTypeDto = new CarTypeDto("XM", Arrays.asList("Parts", "Paper"));

            //act
            final CarTypeDto returnedCarTypeDto = service.saveCarTypeToDatabase(carTypeDto);
            //assert
            assertThat(repositoryFake.savedEntity.toString()).isEqualTo(returnedCarTypeDto.toString());
        }

        @Test
        public void should_getExistingCarTypeByAAR()
        {
            //assign
            final CarTypeDto existingCarTypeDto = new CarTypeDto("GS", Collections.singletonList("Ingredients"));
            repositoryFake.findByAarReturned(existingCarTypeDto);
            //act
            final CarTypeDto carTypeForAAR = service.carTypeForAAR("XM");
            //assert

            assertThat(carTypeForAAR.getId()).isEqualTo(existingCarTypeDto.getId());
            assertThat(carTypeForAAR.aarType).isEqualTo(existingCarTypeDto.aarType);
            assertThat(carTypeForAAR.carriedGoods).isEqualTo(existingCarTypeDto.carriedGoods);
        }

        @Test
        public void should_returnNullCarTypeDtp_fromCarTypeWithAAR()
        {
            //assign
            //act
            final CarTypeDto carTypeForAAR = service.carTypeForAAR("XM");
            //assert

            assertThat(carTypeForAAR.isNull()).isTrue();
        }
    }
}