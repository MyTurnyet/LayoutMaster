package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.data.AARTypeDto;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest()
@ExtendWith(SpringExtension.class)

@Tag("Mongo")
public class FreightCarRepositoryTests
{
    private final FreightCarRepository repository;
    private AARTypeDto gondolatTypeDto;

    public FreightCarRepositoryTests(@Autowired FreightCarRepository repository)
    {
        this.repository = repository;
    }

    @BeforeEach
    public void setupRepository()
    {
        repository.deleteAll();
        final AARTypeDto boxcarTypeDto = new AARTypeDto("XM", Arrays.asList("Ingredients", "Paper", "Logs"));
        final AARTypeDto flatcarTypeDto = new AARTypeDto("FC", Arrays.asList("Parts", "Logs"));
        final FreightCarDto freightCarDto1 = new FreightCarDto("PNWR", 2145, boxcarTypeDto);
        final FreightCarDto freightCarDto3 = new FreightCarDto("BCR", 2342, boxcarTypeDto);
        final FreightCarDto freightCarDto4 = new FreightCarDto("PNWR", 2335, boxcarTypeDto);

        gondolatTypeDto = new AARTypeDto("GS", Arrays.asList("MetalScraps", "ScrapMetal", "Aggregates"));
        final FreightCarDto freightCarDto5 = new FreightCarDto("BNSF", 1234, gondolatTypeDto);
        final FreightCarDto freightCarDto6 = new FreightCarDto("ATSF", 1232, flatcarTypeDto);
        final FreightCarDto freightCarDto7 = new FreightCarDto("PNWR", 1235, gondolatTypeDto);
        final FreightCarDto freightCarDto8 = new FreightCarDto("BCR", 1237, gondolatTypeDto);
        repository.saveAll(
                Arrays.asList(freightCarDto1, freightCarDto3, freightCarDto4,
                        freightCarDto5, freightCarDto6, freightCarDto7, freightCarDto8)
        );
    }

    @Test
    public void should_saveFreightCar()
    {
        //assign
        final FreightCarDto freightCarDto = new FreightCarDto("BNSF", 1212, gondolatTypeDto);

        //act
        repository.save(freightCarDto);
        //assert
        final Optional<FreightCarDto> savedDto = repository.findById(freightCarDto.id);
        assertThat(savedDto.isPresent()).isTrue();
        assertThat(freightCarDto.toString()).isEqualTo(savedDto.get().toString());
    }

    @Test
    public void should_getAllFreightCarsInRepository()
    {
        final List<FreightCarDto> freightCarDtos = repository.findAll();
        assertThat(freightCarDtos.size()).isEqualTo(7);
    }

    @Test
    public void should_getThreeBoxcars_ByAARType()
    {
        final List<FreightCarDto> allBoxcarsList = repository.findAllByCarTypeDto_AarType("XM");
        assertThat(allBoxcarsList.size()).isEqualTo(3);
        assertThat(allBoxcarsList.stream().allMatch(freightCarDto -> freightCarDto.carTypeDto.aarType.equals("XM"))).isTrue();
    }

    @Test
    public void should_getTwoFreightCars_ByRoadName()
    {
        final List<FreightCarDto> allBCRFreightList = repository.findAllByRoadName("BCR");
        assertThat(allBCRFreightList.size()).isEqualTo(2);
        assertThat(allBCRFreightList.stream().allMatch(freightCarDto -> freightCarDto.roadName.equals("BCR"))).isTrue();
    }

    @Test
    public void should_getFourCars_ByGoodsCarried_Logs()
    {
        final List<FreightCarDto> allBCRFreightList = repository.findAllByCarTypeDtoCarriedGoodsContains("Logs");
        assertThat(allBCRFreightList.size()).isEqualTo(4);
        assertThat(allBCRFreightList.stream().allMatch(freightCarDto -> freightCarDto.carTypeDto.carriedGoods.contains("Logs"))).isTrue();
    }
}