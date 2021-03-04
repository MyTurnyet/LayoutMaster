package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * FreightCar
 * Should know it's state
 * road name, road number, car type, what goods it can carry
 * if loaded or not
 */

@Document(collection = "FreightCars")
public class FreightCar implements RollingStock
{
    @Id
    public String id;
    private UUID uuid;
    public String roadName;
    public int roadNumber;
    public AARType carType;
    public GoodsType currentlyCarriedGoods = GoodsType.EMPTY;

    public FreightCar()
    {
    }

    public FreightCar(UUID uuid, String roadName, int roadNumber, AARType carType)
    {
        this.uuid = uuid;
        this.id = uuid.toString();
        this.roadName = roadName;
        this.roadNumber = roadNumber;
        this.carType = carType;
    }

    public boolean canCarry(GoodsType expectedGood)
    {
        return carType.canCarry(expectedGood);
    }

    public void load(GoodsType goodsToLoad)
    {
        if (!canCarry(goodsToLoad)) return;
        this.currentlyCarriedGoods = goodsToLoad;
    }

    public String displayName()
    {
        return carType.displayName() + " - " + roadName + " " + roadNumber;
    }

    @Override
    public String toString()
    {
        return "FreightCar{" +
                "id='" + id + '\'' +
                ", roadName='" + roadName + '\'' +
                ", roadNumber=" + roadNumber +
                ", carType=" + carType +
                ", currentlyCarriedGoods=" + currentlyCarriedGoods +
                '}';
    }


    @Override
    public boolean isNull()
    {
        return false;
    }

    @Override
    public boolean isAARType(AARDesignation expectedAARDesignation)
    {
        return this.carType.isOfType(expectedAARDesignation);
    }

    @Override
    public boolean isCarrying(GoodsType goodsType)
    {
        return currentlyCarriedGoods == goodsType;
    }
}
