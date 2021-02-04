package dev.paigewatson.layoutmaster.data.models;

import dev.paigewatson.layoutmaster.data.NullableEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;

@Document(collection = "CarTypes")
public class CarTypeDto implements NullableEntity
{
    @Id
    public String id = "";
    public String aarType = "";
    public List<String> carriedGoods = Collections.emptyList();


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
    public boolean isNull()
    {
        return false;
    }
}
