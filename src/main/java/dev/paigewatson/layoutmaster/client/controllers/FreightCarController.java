package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import org.springframework.beans.factory.annotation.Autowired;

public class FreightCarController
{
    private final FreightCarService freightCarService;

    public FreightCarController(@Autowired FreightCarService freightCarService)
    {

        this.freightCarService = freightCarService;
    }
}
