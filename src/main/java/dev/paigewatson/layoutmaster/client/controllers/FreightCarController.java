package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import dev.paigewatson.layoutmaster.models.data.NullFreightCarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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
        return Collections.emptyList();
//        return freightCarService.allFreightCars();
    }

    @GetMapping(value = "/freightcars/aar/{aarType}")
    public List<FreightCarDto> getAllFreightCarsOfAARType(@PathVariable(value = "aarType") String aarType)
    {
        return Collections.emptyList();

//        return freightCarService.allFreightCarsByAARType(aarType);
    }

    @GetMapping(value = "/freightcars/goods/{goodsType}")
    public List<FreightCarDto> getAllFreightCarsThatCarry(@PathVariable(value = "goodsType") String goodsType)
    {
        return Collections.emptyList();
//        return freightCarService.allFreightCarsThatCarry(goodsType);
    }

    @GetMapping(value = "/freightcars/{roadname}")
    public List<FreightCarDto> getAllFreightCarsWithRoadName(@PathVariable(value = "roadname") String roadName)
    {
        return Collections.emptyList();
//        return freightCarService.allFreightCarsByRoadName(roadName);
    }

    @PostMapping(value = "/freightcars/add")
    public FreightCarDto saveFreightCarToDatabase(@RequestBody FreightCarDto freightCarToSave)
    {
        return new NullFreightCarDto();
//        return freightCarService.saveFreightCarToDatabase(freightCarToSave);
    }
}
