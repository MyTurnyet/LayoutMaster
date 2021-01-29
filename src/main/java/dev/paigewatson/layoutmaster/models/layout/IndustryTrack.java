package dev.paigewatson.layoutmaster.models.layout;

import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;

import java.util.ArrayList;

public class IndustryTrack
{
    private final String trackName;
    private final int maximumNumberOfCars;
    private final ArrayList<FreightCar> carsAtIndustry;

    public IndustryTrack(String trackName, int maximumNumberOfCars)
    {
        this.trackName = trackName;
        this.maximumNumberOfCars = maximumNumberOfCars;
        carsAtIndustry = new ArrayList<>();
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
}
