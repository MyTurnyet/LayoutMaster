package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.ConvertsToDto;
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
import java.util.stream.Collectors;

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

        final List<RollingStock> allRollingStockList = freightCarService.allFreightCars();
        return convertRollingStockListToDTOList(allRollingStockList);
    }

    private List<FreightCarDto> convertRollingStockListToDTOList(List<RollingStock> rollingStockList)
    {
        return rollingStockList.stream().map(ConvertsToDto::getDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/freightcars/aar/{aarType}")
    public List<FreightCarDto> getAllFreightCarsOfAARType(@PathVariable(value = "aarType") AARDesignation aarType)
    {
        final List<RollingStock> rollingStockList = freightCarService.allFreightCarsByAARType(aarType);
        return convertRollingStockListToDTOList(rollingStockList);
    }

    @GetMapping(value = "/freightcars/{roadname}")
    public List<FreightCarDto> getAllFreightCarsWithRoadName(@PathVariable(value = "roadname") String roadName)
    {
        final List<RollingStock> rollingStockList = freightCarService.allFreightCarsByRoadName(roadName);
        return convertRollingStockListToDTOList(rollingStockList);
    }

    @PostMapping(value = "/freightcars/add")
    public FreightCarDto saveFreightCarToDatabase(@RequestBody FreightCarDto freightCarToSave)
    {
        final FreightCar carToSaveEntity = freightCarToSave.getEntity();
        final RollingStock saveFreightCarToDatabase = freightCarService.saveFreightCarToDatabase(carToSaveEntity);
        return saveFreightCarToDatabase.getDto();
    }
}
