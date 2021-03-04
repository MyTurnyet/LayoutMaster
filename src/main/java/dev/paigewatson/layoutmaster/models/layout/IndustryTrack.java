package dev.paigewatson.layoutmaster.models.layout;

import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;

import java.util.ArrayList;

public class IndustryTrack
{
    private final String trackName;
    private final int maximumNumberOfCars;
    private final ArrayList<FreightCar> carsAtIndustry = new ArrayList<>();

    public IndustryTrack(String trackName, int maximumNumberOfCars)
    {
        this.trackName = trackName;
        this.maximumNumberOfCars = maximumNumberOfCars;
    }

    public String name()
    {
        return trackName;
    }

    public boolean hasOpenSlots()
    {
        return carsAtIndustry.size() < maximumNumberOfCars;
    }

    public void setOutCar(FreightCar freightCar)
    {
        carsAtIndustry.add(freightCar);
    }

    @Override
    public String toString()
    {
        return "IndustryTrack{" +
                "trackName='" + trackName + '\'' +
                ", maximumNumberOfCars=" + maximumNumberOfCars +
                ", carsAtIndustry=" + carsAtIndustry +
                '}';
    }
}
