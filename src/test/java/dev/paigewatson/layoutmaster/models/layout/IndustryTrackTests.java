package dev.paigewatson.layoutmaster.models.layout;

import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCarTests;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IndustryTrackTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_haveName_AndHasOpenSlots()
        {
            //assign
            final IndustryTrack industryTrack = new IndustryTrack("factory siding", 1);

            //act
            //assert
            assertThat(industryTrack.hasOpenSlots()).isTrue();
            assertThat(industryTrack.name()).isEqualTo("factory siding");
        }

        @Test
        public void should_HaveNoOpenSlots()
        {
            //assign
            final IndustryTrack industryTrack = new IndustryTrack("factory siding", 1);
            final FreightCar freightCar = FreightCarTests.createTestFreightCar();
            //act
            industryTrack.setOutCar(freightCar);
            //assert
            assertThat(industryTrack.hasOpenSlots()).isFalse();
        }

        @Test
        public void should_representItselfAsStringForSaving()
        {
            //assign
            final IndustryTrack industryTrack = new IndustryTrack("Siding 1", 2);

            //act
            final String toString = industryTrack.toString();
            //assert
            assertThat(toString).isEqualTo("IndustryTrack{trackName='Siding 1', maximumNumberOfCars=2, carsAtIndustry=[]}");

        }
    }
}