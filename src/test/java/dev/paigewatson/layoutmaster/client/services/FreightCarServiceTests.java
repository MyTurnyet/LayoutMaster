package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.helpers.RollingStockDALFake;
import dev.paigewatson.layoutmaster.helpers.TestFreightCarCreator;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FreightCarServiceTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        private FreightCarService freightCarService;
        private RollingStockDALFake rollingStockDALFake;

        private RollingStock boxcarOne;
        private RollingStock boxcarTwo;
        private RollingStock boxcarThree;
        private RollingStock gondolaOne;
        private RollingStock gondolaTwo;
        private RollingStock gondolaThree;
        private RollingStock flatCarOne;

        @BeforeEach
        public void setupTests()
        {
            rollingStockDALFake = new RollingStockDALFake();

            boxcarOne = TestFreightCarCreator.boxcar("PNWR", 2341);
            boxcarTwo = TestFreightCarCreator.boxcar("BCR", 2342);
            boxcarThree = TestFreightCarCreator.boxcar("PNWR", 2335);

            flatCarOne = TestFreightCarCreator.flatcar("ATSF", 1232);
            gondolaOne = TestFreightCarCreator.gondola("BNSF", 1234);
            gondolaTwo = TestFreightCarCreator.gondola("PNWR", 1235);
            gondolaThree = TestFreightCarCreator.gondola("BCR", 1237);

            freightCarService = new MongoFreightCarService(rollingStockDALFake);
        }

        @Test
        public void should_getAllFreightCars_fromRepository()
        {
            //assign
            rollingStockDALFake.setReturnedValuesList(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree,
                    gondolaOne, flatCarOne, gondolaTwo, gondolaThree
            ));
            //act
            final List<RollingStock> rollingStockList = freightCarService.allFreightCars();
            //assert
            assertThat(rollingStockList.size()).isEqualTo(7);
        }

        @Test
        public void should_getAllFreightCars_fromRepository_matchingAARType_XM()
        {
            //assign
            rollingStockDALFake.setReturnedValuesList(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree
            ));

            //act
            final List<RollingStock> rollingStockList = freightCarService.allFreightCarsByAARType(AARDesignation.XM);
            //assert
            assertThat(rollingStockList.size()).isEqualTo(3);
        }

        @Test
        public void should_getAllFreightCars_fromRepository_withBCR_roadName()
        {
            //assign
            rollingStockDALFake.setReturnedValuesList(Arrays.asList(
                    boxcarTwo, gondolaThree
            ));

            //act
            final List<RollingStock> rollingStockList = freightCarService.allFreightCarsByRoadName("BCR");
            //assert
            assertThat(rollingStockList.size()).isEqualTo(2);
        }

        @Test
        public void should_insertNewFreightCar_IntoRepository()
        {
            //act
            final RollingStock savedRollingStock = freightCarService.saveFreightCarToDatabase(boxcarThree);
            //assert
            assertThat(rollingStockDALFake.savedEntity).isEqualTo(boxcarThree);
            assertThat(savedRollingStock).isEqualTo(boxcarThree);
        }

        @Test
        public void should_deleteFreightCar_fromDatabase()
        {
            //assign
            freightCarService.delete(boxcarOne);

            //assert
            assertThat(rollingStockDALFake.deletedEntity).isEqualTo(boxcarOne);
        }
    }
}