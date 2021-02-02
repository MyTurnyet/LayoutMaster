package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DtoTests
{
    @Nested
    @Tag("Unit")
    class CarTypeDtoTests
    {
        @Test
        public void should_returnItselfAsString()
        {
            //assign
            final CarTypeDto carTypeDto = new CarTypeDto("FOOO!", "XM", Arrays.asList("Stuff"));

            //act
            final String carTypeAsString = carTypeDto.toString();
            //assert
            assertThat(carTypeAsString).isEqualTo("CarTypeDto{id='FOOO!', aarType='XM', carriedGoods=[Stuff]}");
        }

        @Test
        public void should_returnCarType()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarType boxCarType = new CarType("FOO!", XM, carriedGoodsList);

            final Dto<CarType> carTypeDto = new CarTypeDto("FOO!", "XM", Arrays.asList("Ingredients"));
            //act
            CarType returnedCarType = carTypeDto.getEntity();
            //assert
            assertThat(returnedCarType.toString()).isEqualTo(boxCarType.toString());
        }
    }
}