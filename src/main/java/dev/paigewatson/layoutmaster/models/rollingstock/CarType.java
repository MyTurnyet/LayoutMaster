package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "CarTypes")
public class CarType
{
    @Id
    public String id = "";
    private AARDesignation aarDesignation;
    private ArrayList<GoodsType> carriedGoodsList;

    public CarType()
    {
    }

    public CarType(AARDesignation aarDesignation, ArrayList<GoodsType> carriedGoodsList)
    {
        this("", aarDesignation, carriedGoodsList);

    }

    @PersistenceConstructor
    public CarType(String id, AARDesignation aarDesignation, ArrayList<GoodsType> carriedGoodsList)
    {
        this.id = id;
        this.aarDesignation = aarDesignation;
        this.carriedGoodsList = carriedGoodsList;
    }

    public AARDesignation getAarDesignation()
    {
        return aarDesignation;
    }

    public void setAarDesignation(AARDesignation aarDesignation)
    {
        this.aarDesignation = aarDesignation;
    }

    public ArrayList<GoodsType> getCarriedGoodsList()
    {
        return carriedGoodsList;
    }

    public void setCarriedGoodsList(ArrayList<GoodsType> carriedGoodsList)
    {
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

    @Override
    public String toString()
    {
        return "CarType{" +
                "id='" + id + '\'' +
                ", carTypeDesignation=" + aarDesignation +
                ", carriedGoodsList=" + carriedGoodsList +
                '}';
    }

    public String displayName()
    {
        return aarDesignation.name();
    }
}
