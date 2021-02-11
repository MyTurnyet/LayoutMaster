package dev.paigewatson.layoutmaster.models.data;

import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "FreightCars")
public class FreightCarDto implements EntityDto<FreightCar>
{
    public String roadName = "";
    public int roadNumber = 0;
    public AARTypeDto carTypeDto;
    @Id
    public String id;

    public FreightCarDto()
    {
    }

    public FreightCarDto(String roadName, int roadNumber, AARTypeDto carTypeDto)
    {
        this(UUID.randomUUID(), roadName, roadNumber, carTypeDto);
    }

    public FreightCarDto(UUID uuid, String roadName, int roadNumber, AARTypeDto carTypeDto)
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

    @Override
    public FreightCar getEntity()
    {
        return null;
    }
}
