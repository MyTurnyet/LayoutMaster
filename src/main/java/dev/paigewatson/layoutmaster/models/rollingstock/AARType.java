package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "AARTypes")
public class AARType implements CarType
{
    public AARDesignation aarDesignation;
    public List<GoodsType> carriedGoodsList;

    @Id
    public String id = "";
    private UUID uuid;


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
        this.uuid = uuid;
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

    public AARDesignation displayName()
    {
        return aarDesignation;
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
