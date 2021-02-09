package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.helpers.CarTypeRepositoryFake;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.ScrapMetal;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.FA;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AARTypeTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_evaluateTo_NullCarType()
        {
            //assign
            CarType nullCarType = new NullCarType();
            //act
            //assert
            final boolean ofType = nullCarType.isOfType(XM);
            final boolean canCarry = nullCarType.canCarry(Ingredients);
            final String displayName = nullCarType.displayName();
            final boolean isNull = nullCarType.isNull();

            assertThat(isNull).isTrue();
            assertThat(displayName).isEqualTo("");
            assertThat(canCarry).isFalse();
            assertThat(ofType).isFalse();

        }

        @Test
        public void should_haveTypeName_andGoodsCarried()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarType boxCarType = new AARType(XM, carriedGoodsList);

            //act
            //assert
            assertThat(boxCarType.canCarry(Ingredients)).isTrue();
            assertThat(boxCarType.canCarry(ScrapMetal)).isFalse();
            assertThat(boxCarType.isOfType(XM)).isTrue();
            assertThat(boxCarType.isOfType(FA)).isFalse();
            assertThat(boxCarType.displayName()).isEqualTo("XM");
        }


//        @Test
//        public void should_returnDtoOfItself()
//        {
//            //assign
//            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
//            carriedGoodsList.add(Ingredients);
//
//            final CarType boxCarType = new AARType(XM, carriedGoodsList);
//
//            //act
//            CarTypeDto boxcarDto = boxCarType.getDto();
//            //assert
//            assertThat(boxcarDto.aarType).isEqualTo("XM");
//            final String[] strings = {"Ingredients"};
//            assertThat(boxcarDto.carriedGoods).isEqualTo(Arrays.asList(strings));
//        }

        @Test
        public void should_saveItselfToRepository()
        {
            //assign
            final CarTypeRepositoryFake carTypeRepositoryFake = new CarTypeRepositoryFake();
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);
            final CarTypeDto carTypeDto = new CarTypeDto();
            carTypeDto.aarType = "XM";

            final List<String> expectedGoodsList = Arrays.asList("Ingredients");
            carTypeDto.carriedGoods = expectedGoodsList;

            final AARType carType = new AARType(XM, carriedGoodsList);
            //act
            carType.saveToRepository(carTypeRepositoryFake);

            //assert
            assertThat(carTypeRepositoryFake.savedEntity).isInstanceOf(CarTypeDto.class);
            assertThat(carTypeRepositoryFake.savedEntity.aarType).isEqualTo("XM");
            assertThat(carTypeRepositoryFake.savedEntity.getId()).isEqualTo("");
            assertThat(carTypeRepositoryFake.savedEntity.carriedGoods).isEqualTo(expectedGoodsList);
        }
    }
}