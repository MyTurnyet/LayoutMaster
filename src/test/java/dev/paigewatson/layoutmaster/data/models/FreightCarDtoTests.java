package dev.paigewatson.layoutmaster.data.models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FreightCarDtoTests
{
    @Nested
    @Tag("Unit")
    class NonNullFreightCarDtoTests
    {
        @Test
        public void should_createFreightCarDto()
        {
            //assign
            final List<String> carriedGoods = Arrays.asList("Ingredients", "Paper", "Parts");
            final String expectedAARType = "XM";
            final UUID uuidCarType = UUID.randomUUID();
            final AARTypeDto boxcarTypeDto = new AARTypeDto(uuidCarType, expectedAARType, carriedGoods);

            final UUID uuidFreight = UUID.randomUUID();
            final FreightCarDto freightCarDto = new FreightCarDto(uuidFreight, "PNWR", 1234, boxcarTypeDto);

            //assert
            assertThat(freightCarDto.isNull()).isFalse();
            assertThat(freightCarDto.toString()).isEqualTo("FreightCarDto{id='" +
                    uuidFreight.toString() +
                    "', roadName='PNWR', roadNumber=1234, carTypeDto=CarTypeDto{id='" +
                    uuidCarType.toString() +
                    "', aarType='XM', carriedGoods=[Ingredients, Paper, Parts]}}");
        }
    }

    @Nested
    @Tag("Unit")
    class NullFreightCarDtoTests
    {
//        @Test
//        public void should_createNullFreightCarDto()
//        {
//            //assign
//            final NullFreightCarDto nullFreightCarDto = new NullFreightCarDto();
//
//            //assert
//            assertThat(nullFreightCarDto.isNull()).isTrue();
//            assertThat(nullFreightCarDto.roadName).isEqualTo("");
//            assertThat(nullFreightCarDto.roadNumber).isEqualTo(0);
//            assertThat(nullFreightCarDto.carTypeDto.toString()).isEqualTo(new NullCarTypeDto().toString());
//            assertThat(nullFreightCarDto.toString()).isEqualTo("FreightCarDto{id='null', roadName='', roadNumber=0, carTypeDto=CarTypeDto{id='', aarType='', carriedGoods=[]}}");
//        }
    }

}