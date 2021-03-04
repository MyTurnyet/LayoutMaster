package dev.paigewatson.layoutmaster.client.controllers

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/models")
class GoodsController {
    @get:GetMapping(value = ["/goods"])
    val allGoods: List<GoodsType>
        get() = Arrays.asList(*GoodsType::class.java.enumConstants)
}
