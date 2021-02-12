package dev.paigewatson.layoutmaster.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paigewatson.layoutmaster.client.services.FreightCarService;
import dev.paigewatson.layoutmaster.helpers.FreightCarServiceFake;
import dev.paigewatson.layoutmaster.helpers.TestFreightCarCreator;
import dev.paigewatson.layoutmaster.models.data.AARTypeDto;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        private RollingStock boxcarOne;
        private RollingStock boxcarTwo;
        private RollingStock boxcarThree;
        private RollingStock gondolaOne;
        private RollingStock flatCarOne;
        private FreightCarServiceFake freightCarServiceFake;
        private FreightCarController freightCarController;

        @BeforeEach
        public void setupInventory()
        {
            freightCarServiceFake = new FreightCarServiceFake();

            boxcarOne = TestFreightCarCreator.boxcar("PNWR", 2341);
            boxcarTwo = TestFreightCarCreator.boxcar("BCR", 2342);
            boxcarThree = TestFreightCarCreator.boxcar("PNWR", 2335);
            flatCarOne = TestFreightCarCreator.flatcar("ATSF", 1232);
            gondolaOne = TestFreightCarCreator.gondola("BNSF", 1234);

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
            final RollingStock savedFreightCar = freightCarServiceFake.savedFreightCar;
            assertThat(returnedFreightCarDto.toString()).isEqualTo(savedFreightCar.getDto().toString());
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
            final List<FreightCarDto> freightCarDtoList = freightCarController.getAllFreightCarsOfAARType(AARDesignation.XM);
            //assert
            assertThat(freightCarDtoList.size()).isEqualTo(3);
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
        private UUID boxcarOneUUID;
        private RollingStock boxcarOne;
        private RollingStock boxcarTwo;
        private RollingStock gondolaOne;
        private RollingStock flatCarOne;
        private UUID gondolaUUID;
        private UUID flatcarUUID;
        private UUID gondolaTypeUUID;
        private UUID flatcarTypeUUID;
        private UUID boxcarTypeUUID;
        private UUID boxcarTwoUUID;

        @BeforeEach
        public void setupTests()
        {
            boxcarOneUUID = UUID.randomUUID();
            boxcarTwoUUID = UUID.randomUUID();
            boxcarTypeUUID = UUID.randomUUID();
            boxcarOne = TestFreightCarCreator.boxcar(boxcarOneUUID, "PNWR", 2341, boxcarTypeUUID);
            boxcarTwo = TestFreightCarCreator.boxcar(boxcarTwoUUID, "BCR", 2342, boxcarTypeUUID);

            flatcarTypeUUID = UUID.randomUUID();
            flatcarUUID = UUID.randomUUID();
            flatCarOne = TestFreightCarCreator.flatcar(flatcarUUID, "ATSF", 1232, flatcarTypeUUID);

            gondolaTypeUUID = UUID.randomUUID();
            gondolaUUID = UUID.randomUUID();
            gondolaOne = TestFreightCarCreator.gondola(gondolaUUID, "BNSF", 1234, gondolaTypeUUID);
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

            assertThat(contentAsString).isEqualTo("[" +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.FreightCarDto\"," +
                    "\"carTypeDto\":" +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Logs\",\"Parts\"]," +
                    "\"null\":false}," +
                    "\"roadName\":\"PNWR\",\"roadNumber\":2341,\"id\":\"" +
                    boxcarOneUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.FreightCarDto\"," +
                    "\"carTypeDto\":" +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    gondolaTypeUUID.toString() +
                    "\",\"aarType\":\"GS\",\"carriedGoods\":[\"ScrapMetal\",\"MetalScraps\",\"Logs\",\"Aggregates\"],\"null\":false}," +
                    "\"roadName\":\"BNSF\",\"roadNumber\":1234,\"id\":\"" +
                    gondolaUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.FreightCarDto\"," +
                    "\"carTypeDto\":" +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    flatcarTypeUUID.toString() +
                    "\",\"aarType\":\"FC\",\"carriedGoods\":[\"Logs\",\"Lumber\",\"Parts\"],\"null\":false}," +
                    "\"roadName\":\"ATSF\",\"roadNumber\":1232,\"id\":\"" +
                    flatcarUUID.toString() +
                    "\",\"null\":false}]");
        }

        @Test
        public void should_addFreightCarToDatabase() throws Exception
        {
            when(freightCarService.saveFreightCarToDatabase(any())).thenReturn(boxcarOne);
            final String content = asJsonString(boxcarOne.getDto());
            mockMvc.perform(MockMvcRequestBuilders.post("/inventory/freightcars/add")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(freightCarService, times(1)).saveFreightCarToDatabase(any(RollingStock.class));

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
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.FreightCarDto\"," +
                    "\"carTypeDto\":{" +
                    "\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Logs\",\"Parts\"],\"null\":false}," +
                    "\"roadName\":\"PNWR\",\"roadNumber\":2341," +
                    "\"id\":\"" +
                    boxcarOneUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.FreightCarDto\"," +
                    "\"carTypeDto\":" +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Logs\",\"Parts\"],\"null\":false}," +
                    "\"roadName\":\"BCR\",\"roadNumber\":2342," +
                    "\"id\":\"" +
                    boxcarTwoUUID.toString() +
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
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.FreightCarDto\"," +
                    "\"carTypeDto\":" +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Logs\",\"Parts\"],\"null\":false}," +
                    "\"roadName\":\"PNWR\",\"roadNumber\":2341," +
                    "\"id\":\"" +
                    boxcarOneUUID.toString() +
                    "\",\"null\":false}," +
                    "{\"@class\":\"dev.paigewatson.layoutmaster.models.data.FreightCarDto\"," +
                    "\"carTypeDto\":{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    boxcarTypeUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Ingredients\",\"Logs\",\"Parts\"],\"null\":false}," +
                    "\"roadName\":\"BCR\",\"roadNumber\":2342," +
                    "\"id\":\"" +
                    boxcarTwoUUID.toString() +
                    "\",\"null\":false}]");
        }
    }
}