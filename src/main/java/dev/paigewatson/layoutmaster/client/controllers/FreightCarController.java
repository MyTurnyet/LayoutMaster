package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class FreightCarController
{
    private final FreightCarService freightCarService;

    public FreightCarController(@Autowired FreightCarService freightCarService)
    {
        this.freightCarService = freightCarService;
    }

    @GetMapping(value = "/freightcars")
    public List<RollingStock> getAllFreightCars()
    {

        final List<RollingStock> allRollingStockList = freightCarService.allFreightCars();
        return allRollingStockList;
    }

    @GetMapping(value = "/freightcars/aar/{aarType}")
    public List<RollingStock> getAllFreightCarsOfAARType(@PathVariable(value = "aarType") AARDesignation aarType)
    {
        final List<RollingStock> rollingStockList = freightCarService.allFreightCarsByAARType(aarType);
        return rollingStockList;
    }

    @GetMapping(value = "/freightcars/{roadname}")
    public List<RollingStock> getAllFreightCarsWithRoadName(@PathVariable(value = "roadname") String roadName)
    {
        final List<RollingStock> rollingStockList = freightCarService.allFreightCarsByRoadName(roadName);
        return rollingStockList;
    }

    @PostMapping(value = "/freightcars/add")
    public RollingStock saveFreightCarToDatabase(@RequestBody FreightCar freightCarToSave)
    {

        final RollingStock saveFreightCarToDatabase = freightCarService.saveFreightCarToDatabase(freightCarToSave);
        return saveFreightCarToDatabase;
    }
}
