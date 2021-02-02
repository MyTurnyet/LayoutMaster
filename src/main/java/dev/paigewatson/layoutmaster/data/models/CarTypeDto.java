package dev.paigewatson.layoutmaster.data.models;

import dev.paigewatson.layoutmaster.data.Dto;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "CarTypes")
public class CarTypeDto implements Dto<CarType>
{
    @Id
    public String id;
    public String aarType;
    public List<String> carriedGoods;


    public CarTypeDto(String id, String aarType, List<String> carriedGoods)
    {
        this.id = id;
        this.aarType = aarType;
        this.carriedGoods = carriedGoods;
    }

    public CarTypeDto()
    {
    }

    @Override
    public String toString()
    {
        return "CarTypeDto{" +
                "id='" + id + '\'' +
                ", aarType='" + aarType + '\'' +
                ", carriedGoods=" + carriedGoods +
                '}';
    }

    @Override
    public CarType getEntity()
    {
        ArrayList<GoodsType> goods = new ArrayList<>();
        for (String carriedGood : carriedGoods)
        {
            goods.add(GoodsType.valueOf(carriedGood));
        }
        return new CarType(this.id, AARDesignation.valueOf(aarType), goods);
    }
}
