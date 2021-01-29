package dev.paigewatson.layoutmaster.models.layout;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.goods.ProducedGood;
import dev.paigewatson.layoutmaster.models.goods.RequiredGood;
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Chemicals;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.MetalParts;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Oil;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Paper;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.ScrapMetal;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.SheetMetal;
import static dev.paigewatson.layoutmaster.models.rollingstock.CarType.Boxcar;
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
        public void should_returnNumberOfEmptyLocations_as1()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);
            carriedGoodsList.add(Paper);
            final FreightCar freightCar = new FreightCar("PNWR 1234", Boxcar, carriedGoodsList);
            freightCar.load(Paper);

            //act
            final int emptyLocations = cogFactory.emptyLocations();
            //assert
            assertThat(emptyLocations).isEqualTo(1);
        }

        @Test
        public void should_setAddCarToIndustry_andReturnNumberOfEmptyLocations_as0()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);
            carriedGoodsList.add(Paper);
            final FreightCar freightCar = new FreightCar("PNWR 1234", Boxcar, carriedGoodsList);
            freightCar.load(Paper);

            //act
            cogFactory.setOutCar(freightCar);
            //assert
            assertThat(cogFactory.emptyLocations()).isEqualTo(0);
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

            cogFactory = new Industry("Cog Factory", acceptedGoodsList, returnedGoodsList);
        }
    }
}