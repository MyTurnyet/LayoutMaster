package dev.paigewatson.layoutmaster.data.models;

import dev.paigewatson.layoutmaster.data.NullableEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Document(collection = "CarTypes")
public class CarTypeDto implements NullableEntity
{
    @Id
    private String id = "";
    public String aarType = "";
    public List<String> carriedGoods = Collections.emptyList();

    public CarTypeDto()
    {
    }

    public CarTypeDto(String aarType, List<String> carriedGoods)
    {
        this(UUID.randomUUID(), aarType, carriedGoods);
    }

    public CarTypeDto(UUID id, String aarType, List<String> carriedGoods)
    {
        this.id = id.toString();
        this.aarType = aarType;
        this.carriedGoods = carriedGoods;
    }

    public String getId()
    {
        return id;
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
