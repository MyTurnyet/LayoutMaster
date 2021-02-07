package dev.paigewatson.layoutmaster.data.models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GoodsTypeDtoTests
{
    @Nested
    @Tag("Unit")
    class NullGoodsTypeTests
    {
        @Test
        public void should_returnNullGoodsTypeDto()
        {
            //assign
            final NullGoodsTypeDto nullGoodsTypeDto = new NullGoodsTypeDto();

            //act
            final boolean isNull = nullGoodsTypeDto.isNull();
            //assert
            assertThat(isNull).isTrue();
            assertThat(nullGoodsTypeDto.toString()).isEqualTo("GoodsTypeDto{id='', category='', goodsName='', state=''}");
        }
    }

    @Nested
    @Tag("Unit")
    class NonNullGoodsTypeTests
    {
        @Test
        public void should_returnGoodsTypeDto()
        {
            //assign
            final String category = "Grain";
            final String goodsName = "Flour";
            final String state = "Loose";
            final UUID uuid = UUID.randomUUID();

            final GoodsTypeDto goodsTypeDto = new GoodsTypeDto(uuid, category, goodsName, state);

            //act
            final boolean isNull = goodsTypeDto.isNull();
            //assert
            assertThat(isNull).isFalse();
            assertThat(goodsTypeDto.toString()).isEqualTo("GoodsTypeDto{id='" +
                    uuid.toString() +
                    "', category='Grain', goodsName='Flour', state='Loose'}");
        }
    }
}