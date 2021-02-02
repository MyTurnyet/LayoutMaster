package dev.paigewatson.layoutmaster.models.layout;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.goods.ProducedGood;
import dev.paigewatson.layoutmaster.models.goods.RequiredGood;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/**
 * Industry
 * Should know it's name and take in goods,
 * can produce goods
 * knows what rolling stock are at it's location and can load/unload them
 * knows how many cars it can hold
 */
public class Industry
{

    private final ArrayList<ProducedGood> producedGoods;
    private final String industryName;
    private final ArrayList<RequiredGood> acceptedGoodsList;
    private final ArrayList<IndustryTrack> industryTracks;
    @Id
    private String id;

    public Industry(String industryName, ArrayList<RequiredGood> acceptedGoodsList, ArrayList<ProducedGood> producedGoods, ArrayList<IndustryTrack> industryTracks)
    {
        this.industryTracks = industryTracks;
        this.industryName = industryName;
        this.acceptedGoodsList = acceptedGoodsList;
        this.producedGoods = producedGoods;
    }

    public String name()
    {
        return industryName;
    }

    public boolean needs(GoodsType goodsType)
    {
        return acceptedGoodsList.stream().anyMatch(requiredGood -> requiredGood.needs(goodsType));
    }

    public boolean produces(GoodsType goodsType)
    {
        return producedGoods.stream().anyMatch(producedGood -> producedGood.isOfType(goodsType));
    }

    @Override
    public String toString()
    {
        return "Industry{" +
                "id='" + id + '\'' +
                ", industryName='" + industryName + '\'' +
                ", acceptedGoodsList=" + acceptedGoodsList +
                ", producedGoods=" + producedGoods +
                ", industryTracks=" + industryTracks +
                '}';
    }
}
