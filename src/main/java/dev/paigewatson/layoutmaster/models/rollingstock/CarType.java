package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "CarTypes")
public class CarType
{
    private final AARDesignation AARDesignation;
    private final ArrayList<GoodsType> carriedGoodsList;
    @Id
    private final String id = "";

    public CarType(AARDesignation AARDesignation, ArrayList<GoodsType> carriedGoodsList)
    {
        this.AARDesignation = AARDesignation;
        this.carriedGoodsList = carriedGoodsList;
    }

    public boolean isOfType(AARDesignation expectedTypeDesignation)
    {
        return expectedTypeDesignation == AARDesignation;
    }

    public boolean canCarry(GoodsType expectedGoodsType)
    {
        return carriedGoodsList.contains(expectedGoodsType);
    }

    @Override
    public String toString()
    {
        return "CarType{" +
                "id='" + id + '\'' +
                ", carTypeDesignation=" + AARDesignation +
                ", carriedGoodsList=" + carriedGoodsList +
                '}';
    }

    public String displayName()
    {
        return AARDesignation.name();
    }
}
