package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class FreightCarDto implements NullableEntity
{
    public String roadName = "";
    public int roadNumber = 0;
    public CarTypeDto carTypeDto;
    @Id
    private String id;

    public FreightCarDto()
    {
    }

    public FreightCarDto(String roadName, int roadNumber, CarTypeDto carTypeDto)
    {
        this(UUID.randomUUID(), roadName, roadNumber, carTypeDto);
    }

    public FreightCarDto(UUID uuid, String roadName, int roadNumber, CarTypeDto carTypeDto)
    {
        this.id = uuid.toString();
        this.roadName = roadName;
        this.roadNumber = roadNumber;
        this.carTypeDto = carTypeDto;
    }

    @Override
    public String toString()
    {
        return "FreightCarDto{" +
                "id='" + id + '\'' +
                ", roadName='" + roadName + '\'' +
                ", roadNumber=" + roadNumber +
                ", carTypeDto=" + carTypeDto +
                '}';
    }

    @Override
    public boolean isNull()
    {
        return false;
    }
}
