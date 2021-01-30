package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GoodsControllerTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_returnAllGoods()
        {
            //assign
            final GoodsController goodsController = new GoodsController();

            //act
            final List<GoodsType> goodsTypes = goodsController.getAllGoods();
            //assert
            assertThat(goodsTypes.size()).isEqualTo(14);
        }
    }
}