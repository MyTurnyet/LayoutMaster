package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.data.models.FreightCarDto;
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
    public List<FreightCarDto> getAllFreightCars()
    {
        return freightCarService.allFreightCars();
    }

    @GetMapping(value = "/freightcars/aar/{aarType}")
    public List<FreightCarDto> getAllFreightCarsOfAARType(@PathVariable(value = "aarType") String aarType)
    {
        return freightCarService.allFreightCarsByAARType(aarType);
    }

    @GetMapping(value = "/freightcars/goods/{goodsType}")
    public List<FreightCarDto> getAllFreightCarsThatCarry(@PathVariable(value = "goodsType") String goodsType)
    {
        return freightCarService.allFreightCarsThatCarry(goodsType);
    }

    @GetMapping(value = "/freightcars/{roadname}")
    public List<FreightCarDto> getAllFreightCarsWithRoadName(@PathVariable(value = "roadname") String roadName)
    {
        return freightCarService.allFreightCarsByRoadName(roadName);
    }

    @PostMapping(value = "/freightcars/add")
    public FreightCarDto saveFreightCarToDatabase(@RequestBody FreightCarDto freightCarToSave)
    {
        return freightCarService.saveFreightCarToDatabase(freightCarToSave);
    }
}
