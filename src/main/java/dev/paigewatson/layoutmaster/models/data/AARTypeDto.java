package dev.paigewatson.layoutmaster.models.data;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AARTypeDto implements CarTypeDto<AARType>
{
    @Id
    private String id = "";
    public String aarType = "";
    public List<String> carriedGoods = Collections.emptyList();

    public AARTypeDto()
    {
    }

    public AARTypeDto(String aarType, List<String> carriedGoods)
    {
        this(UUID.randomUUID(), aarType, carriedGoods);
    }

    public AARTypeDto(UUID id, String aarType, List<String> carriedGoods)
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

    @Override
    public AARType getEntity()
    {
        final UUID uuid = UUID.fromString(id);
        final AARDesignation aarDesignation = AARDesignation.valueOf(aarType);
        final List<GoodsType> goodsTypes = carriedGoods.stream().map(GoodsType::valueOf).collect(Collectors.toCollection(ArrayList::new));
        return new AARType(uuid, aarDesignation, goodsTypes);
    }
}
