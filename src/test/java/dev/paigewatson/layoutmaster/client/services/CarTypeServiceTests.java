package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.helpers.CarTypeRepositoryFake;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarTypeServiceTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {

        private CarTypeRepositoryFake repositoryFake;

        @BeforeEach
        public void beforeEachTestRuns()
        {
            repositoryFake = new CarTypeRepositoryFake();
        }

        @Test
        public void should_returnAllAARDesignations()
        {
            //assign
            final CarTypeService service = new MongoCarTypeService(repositoryFake);

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

            final CarType boxCarType = new CarType(XM, carriedGoodsList);
            ArrayList<CarType> returnedCarTypes = new ArrayList<>();
            returnedCarTypes.add(boxCarType);
            repositoryFake.setReturnedValuesList(returnedCarTypes);

            final CarTypeService service = new MongoCarTypeService(repositoryFake);

            //act
            List<CarType> allCarTypesList = service.allCarTypes();
            //assert
            assertThat(allCarTypesList).isEqualTo(returnedCarTypes);
        }
    }
}