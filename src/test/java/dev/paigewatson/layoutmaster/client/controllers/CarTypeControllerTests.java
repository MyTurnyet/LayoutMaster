package dev.paigewatson.layoutmaster.client.controllers;

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
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.FC;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarTypeControllerTests
{

    @Nested
    @Tag("Unit")
    class UnitTests
    {


        private CarTypeRepositoryFake repositoryFake;
        private CarTypeController carTypeController;

        @BeforeEach
        public void setUp()
        {
            repositoryFake = new CarTypeRepositoryFake();
            carTypeController = new CarTypeController(repositoryFake);
        }

        @Test
        public void should_returnAllAARDesignations()
        {
            //assign
            //act
            final List<AARDesignation> allCarTypes = carTypeController.getAARDesignations();
            //assert
            assertThat(allCarTypes.size()).isEqualTo(23);
        }

        @Test
        public void should_returnAllCarTypes()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarType boxCarType = new CarType(XM, carriedGoodsList);
            ArrayList<CarType> returnedCarTypes = new ArrayList<>();
            returnedCarTypes.add(boxCarType);
            repositoryFake.setReturnedValuesList(returnedCarTypes);

            //act
            final List<CarType> allCarTypes = carTypeController.getAllCarTypes();
            //assert
            assertThat(allCarTypes).isEqualTo(returnedCarTypes);
        }

        @Test
        public void should_saveCarTypeToRepository()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            //act
            carTypeController.addNewCarType(FC, carriedGoodsList);

            //assert
            assertThat(repositoryFake.savedEntity().isOfType(FC)).isTrue();
            assertThat(repositoryFake.savedEntity().canCarry(Ingredients)).isTrue();
        }
    }
}