package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.helpers.EntityCreator;
import dev.paigewatson.layoutmaster.helpers.RollingStockDALFake;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        private UUID boxcarOneUUID;

        @BeforeEach
        public void setupTests()
        {
            rollingStockDALFake = new RollingStockDALFake();

            boxcarOneUUID = UUID.randomUUID();
            boxcarOne = EntityCreator.boxcar(boxcarOneUUID, "PNWR", 2341);
            boxcarTwo = EntityCreator.boxcar(UUID.randomUUID(), "BCR", 2342);
            boxcarThree = EntityCreator.boxcar(UUID.randomUUID(), "PNWR", 2335);

            flatCarOne = EntityCreator.flatcar("ATSF", 1232);
            gondolaOne = EntityCreator.gondola("BNSF", 1234);
            gondolaTwo = EntityCreator.gondola("PNWR", 1235);
            gondolaThree = EntityCreator.gondola("BCR", 1237);

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
            //assign

            //act
            final RollingStock savedRollingStock = freightCarService.saveFreightCarToDatabase(boxcarThree);
            //assert
            assertThat(rollingStockDALFake.savedEntity).isEqualTo(boxcarThree);
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