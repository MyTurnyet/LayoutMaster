package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.data.CarTypeDAL;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "AARTypes")
public class AARType extends BaseCarType
{
    public AARType()
    {
    }

    public AARType(AARDesignation aarDesignation, List<GoodsType> carriedGoodsList)
    {
        super(UUID.randomUUID(), aarDesignation, carriedGoodsList);
    }

    public AARType(UUID uuid, AARDesignation aarDesignation, List<GoodsType> carriedGoodsList)
    {
        super(uuid, aarDesignation, carriedGoodsList);
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
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof AARType)) return false;
        AARType aarType = (AARType) o;
        return aarDesignation == aarType.aarDesignation && carriedGoodsList.equals(aarType.carriedGoodsList) && id.equals(aarType.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(aarDesignation, carriedGoodsList, id);
    }

    @Override
    public boolean isNull()
    {
        return false;
    }
}
