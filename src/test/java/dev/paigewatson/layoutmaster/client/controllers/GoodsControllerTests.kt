package dev.paigewatson.layoutmaster.client.controllers

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class GoodsControllerTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        @Test
        fun should_returnAllGoods() {
            //assign
            val goodsController = GoodsController()

            //act
            val goodsTypes = goodsController.allGoods
            //assert
            AssertionsForClassTypes.assertThat(goodsTypes.size).isEqualTo(14)
        }
    }

    @Nested
    @Tag("Integration")
    @ExtendWith(SpringExtension::class)
    @WebMvcTest(
        GoodsController::class
    )
    internal inner class IntegrationTests {
        @Autowired
        private val mockMvc: MockMvc? = null

        @Test
        @Throws(Exception::class)
        fun should_getAllGoods() {
            val result = mockMvc!!.perform(
                MockMvcRequestBuilders.get("/models/goods")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
            val contentAsString = result.response.contentAsString
            AssertionsForClassTypes.assertThat(contentAsString)
                .isEqualTo("[\"Aggregates\",\"Chemicals\",\"Coal\",\"MetalParts\",\"MetalScraps\",\"Ingredients\",\"Logs\",\"Lumber\",\"Oil\",\"Paper\",\"Parts\",\"SheetMetal\",\"ScrapMetal\",\"EMPTY\"]")
        }
    }
}
