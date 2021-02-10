package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest()
@ExtendWith(SpringExtension.class)
@Tag("Mongo")
public class CarTypeRepositoryTests
{
    private final CarTypeRepository repository;
    private CarTypeDto boxcarTypeDto;
    private CarTypeDto gondolaCarTypeDto;
    private CarTypeDto flatcarTypeDto;

    public CarTypeRepositoryTests(@Autowired CarTypeRepository repository)
    {
        this.repository = repository;
    }

    @BeforeEach
    public void setUpRepository()
    {
        repository.deleteAll();
        boxcarTypeDto = new CarTypeDto("XM", Arrays.asList("Ingredients", "Logs"));
        flatcarTypeDto = new CarTypeDto("FC", Arrays.asList("Parts", "Machinery"));
        gondolaCarTypeDto = new CarTypeDto("GS", Arrays.asList("MetalScraps", "ScrapMetal", "Logs"));

        repository.insert(Arrays.asList(boxcarTypeDto, gondolaCarTypeDto, flatcarTypeDto));
    }

    @Test
    public void should_saveCarType()
    {
        final CarTypeDto coverGonCarTypeDto = new CarTypeDto("GA", Arrays.asList("MetalScraps", "ScrapMetal", "Aggregates"));
        final List<CarTypeDto> carTypeList = repository.findAll();
        assertThat(carTypeList.size()).isEqualTo(3);

        final CarTypeDto savesDto = repository.insert(coverGonCarTypeDto);

        System.out.print(savesDto);
        assertThat(savesDto.getId()).isNotEmpty();
        final List<CarTypeDto> carTypeList2 = repository.findAll();
        assertThat(carTypeList2.size()).isEqualTo(4);
    }

    @Test
    public void should_getCarsInRepository()
    {
        final List<CarTypeDto> carTypeList = repository.findAll();
        assertThat(carTypeList.size()).isEqualTo(3);
    }

    @Test
    public void should_getCarTypeByAAR_andReturnOneCarType()
    {
        final CarTypeDto carType = repository.findByAarTypeEquals("XM");
        assertThat(carType.aarType).isEqualTo(boxcarTypeDto.aarType);
        assertThat(carType.carriedGoods).isEqualTo(boxcarTypeDto.carriedGoods);
    }

    @Test
    public void should_getCarTypeByAAR_andReturnNothing()
    {
        final CarTypeDto carType = repository.findByAarTypeEquals("XB");
        assertThat(carType).isNull();
    }

    @Test
    public void should_getAllCarTypes_thatCarry_Logs()
    {
        final List<CarTypeDto> carriedGoodsContains = repository.findAllByCarriedGoodsContains("Logs");
        assertThat(carriedGoodsContains.size()).isEqualTo(2);
        assertThat(carriedGoodsContains.stream().allMatch(carTypeDto -> carTypeDto.carriedGoods.contains("Logs"))).isTrue();
    }
}