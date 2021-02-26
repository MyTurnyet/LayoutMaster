package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GoodsControllerTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_returnAllGoods()
        {
            //assign
            final GoodsController goodsController = new GoodsController();

            //act
            final List<GoodsType> goodsTypes = goodsController.getAllGoods();
            //assert
            assertThat(goodsTypes.size()).isEqualTo(14);
        }
    }

    @Nested
    @Tag("Integration")
    @ExtendWith(SpringExtension.class)
    @WebMvcTest(GoodsController.class)
    class IntegrationTests
    {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void should_getAllGoods() throws Exception
        {
            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/goods")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[\"Aggregates\",\"Chemicals\",\"Coal\",\"MetalParts\",\"MetalScraps\",\"Ingredients\",\"Logs\",\"Lumber\",\"Oil\",\"Paper\",\"Parts\",\"SheetMetal\",\"ScrapMetal\",\"EMPTY\"]");
        }
    }
}