package dev.paigewatson.layoutmaster.models.data;

import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("Unit")
public class FreightCarDtoTests
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
                "', roadName='PNWR', roadNumber=1234, carTypeDto=AARTypeDto{id='" +
                uuidCarType.toString() +
                "', aarType='XM', carriedGoods=[Ingredients, Paper, Parts]}}");
    }

    @Test
    public void should_getEntity()
    {
        //assign
        final List<String> carriedGoods = Arrays.asList("Ingredients", "Paper", "Parts");
        final String expectedAARType = "XM";
        final UUID uuidCarType = UUID.randomUUID();
        final AARTypeDto boxcarTypeDto = new AARTypeDto(uuidCarType, expectedAARType, carriedGoods);

        final UUID uuidFreight = UUID.randomUUID();
        final FreightCarDto freightCarDto = new FreightCarDto(uuidFreight, "PNWR", 4324, boxcarTypeDto);

        //act
        final FreightCar freightCar = freightCarDto.getEntity();
        //assert
        assertThat(freightCar.toString()).isEqualTo("FreightCar{id='" +
                uuidFreight.toString() +
                "', roadName='PNWR', roadNumber=4324, " +
                "carType=AARType{id='" +
                uuidCarType.toString() +
                "', aarDesignation=XM, carriedGoodsList=[Ingredients, Paper, Parts]}, currentlyCarriedGoods=EMPTY}");

    }
}