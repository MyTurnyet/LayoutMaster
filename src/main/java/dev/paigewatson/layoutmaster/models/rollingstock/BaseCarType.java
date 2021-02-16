package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "AARTypes")
public abstract class BaseCarType implements CarType
{
    public AARDesignation aarDesignation;
    public List<GoodsType> carriedGoodsList;

    @Id
    public String id = "";

    public BaseCarType()
    {
    }

    public BaseCarType(UUID uuid, AARDesignation aarDesignation, List<GoodsType> carriedGoodsList)
    {
        this.id = uuid.toString();
        this.aarDesignation = aarDesignation;
        this.carriedGoodsList = carriedGoodsList;
    }


    public boolean isOfType(AARDesignation expectedTypeDesignation)
    {
        return expectedTypeDesignation == aarDesignation;
    }


}
