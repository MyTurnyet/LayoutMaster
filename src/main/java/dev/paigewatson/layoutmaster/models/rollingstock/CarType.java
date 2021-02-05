package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.CarTypeRepository;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CarType
{
    private final AARDesignation aarDesignation;
    private final List<GoodsType> carriedGoodsList;
    private String id = "";

    public CarType(AARDesignation aarDesignation, List<GoodsType> carriedGoodsList)
    {
        this("", aarDesignation, carriedGoodsList);
    }

    public CarType(String id, AARDesignation aarDesignation, List<GoodsType> carriedGoodsList)
    {
        this.id = id;
        this.aarDesignation = aarDesignation;
        this.carriedGoodsList = carriedGoodsList;
    }


    public boolean isOfType(AARDesignation expectedTypeDesignation)
    {
        return expectedTypeDesignation == aarDesignation;
    }

    public boolean canCarry(GoodsType expectedGoodsType)
    {
        return carriedGoodsList.contains(expectedGoodsType);
    }

    public String displayName()
    {
        return aarDesignation.name();
    }

    public CarTypeDto getDto()
    {
        final CarTypeDto carTypeDto = new CarTypeDto();
//        carTypeDto.id = this.id;
        carTypeDto.aarType = this.aarDesignation.name();
        carTypeDto.carriedGoods = carriedGoodsList.stream().map(Enum::name).collect(Collectors.toCollection(ArrayList::new));
        return carTypeDto;
    }

    public void saveToRepository(CarTypeRepository carTypeRepository)
    {
        carTypeRepository.save(this.getDto());
    }

    @Override
    public String toString()
    {
        return "CarType{" +
                "id='" + id + '\'' +
                ", aarDesignation=" + aarDesignation +
                ", carriedGoodsList=" + carriedGoodsList +
                '}';
    }
}
