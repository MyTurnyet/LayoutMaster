package dev.paigewatson.layoutmaster.client.services;

import dev.paigewatson.layoutmaster.data.FreightCarRepository;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoFreightCarService implements FreightCarService
{
    private final FreightCarRepository freightCarRepository;

    public MongoFreightCarService(@Autowired FreightCarRepository freightCarRepository)
    {
        this.freightCarRepository = freightCarRepository;
    }

    @Override
    public List<FreightCarDto> allFreightCars()
    {
        return freightCarRepository.findAll();
    }

    @Override
    public List<FreightCarDto> allFreightCarsByAARType(String aarType)
    {
        return freightCarRepository.findAllByCarTypeDto_AarType(aarType);
    }

    @Override
    public List<FreightCarDto> allFreightCarsThatCarry(String goodsType)
    {
        return freightCarRepository.findAllByCarTypeDtoCarriedGoodsContains(goodsType);
    }

    @Override
    public List<FreightCarDto> allFreightCarsByRoadName(String roadName)
    {
        return freightCarRepository.findAllByRoadName(roadName);
    }

    @Override
    public FreightCarDto saveFreightCarToDatabase(FreightCarDto freightCarToSave)
    {
        final Optional<FreightCarDto> freightCarById = freightCarRepository.findById(freightCarToSave.id);
        if (!freightCarById.isPresent())
        {
            return freightCarRepository.insert(freightCarToSave);
        }
        freightCarToSave.id = freightCarById.get().id;
        return freightCarRepository.save(freightCarToSave);
    }
}
