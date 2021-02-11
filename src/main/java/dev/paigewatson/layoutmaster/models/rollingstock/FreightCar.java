package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * FreightCar
 * Should know it's state
 * road name, road number, car type, what goods it can carry
 * if loaded or not
 */

public class FreightCar implements RollingStock
{
    @Id
    private final String id;
    private final UUID uuid;
    private final String roadName;
    private final int roadNumber;
    private final AARType carType;
    private GoodsType currentlyCarriedGoods = GoodsType.EMPTY;

    public FreightCar(String roadName, int roadNumber, AARType carType)
    {
        this(UUID.randomUUID(), roadName, roadNumber, carType);
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

    public boolean isLoaded()
    {
        return currentlyCarriedGoods != GoodsType.EMPTY;
    }

    public void load(GoodsType goodsToLoad)
    {
        if (!canCarry(goodsToLoad)) return;
        this.currentlyCarriedGoods = goodsToLoad;
    }

    public String displayName()
    {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(carType.displayName());
        stringBuilder.append(" - ");
        stringBuilder.append(roadName);
        stringBuilder.append(" ");
        stringBuilder.append(roadNumber);
        return stringBuilder.toString();
    }

    @Override
    public String toString()
    {
        return "FreightCar{" +
                "roadName='" + roadName + '\'' +
                ", roadNumber=" + roadNumber +
                ", carType=" + carType.toString() +
                ", currentlyCarriedGoods=" + currentlyCarriedGoods +
                '}';
    }

    @Override
    public boolean isNull()
    {
        return false;
    }

    @Override
    public FreightCar getDto()
    {
        return null;
    }
}
