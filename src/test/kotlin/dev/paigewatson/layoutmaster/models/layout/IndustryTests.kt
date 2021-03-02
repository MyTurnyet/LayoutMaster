package dev.paigewatson.layoutmaster.models.layout

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.goods.ProducedGood
import dev.paigewatson.layoutmaster.models.goods.RequiredGood
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.*

class IndustryTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        private var cogFactory: Industry? = null

        @BeforeEach
        fun setUp() {
            createCogFactoryForTests()
        }

        @Test
        fun should_haveName_andListOfAcceptedGoods() {
            createCogFactoryForTests()

            //act
            //assert
            AssertionsForClassTypes.assertThat(cogFactory!!.needs(GoodsType.Parts)).isTrue
            AssertionsForClassTypes.assertThat(cogFactory!!.needs(GoodsType.SheetMetal)).isFalse
            AssertionsForClassTypes.assertThat(cogFactory!!.needs(GoodsType.MetalParts)).isFalse
            AssertionsForClassTypes.assertThat(cogFactory!!.produces(GoodsType.ScrapMetal)).isTrue
            AssertionsForClassTypes.assertThat(cogFactory!!.produces(GoodsType.Oil)).isFalse
            AssertionsForClassTypes.assertThat(cogFactory!!.name()).isEqualTo("Cog Factory")
        }

        @Test
        fun should_representItselfAsString() {
            AssertionsForClassTypes.assertThat(cogFactory.toString()).isEqualTo(
                "Industry{" +
                        "id='null', industryName='Cog Factory', " +
                        "acceptedGoodsList=[" +
                        "RequiredGood{neededGoodsType=Chemicals, isAssigned=false}, " +
                        "RequiredGood{neededGoodsType=Parts, isAssigned=false}, " +
                        "RequiredGood{neededGoodsType=SheetMetal, isAssigned=true}], " +
                        "producedGoods=[" +
                        "ProducedGood{goodsType=MetalParts, isAssigned=false}, " +
                        "ProducedGood{goodsType=ScrapMetal, isAssigned=false}], " +
                        "industryTracks=[" +
                        "IndustryTrack{trackName='Lone Siding', maximumNumberOfCars=2, carsAtIndustry=[]}" +
                        "]}"
            )
        }

        private fun createCogFactoryForTests() {
            //assign
            val acceptedGoodsList = ArrayList<RequiredGood>()
            acceptedGoodsList.add(RequiredGood(GoodsType.Chemicals))
            acceptedGoodsList.add(RequiredGood(GoodsType.Parts))
            acceptedGoodsList.add(RequiredGood(GoodsType.SheetMetal, true))
            val returnedGoodsList = ArrayList<ProducedGood>()
            returnedGoodsList.add(ProducedGood(GoodsType.MetalParts))
            returnedGoodsList.add(ProducedGood(GoodsType.ScrapMetal))
            val siding = IndustryTrack("Lone Siding", 2)
            val industryTracks = ArrayList<IndustryTrack>()
            industryTracks.add(siding)
            cogFactory = Industry("Cog Factory", acceptedGoodsList, returnedGoodsList, industryTracks)
        }
    }
}
