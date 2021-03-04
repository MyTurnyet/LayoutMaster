package dev.paigewatson.layoutmaster.helpers;

import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;

import java.util.UUID;

public class TestFreightCarCreator
{
    public static FreightCar gondola(String roadName, int roadNumber)
    {
        return gondola(UUID.randomUUID(), roadName, roadNumber);
    }

    public static FreightCar gondola(UUID uuid, String roadName, int roadNumber)
    {
        return gondola(uuid, roadName, roadNumber, UUID.randomUUID());
    }

    public static FreightCar gondola(UUID uuid, String roadName, int roadNumber, UUID carTypeUUID)
    {
        return new FreightCar(uuid, roadName, roadNumber, TestAARTypeCreator.gondolaType(carTypeUUID));
    }

    public static FreightCar flatcar(String roadName, int roadNumber)
    {
        return flatcar(UUID.randomUUID(), roadName, roadNumber);
    }

    public static FreightCar flatcar(UUID flatcarUUID, String roadName, int roadNumber)
    {
        return new FreightCar(flatcarUUID, roadName, roadNumber, TestAARTypeCreator.flatcarType());
    }

    public static FreightCar flatcar(UUID flatcarUUID, String roadName, int roadNumber, UUID carTypeUUID)
    {
        return new FreightCar(flatcarUUID, roadName, roadNumber, TestAARTypeCreator.flatcarType(carTypeUUID));
    }

    public static FreightCar boxcar(String roadName, int roadNumber)
    {
        return boxcar(UUID.randomUUID(), roadName, roadNumber, UUID.randomUUID());
    }

    public static FreightCar boxcar(UUID uuid, String roadName, int roadNumber, UUID carTypeUUID)
    {
        return new FreightCar(uuid, roadName, roadNumber, TestAARTypeCreator.boxcarType(carTypeUUID));
    }

}
