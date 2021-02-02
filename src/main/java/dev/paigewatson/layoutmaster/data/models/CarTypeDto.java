package dev.paigewatson.layoutmaster.data.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "CarTypes")
public class CarTypeDto
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

}
