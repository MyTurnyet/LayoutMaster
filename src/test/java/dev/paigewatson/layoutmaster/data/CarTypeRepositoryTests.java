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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest()
@ExtendWith(SpringExtension.class)

@Tag("Mongo")
public class CarTypeRepositoryTests
{
    private final CarTypeRepository repository;
    private CarTypeDto boxcarTypeDto;

    public CarTypeRepositoryTests(@Autowired CarTypeRepository repository)
    {
        this.repository = repository;
    }

    @BeforeEach
    public void setUpRepository()
    {
        repository.deleteAll();
        //assign
        boxcarTypeDto = new CarTypeDto("XM", Collections.singletonList("Ingredients"));

        //act
        repository.insert(boxcarTypeDto);
    }

    @Test
    public void should_saveCarType()
    {
        //assert
        final CarTypeDto carTypeDto = new CarTypeDto("GS", Arrays.asList("MetalScraps", "ScrapMetal", "Aggregates"));
        final List<CarTypeDto> carTypeList = repository.findAll();
        assertThat(carTypeList.size()).isEqualTo(1);

        final CarTypeDto savesDto = repository.insert(carTypeDto);

        System.out.print(savesDto);
        assertThat(savesDto.getId()).isNotEmpty();
        final List<CarTypeDto> carTypeList2 = repository.findAll();
        assertThat(carTypeList2.size()).isEqualTo(2);
    }

    @Test
    public void should_getCarsInRepository()
    {
        //assign
        final List<CarTypeDto> carTypeList = repository.findAll();
        assertThat(carTypeList.size()).isEqualTo(1);
    }

    @Test
    public void should_getCarTypeByAAR_andReturnOneCarType()
    {
        //assign
        final CarTypeDto carType = repository.findByAarTypeEquals("XM");
        assertThat(carType.aarType).isEqualTo(boxcarTypeDto.aarType);
        assertThat(carType.carriedGoods).isEqualTo(boxcarTypeDto.carriedGoods);
    }

    @Test
    public void should_getCarTypeByAAR_andReturnNothing()
    {
        //assign
        final CarTypeDto carType = repository.findByAarTypeEquals("XB");
        assertThat(carType).isNull();
    }
}