package dev.paigewatson.layoutmaster.models.layout;

import dev.paigewatson.layoutmaster.models.goods.ProducedGood;
import dev.paigewatson.layoutmaster.models.goods.RequiredGood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Chemicals;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.MetalParts;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Oil;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.ScrapMetal;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.SheetMetal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IndustryTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {

        private Industry cogFactory;

        @BeforeEach
        public void setUp()
        {
            createCogFactoryForTests();
        }

        @Test
        public void should_haveName_andListOfAcceptedGoods()
        {
            createCogFactoryForTests();

            //act
            //assert
            assertThat(cogFactory.needs(Parts)).isTrue();
            assertThat(cogFactory.needs(SheetMetal)).isFalse();
            assertThat(cogFactory.needs(MetalParts)).isFalse();
            assertThat(cogFactory.produces(ScrapMetal)).isTrue();
            assertThat(cogFactory.produces(Oil)).isFalse();
            assertThat(cogFactory.name()).isEqualTo("Cog Factory");
        }


        @Test
        public void should_representItselfAsString()
        {
            assertThat(cogFactory.toString()).isEqualTo(
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
                            "]}");
        }

        private void createCogFactoryForTests()
        {
            //assign
            final ArrayList<RequiredGood> acceptedGoodsList = new ArrayList<>();
            acceptedGoodsList.add(new RequiredGood(Chemicals));
            acceptedGoodsList.add(new RequiredGood(Parts));
            acceptedGoodsList.add(new RequiredGood(SheetMetal, true));

            final ArrayList<ProducedGood> returnedGoodsList = new ArrayList<>();
            returnedGoodsList.add(new ProducedGood(MetalParts));
            returnedGoodsList.add(new ProducedGood(ScrapMetal));
            final IndustryTrack siding = new IndustryTrack("Lone Siding", 2);

            ArrayList<IndustryTrack> industryTracks = new ArrayList<>();
            industryTracks.add(siding);

            cogFactory = new Industry("Cog Factory", acceptedGoodsList, returnedGoodsList, industryTracks);
        }

    }
}