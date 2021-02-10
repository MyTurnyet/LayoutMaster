package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.data.CarTypeRepository;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Document(collection = "AARTypes")
public class AARType implements CarType
{
    private AARDesignation aarDesignation;
    private List<GoodsType> carriedGoodsList;
    private String id = "";

    public AARType()
    {
    }

    public AARType(AARDesignation aarDesignation, List<GoodsType> carriedGoodsList)
    {
        this(UUID.randomUUID(), aarDesignation, carriedGoodsList);
    }

    public AARType(UUID uuid, AARDesignation aarDesignation, List<GoodsType> carriedGoodsList)
    {
        this.id = uuid.toString();
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

    @Override
    public CarType saveToDatabase(CarTypeDAL carTypeDAL)
    {
        final CarType byAarType = carTypeDAL.findByAarType(this.aarDesignation);
        if (!byAarType.isNull())
        {
            carTypeDAL.delete(byAarType);
        }
        return carTypeDAL.insertCarType(this);
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
        return "AARType{" +
                "id='" + id + "'" +
                ", aarDesignation=" + aarDesignation +
                ", carriedGoodsList=" + carriedGoodsList +
                "}";
    }

    @Override
    public boolean isNull()
    {
        return false;
    }
}
