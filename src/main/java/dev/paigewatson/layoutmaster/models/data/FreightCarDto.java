package dev.paigewatson.layoutmaster.models.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;
import org.springframework.data.annotation.Id;

import java.util.UUID;


public class FreightCarDto implements EntityDto<FreightCar>
{
    public CarTypeDto<AARType> carTypeDto;
    public String roadName = "";
    public int roadNumber = 0;
    private UUID uuid = UUID.randomUUID();
    @Id
    public String id;

    public FreightCarDto()
    {
    }

    public FreightCarDto(UUID uuid, String roadName, int roadNumber, CarTypeDto<AARType> carTypeDto)
    {
        this.id = uuid.toString();
        this.uuid = uuid;
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
    @JsonIgnore
    public FreightCar getEntity()
    {
        return new FreightCar(uuid, roadName, roadNumber, carTypeDto.getEntity());
    }
}
