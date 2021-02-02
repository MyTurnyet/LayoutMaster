package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest()
@ExtendWith(SpringExtension.class)

@Tag("Mongo")
public class CarTypeRepositoryTests
{
    private final MongoCarTypeRepository repository;

    public CarTypeRepositoryTests(@Autowired MongoCarTypeRepository repository)
    {
        this.repository = repository;
    }

    @BeforeEach
    public void setUpRepository()
    {
        repository.deleteAll();
        //assign
        final ArrayList<String> carriedGoodsList = new ArrayList<>();
        carriedGoodsList.add("Ingredients");

        final CarTypeDto boxcarTypeDto = new CarTypeDto("", "XM", carriedGoodsList);

        //act
        repository.save(boxcarTypeDto);
    }

    @Test
    public void should_saveCarType()
    {
        //assert
        final List<CarTypeDto> carTypeList = repository.findAll();
        assertThat(carTypeList.size()).isEqualTo(1);
    }
}