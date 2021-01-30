package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.ScrapMetal;
import static dev.paigewatson.layoutmaster.models.rollingstock.CarTypeDesignation.FA;
import static dev.paigewatson.layoutmaster.models.rollingstock.CarTypeDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarTypeTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_haveTypeName_andGoodsCarried()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarType boxCarType = new CarType(XM, carriedGoodsList);

            //act
            //assert
            assertThat(boxCarType.canCarry(Ingredients)).isTrue();
            assertThat(boxCarType.canCarry(ScrapMetal)).isFalse();
            assertThat(boxCarType.isOfType(XM)).isTrue();
            assertThat(boxCarType.isOfType(FA)).isFalse();
            assertThat(boxCarType.displayName()).isEqualTo("XM");
        }

        @Test
        public void should_representItselfAsString()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarType boxCarType = new CarType(XM, carriedGoodsList);

            //act
            final String toString = boxCarType.toString();
            //assert
            assertThat(toString).isEqualTo("CarType{id='', carTypeDesignation=XM, carriedGoodsList=[Ingredients]}");
        }
    }
}