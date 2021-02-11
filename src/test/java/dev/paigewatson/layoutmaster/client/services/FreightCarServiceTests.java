package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.models.AARTypeDto;
import dev.paigewatson.layoutmaster.data.models.FreightCarDto;
import dev.paigewatson.layoutmaster.helpers.FreightCarRepositoryFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FreightCarServiceTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {

        private FreightCarRepositoryFake repositoryFake;
        private FreightCarService freightCarService;
        private FreightCarDto boxcarOne;
        private FreightCarDto boxcarTwo;
        private FreightCarDto boxcarThree;
        private FreightCarDto gondolaOne;
        private FreightCarDto gondolaTwo;
        private FreightCarDto gondolaThree;
        private FreightCarDto flatCarOne;
        private AARTypeDto boxcarTypeDto;
        private UUID boxcarOneUUID;

        @BeforeEach
        public void setupTests()
        {
            repositoryFake = new FreightCarRepositoryFake();
            boxcarTypeDto = new AARTypeDto("XM", Arrays.asList("Ingredients", "Paper", "Logs"));
            final AARTypeDto flatcarTypeDto = new AARTypeDto("FC", Arrays.asList("Parts", "Logs"));
            boxcarOneUUID = UUID.randomUUID();
            boxcarOne = new FreightCarDto(boxcarOneUUID, "PNWR", 2145, boxcarTypeDto);
            boxcarTwo = new FreightCarDto("BCR", 2342, boxcarTypeDto);
            boxcarThree = new FreightCarDto("PNWR", 2335, boxcarTypeDto);

            AARTypeDto gondolatTypeDto = new AARTypeDto("GS", Arrays.asList("MetalScraps", "ScrapMetal", "Aggregates"));
            gondolaOne = new FreightCarDto("BNSF", 1234, gondolatTypeDto);
            flatCarOne = new FreightCarDto("ATSF", 1232, flatcarTypeDto);
            gondolaTwo = new FreightCarDto("PNWR", 1235, gondolatTypeDto);
            gondolaThree = new FreightCarDto("BCR", 1237, gondolatTypeDto);

            freightCarService = new MongoFreightCarService(repositoryFake);
        }

        @Test
        public void should_getAllFreightCars_fromRepository()
        {
            //assign
            repositoryFake.setReturnedValuesList(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree,
                    gondolaOne, flatCarOne, gondolaTwo, gondolaThree
            ));
            //act
            final List<FreightCarDto> freightCarDtoList = freightCarService.allFreightCars();
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(7);
        }

        @Test
        public void should_getAllFreightCars_fromRepository_matchingAARType_XM()
        {
            //assign
            repositoryFake.setReturnedValuesList(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree
            ));

            //act
            final List<FreightCarDto> freightCarDtoList = freightCarService.allFreightCarsByAARType("XM");
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(3);
        }

        @Test
        public void should_getAllFreightCars_fromRepository_ThatCarryPaper()
        {
            //assign
            repositoryFake.setReturnedValuesList(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree, flatCarOne
            ));

            //act
            final List<FreightCarDto> freightCarDtoList = freightCarService.allFreightCarsThatCarry("Logs");
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(4);
        }

        @Test
        public void should_getAllFreightCars_fromRepository_withBCR_roadName()
        {
            //assign
            repositoryFake.setReturnedValuesList(Arrays.asList(
                    boxcarTwo, gondolaThree
            ));

            //act
            final List<FreightCarDto> freightCarDtoList = freightCarService.allFreightCarsByRoadName("BCR");
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(2);
        }

        @Test
        public void should_insertNewFreightCar_IntoRepository()
        {
            //assign
            final FreightCarDto boxcarToInsert = new FreightCarDto("BCR", 2342, boxcarTypeDto);

            //act
            final FreightCarDto freightCarDtoList = freightCarService.saveFreightCarToDatabase(boxcarToInsert);
            //assert
            assertThat(repositoryFake.savedEntity).isEqualTo(boxcarToInsert);
            assertThat(repositoryFake.entityInserted).isTrue();
            assertThat(repositoryFake.entitySaved).isFalse();
        }

        @Test
        public void should_updateFreightCar_InRepository_IfIDExists()
        {
            //assign
            repositoryFake.setReturnedValuesList(Collections.singletonList(boxcarOne));

            final FreightCarDto boxcarToInsert = new FreightCarDto(boxcarOneUUID, "STFU", 9999, boxcarTypeDto);

            //act
            freightCarService.saveFreightCarToDatabase(boxcarToInsert);
            //assert
            assertThat(repositoryFake.savedEntity.toString()).isEqualTo(boxcarToInsert.toString());
            assertThat(repositoryFake.entitySaved).isTrue();
            assertThat(repositoryFake.entityInserted).isFalse();
        }

    }
}