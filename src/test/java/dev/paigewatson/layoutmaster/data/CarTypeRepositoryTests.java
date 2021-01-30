package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.rollingstock.CarTypeDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest()
@ExtendWith(SpringExtension.class)

@Tag("Mongo")
public class CarTypeRepositoryTests
{
    private final CarTypeRepository repository;

    public CarTypeRepositoryTests(@Autowired CarTypeRepository repository)
    {
        this.repository = repository;
    }

    @BeforeEach
    public void setUpRepository()
    {
//        repository.deleteAll();
        //assign
        final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
        carriedGoodsList.add(Ingredients);

        final CarType carType = new CarType(XM, carriedGoodsList);

        //act
//        repository.save(carType);
    }

    @Test
    public void should_saveCarType()
    {
        //assert
        final List<CarType> carTypeList = repository.findAll();
        assertThat(carTypeList.size()).isEqualTo(1);
    }
}