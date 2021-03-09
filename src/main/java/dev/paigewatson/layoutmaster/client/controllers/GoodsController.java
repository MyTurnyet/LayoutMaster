package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/models")
public class GoodsController
{

    @GetMapping(value = "/goods")
    public List<GoodsType> getAllGoods()
    {
        return Arrays.asList(GoodsType.class.getEnumConstants());
    }
}
