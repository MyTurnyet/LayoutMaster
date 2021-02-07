package dev.paigewatson.layoutmaster.data.models;

import dev.paigewatson.layoutmaster.data.NullableEntity;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class GoodsTypeDto implements NullableEntity
{
    public String category = "";
    public String goodsName = "";
    public String state = "";
    @Id
    private String id = "";

    public GoodsTypeDto()
    {

    }

    public GoodsTypeDto(String category, String goodsName, String state)
    {
        this(UUID.randomUUID(), category, goodsName, state);
    }

    public GoodsTypeDto(UUID uuid, String category, String goodsName, String state)
    {
        this.id = uuid.toString();
        this.category = category;
        this.goodsName = goodsName;
        this.state = state;
    }

    @Override
    public String toString()
    {
        return "GoodsTypeDto{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean isNull()
    {
        return false;
    }
}
