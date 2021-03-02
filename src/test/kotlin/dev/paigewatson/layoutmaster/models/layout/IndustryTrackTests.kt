package dev.paigewatson.layoutmaster.models.layout

import dev.paigewatson.layoutmaster.models.rollingstock.FreightCarTests
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class IndustryTrackTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        @Test
        fun should_haveName_AndHasOpenSlots() {
            //assign
            val industryTrack = IndustryTrack("factory siding", 1)

            //act
            //assert
            AssertionsForClassTypes.assertThat(industryTrack.hasOpenSlots()).isTrue
            AssertionsForClassTypes.assertThat(industryTrack.name()).isEqualTo("factory siding")
        }

        @Test
        fun should_HaveNoOpenSlots() {
            //assign
            val industryTrack = IndustryTrack("factory siding", 1)
            val freightCar = FreightCarTests.createTestFreightCar()
            //act
            industryTrack.setOutCar(freightCar)
            //assert
            AssertionsForClassTypes.assertThat(industryTrack.hasOpenSlots()).isFalse
        }

        @Test
        fun should_representItselfAsStringForSaving() {
            //assign
            val industryTrack = IndustryTrack("Siding 1", 2)

            //act
            val toString = industryTrack.toString()
            //assert
            AssertionsForClassTypes.assertThat(toString)
                .isEqualTo("IndustryTrack{trackName='Siding 1', maximumNumberOfCars=2, carsAtIndustry=[]}")
        }
    }
}
