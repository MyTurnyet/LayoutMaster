package dev.paigewatson.layoutmaster.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.helpers.FreightCarServiceFake;
import dev.paigewatson.layoutmaster.models.data.AARTypeDto;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreightCarControllerTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        private AARTypeDto boxcarTypeDto;
        private UUID boxcarOneUUID;
        private FreightCarDto boxcarOne;
        private FreightCarDto boxcarTwo;
        private FreightCarDto boxcarThree;
        private FreightCarDto gondolaOne;
        private FreightCarDto flatCarOne;
        private FreightCarDto gondolaTwo;
        private FreightCarDto gondolaThree;
        private FreightCarServiceFake freightCarServiceFake;
        private FreightCarController freightCarController;

        @BeforeEach
        public void setupInventory()
        {
            freightCarServiceFake = new FreightCarServiceFake();
            boxcarTypeDto = new AARTypeDto("XM", Arrays.asList("Ingredients", "Paper", "Logs"));
            final AARTypeDto flatcarTypeDto = new AARTypeDto("FC", Arrays.asList("Parts", "Logs"));
            boxcarOneUUID = UUID.randomUUID();

            boxcarOne = new FreightCarDto(boxcarOneUUID, "PNWR", 2145, boxcarTypeDto);
            boxcarTwo = new FreightCarDto("BCR", 2342, boxcarTypeDto);
            boxcarThree = new FreightCarDto("PNWR", 2335, boxcarTypeDto);

            AARTypeDto gondolaTypeDto = new AARTypeDto("GS", Arrays.asList("MetalScraps", "ScrapMetal", "Aggregates"));
            gondolaOne = new FreightCarDto("BNSF", 1234, gondolaTypeDto);
            flatCarOne = new FreightCarDto("ATSF", 1232, flatcarTypeDto);
            gondolaTwo = new FreightCarDto("PNWR", 1235, gondolaTypeDto);
            gondolaThree = new FreightCarDto("BCR", 1237, gondolaTypeDto);

            freightCarController = new FreightCarController(freightCarServiceFake);
        }

        @Test
        public void should_saveFreightCar_toDatabase()
        {
            //assign
            final UUID gondolaTypeUUID = UUID.randomUUID();
            AARTypeDto gondolaTypeDto = new AARTypeDto(gondolaTypeUUID, "GS", Arrays.asList("MetalScraps", "ScrapMetal", "Aggregates"));
            final UUID gondolaToSaveUUID = UUID.randomUUID();
            final FreightCarDto gondolaToSave = new FreightCarDto(gondolaToSaveUUID, "BNSF", 1234, gondolaTypeDto);


            //act
            final FreightCarDto returnedFreightCarDto = freightCarController.saveFreightCarToDatabase(gondolaToSave);

            //assert
            assertThat(returnedFreightCarDto.toString()).isEqualTo(freightCarServiceFake.savedFreightCar.toString());
        }


        @Test
        public void should_getAllFreightCars()
        {
            //assign
            freightCarServiceFake.setReturnedFreightCars(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree,
                    flatCarOne, gondolaOne));

            //act
            final List<FreightCarDto> freightCarDtoList = freightCarController.getAllFreightCars();
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(5);
        }

        @Test
        public void should_getAllFreightCars_ofAARType()
        {
            //assign
            freightCarServiceFake.setReturnedFreightCars(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree));

            //act
            final List<FreightCarDto> freightCarDtoList = freightCarController.getAllFreightCarsOfAARType("XM");
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(3);
        }

        @Test
        public void should_getAllFreightCars_thatCarryGoodsType()
        {
            //assign
            freightCarServiceFake.setReturnedFreightCars(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree,
                    flatCarOne));

            //act
            final List<FreightCarDto> freightCarDtoList = freightCarController.getAllFreightCarsThatCarry("Logs");
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(4);
        }

        @Test
        public void should_getAllFreightCars_withRoadName()
        {
            //assign
            freightCarServiceFake.setReturnedFreightCars(Arrays.asList(
                    boxcarOne, boxcarTwo, boxcarThree,
                    flatCarOne));

            //act
            final List<FreightCarDto> freightCarDtoList = freightCarController.getAllFreightCarsWithRoadName("BCR");
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(4);
        }
    }

    @Nested
    @Tag("Integration")
    @ExtendWith(SpringExtension.class)
    @WebMvcTest(FreightCarController.class)
    public class IntegrationTests
    {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private FreightCarService freightCarService;
        private AARTypeDto boxcarTypeDto;
        private UUID boxcarOneUUID;
        private FreightCarDto boxcarOne;
        private FreightCarDto boxcarTwo;
        private FreightCarDto boxcarThree;
        private FreightCarDto gondolaOne;
        private FreightCarDto flatCarOne;
        private FreightCarDto gondolaTwo;
        private FreightCarDto gondolaThree;
        private UUID gondolaUUID;
        private UUID flatcarUUID;
        private UUID gondolaTypeUUID;
        private UUID flatcarTypeUUID;
        private UUID boxcarTypeUUID;
        private UUID boxcarTwoUUID;

        @BeforeEach
        private void setupTests()
        {
            boxcarTypeUUID = UUID.randomUUID();
            boxcarTypeDto = new AARTypeDto(boxcarTypeUUID, "XM", Arrays.asList("Ingredients", "Paper", "Logs"));
            boxcarOneUUID = UUID.randomUUID();
            boxcarOne = new FreightCarDto(boxcarOneUUID, "PNWR", 2145, boxcarTypeDto);
            boxcarTwoUUID = UUID.randomUUID();
            boxcarTwo = new FreightCarDto(boxcarTwoUUID, "PNWR", 2342, boxcarTypeDto);
            boxcarThree = new FreightCarDto("PNWR", 2335, boxcarTypeDto);


            flatcarTypeUUID = UUID.randomUUID();
            final AARTypeDto flatcarTypeDto = new AARTypeDto(flatcarTypeUUID, "FC", Arrays.asList("Parts", "Logs"));
            flatcarUUID = UUID.randomUUID();
            flatCarOne = new FreightCarDto(flatcarUUID, "ATSF", 1232, flatcarTypeDto);

            gondolaTypeUUID = UUID.randomUUID();
            AARTypeDto gondolaTypeDto = new AARTypeDto(gondolaTypeUUID, "GS", Arrays.asList("MetalScraps", "ScrapMetal", "Aggregates"));
            gondolaUUID = UUID.randomUUID();
            gondolaOne = new FreightCarDto(gondolaUUID, "BNSF", 1234, gondolaTypeDto);
            gondolaTwo = new FreightCarDto("PNWR", 1235, gondolaTypeDto);
            gondolaThree = new FreightCarDto("BCR", 1237, gondolaTypeDto);

        }

        @Test
        public void should_returnAllFreightCars() throws Exception
        {
            when(freightCarService.allFreightCars()).thenReturn(Arrays.asList(
                    boxcarOne, gondolaOne, flatCarOne
            ));

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/inventory/freightcars")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[{\"roadName\":\"PNWR\",\"roadNumber\":2145,\"carTypeDto\":{\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Paper\",\"Logs\"],\"null\":false},\"id\":\"" +
                    boxcarOneUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"roadName\":\"BNSF\",\"roadNumber\":1234,\"carTypeDto\":{\"id\":\"" +
                    gondolaTypeUUID.toString() +
                    "\",\"aarType\":\"GS\",\"carriedGoods\":[\"MetalScraps\",\"ScrapMetal\",\"Aggregates\"],\"null\":false},\"id\":\"" +
                    gondolaUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"roadName\":\"ATSF\",\"roadNumber\":1232,\"carTypeDto\":{\"id\":\"" +
                    flatcarTypeUUID.toString() +
                    "\",\"aarType\":\"FC\",\"carriedGoods\":[\"Parts\",\"Logs\"],\"null\":false},\"id\":\"" +
                    flatcarUUID.toString() +
                    "\",\"null\":false}]");
        }

        @Captor
        ArgumentCaptor<FreightCarDto> dtoArgumentCaptor;

        @Test
        public void should_addFreightCarToDatabase() throws Exception
        {
            final String content = asJsonString(boxcarOne);
            mockMvc.perform(MockMvcRequestBuilders.post("/inventory/freightcars/add")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(freightCarService, times(1)).saveFreightCarToDatabase(dtoArgumentCaptor.capture());
            assertThat(dtoArgumentCaptor.getValue().toString()).isEqualTo(boxcarOne.toString());
        }

        private String asJsonString(final Object obj)
        {
            try
            {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        @Test
        public void should_returnAllFreightCarsByAARType() throws Exception
        {
            when(freightCarService.allFreightCarsByAARType(any())).thenReturn(Arrays.asList(
                    boxcarOne, boxcarTwo
            ));

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/inventory/freightcars/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[" +
                    "{\"roadName\":\"PNWR\",\"roadNumber\":2145,\"carTypeDto\":{\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Paper\",\"Logs\"],\"null\":false},\"id\":\"" +
                    boxcarOneUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"roadName\":\"PNWR\",\"roadNumber\":2342,\"carTypeDto\":{\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Paper\",\"Logs\"],\"null\":false},\"id\":\"" +
                    boxcarTwoUUID.toString() +
                    "\",\"null\":false}]");
        }

        @Test
        public void should_returnAllFreightCarsThatCarry_GoodsType() throws Exception
        {
            when(freightCarService.allFreightCarsThatCarry(any())).thenReturn(Arrays.asList(
                    boxcarOne, boxcarTwo, flatCarOne
            ));

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/inventory/freightcars/goods/Logs")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[" +
                    "{\"roadName\":\"PNWR\",\"roadNumber\":2145,\"carTypeDto\":{\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Paper\",\"Logs\"],\"null\":false},\"id\":\"" +
                    boxcarOneUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"roadName\":\"PNWR\",\"roadNumber\":2342,\"carTypeDto\":{\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Paper\",\"Logs\"],\"null\":false},\"id\":\"" +
                    boxcarTwoUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"roadName\":\"ATSF\",\"roadNumber\":1232,\"carTypeDto\":{\"id\":\"" +
                    flatcarTypeUUID.toString() +
                    "\",\"aarType\":\"FC\",\"carriedGoods\":[\"Parts\",\"Logs\"],\"null\":false},\"id\":\"" +
                    flatcarUUID.toString() +
                    "\",\"null\":false}]");
        }

        @Test
        public void should_returnAllFreightCarsWithRoadName_PNWR() throws Exception
        {
            when(freightCarService.allFreightCarsByRoadName(any())).thenReturn(Arrays.asList(
                    boxcarOne, boxcarTwo
            ));

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/inventory/freightcars/PNWR")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[" +
                    "{\"roadName\":\"PNWR\",\"roadNumber\":2145,\"carTypeDto\":{\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Paper\",\"Logs\"],\"null\":false},\"id\":\"" +
                    boxcarOneUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"roadName\":\"PNWR\",\"roadNumber\":2342,\"carTypeDto\":{\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Paper\",\"Logs\"],\"null\":false},\"id\":\"" +
                    boxcarTwoUUID.toString() +
                    "\",\"null\":false}]");
        }
    }
}