package dev.paigewatson.layoutmaster.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.helpers.CarTypeServiceFake;
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.boxcarType;
import static dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.gondolaType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarTypeControllerTests
{

    @Nested
    @Tag("Unit")
    class UnitTests
    {
        private CarTypeController carTypeController;
        private CarTypeServiceFake carTypeServiceFake;
        private CarType boxcarType;
        private CarType gondolaCarType;

        @BeforeEach
        public void setUp()
        {
            carTypeServiceFake = new CarTypeServiceFake();
            carTypeController = new CarTypeController(carTypeServiceFake);
            boxcarType = TestAARTypeCreator.boxcarType();
            gondolaCarType = TestAARTypeCreator.gondolaType();
        }

        @Test
        public void should_returnAllAARDesignations()
        {
            final List<AARDesignation> aarDesignations = carTypeController.getAARDesignations();
            assertThat(aarDesignations.size()).isEqualTo(24);
        }

        @Test
        public void should_returnAllCarTypes()
        {
            //assign
            List<CarType> returnedCarTypes = Arrays.asList(boxcarType, gondolaCarType);
            final List<CarType> carTypeDtoList = Arrays.asList(boxcarType, gondolaCarType);
            carTypeServiceFake.setReturnedCarTypeList(returnedCarTypes);

            //act
            final List<CarType> allCarTypes = carTypeController.getAllCarTypes();
            //assert
            IntStream.range(0, allCarTypes.size()).forEachOrdered(i -> assertThat(allCarTypes.get(i).toString()).isEqualTo(carTypeDtoList.get(i).toString()));
        }

        @Test
        public void should_returnCarTypeByAAR()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(boxcarType);

            //act
            final CarType carTypeByAAR = carTypeController.getCarTypeByAAR("XM");

            //assert
            assertThat(carTypeByAAR.toString()).isEqualTo(boxcarType.toString());
        }

        @Test
        public void should_notReturnCarTypeByAAR()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(new NullCarType());

            //act
            final CarType carTypeByAAR = carTypeController.getCarTypeByAAR("XM");
            //assert
            assertThat(carTypeByAAR.isNull()).isTrue();
        }

        @Test
        public void should_returnCarTypesT_thatCarry_goods()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeList(Arrays.asList(boxcarType, gondolaCarType));

            //act
            final List<CarType> carTypesThatCarryLogs = carTypeController.getCarTypesThatCarry("Logs");
            //assert
            assertThat(carTypesThatCarryLogs.size()).isEqualTo(2);
        }

        @Test
        public void should_saveCarTypeToRepository()
        {
            //assign
            final CarType boxcarType = TestAARTypeCreator.boxcarType();

            //act
            carTypeController.addNewCarType(boxcarType);

            //assert
            assertThat(carTypeServiceFake.savedDtoEntity().toString()).isEqualTo(boxcarType.toString());
        }
    }

    @Nested
    @Tag("Integration")
    @ExtendWith(SpringExtension.class)
    @WebMvcTest(CarTypeController.class)
    public class IntegrationTests
    {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CarTypeService carTypeService;

        private AARType boxcarType;
        private AARType gondolaCarType;
        private UUID boxCarUUID;
        private UUID gondolaUUID;

        @BeforeEach
        public void setupTests()
        {
            boxCarUUID = UUID.randomUUID();
            gondolaUUID = UUID.randomUUID();
            boxcarType = boxcarType(boxCarUUID);
            gondolaCarType = gondolaType(gondolaUUID);
        }

        @Test
        public void should_returnAllAARDesignations() throws Exception
        {
            when(carTypeService.allAARDesignations()).thenReturn(Arrays.asList(AARDesignation.values()));

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/aar")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[\"XA\",\"XM\",\"XP\",\"XL\",\"XR\",\"XF\",\"FA\",\"FBC\",\"FC\",\"FL\",\"FM\",\"TC\",\"GA\",\"GS\",\"HK\",\"HFA\",\"HT\",\"HTA\",\"TM\",\"TP\",\"RB\",\"RBL\",\"RP\",\"NULL\"]");
        }

        @Test
        public void should_returnAllCarTypes() throws Exception
        {
            final List<CarType> aarTypes = Collections.singletonList(gondolaCarType);
            when(carTypeService.allCarTypes()).thenReturn(aarTypes);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[{\"aarDesignation\":\"GS\",\"carriedGoodsList\":[\"ScrapMetal\",\"MetalScraps\",\"Logs\",\"Aggregates\"],\"id\":\"" +
                    gondolaUUID.toString() +
                    "\",\"null\":false}]");
        }

        @Test
        public void should_returnAllCarTypeThatCarry_ExpectedGoods() throws Exception
        {
            List<CarType> returnedCarTypes = Arrays.asList(boxcarType, gondolaCarType);

            when(carTypeService.carTypesThatCarryGoodsType(GoodsType.Logs)).thenReturn(returnedCarTypes);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/goods/Logs")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[{\"aarDesignation\":\"XM\",\"carriedGoodsList\":[\"Ingredients\",\"Logs\",\"Parts\"],\"id\":\"" +
                    boxCarUUID.toString() +
                    "\",\"null\":false},{\"aarDesignation\":\"GS\",\"carriedGoodsList\":[\"ScrapMetal\",\"MetalScraps\",\"Logs\",\"Aggregates\"],\"id\":\"" +
                    gondolaUUID.toString() +
                    "\",\"null\":false}]");
        }

        @Test
        public void should_returnCarTypeByAARType() throws Exception
        {
            when(carTypeService.allAARDesignations()).thenReturn(Arrays.asList(AARDesignation.values()));

            when(carTypeService.carTypeForAAR(any())).thenReturn(boxcarType);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("{\"aarDesignation\":\"XM\",\"carriedGoodsList\":[\"Ingredients\",\"Logs\",\"Parts\"],\"id\":\"" +
                    boxCarUUID.toString() +
                    "\",\"null\":false}");
        }

        @Test
        public void should_notReturnCarTypeByAARType() throws Exception
        {
            when(carTypeService.carTypeForAAR(any())).thenReturn(new NullCarType());
            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("{\"null\":true}");
        }

//        @Test
//        public void should_addCarTypeToDatabase() throws Exception
//        {
//
//            //assign
//            final CarType boxcarTypeDto = boxcarType;
//            mockMvc.perform(MockMvcRequestBuilders.post("/models/types/add")
//                    .content(asJsonString(boxcarTypeDto))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//
//            verify(carTypeService, times(1)).saveCarTypeToDatabase(any());
//
//        }

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
    }

    @Nested
    @Tag("Data")
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class FunctionalTests
    {

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        public void should_returnAllCarTypes() throws Exception
        {
            final String contentAsString = this.restTemplate.getForObject("http://localhost:" + port + "/models/types", String.class);

            assertThat(contentAsString).isEqualTo("[{\"aarDesignation\":\"XM\",\"carriedGoodsList\":[\"Ingredients\",\"Logs\",\"Parts\"],\"id\":\"24b5fd54-96e5-4c0e-916d-2c1d233b9456\",\"null\":false},{\"aarDesignation\":\"GS\",\"carriedGoodsList\":[\"ScrapMetal\",\"MetalScraps\",\"Logs\",\"Aggregates\"],\"id\":\"3507d92e-ac29-4b13-9c78-43813c258ce2\",\"null\":false},{\"aarDesignation\":\"FC\",\"carriedGoodsList\":[\"Logs\",\"Lumber\",\"Parts\"],\"id\":\"c5115111-7a14-4410-903e-4d513b1f9db5\",\"null\":false}]");
        }

        @Test
        public void should_returnAllAARDesignations() throws Exception
        {
            final String contentAsString = this.restTemplate.getForObject("http://localhost:" + port + "/models/aar", String.class);

            assertThat(contentAsString).isEqualTo("[\"XA\",\"XM\",\"XP\",\"XL\",\"XR\",\"XF\",\"FA\",\"FBC\",\"FC\",\"FL\",\"FM\",\"TC\",\"GA\",\"GS\",\"HK\",\"HFA\",\"HT\",\"HTA\",\"TM\",\"TP\",\"RB\",\"RBL\",\"RP\",\"NULL\"]");
        }


        @Test
        public void should_returnAllCarTypeThatCarry_ExpectedGoods() throws Exception
        {
            final String contentAsString = this.restTemplate.getForObject("http://localhost:" + port + "/models/types/goods/Logs", String.class);


            assertThat(contentAsString).isEqualTo("");
        }

        @Test
        public void should_returnCarTypeByAARType() throws Exception
        {
            final String contentAsString = this.restTemplate.getForObject("http://localhost:" + port + "/models/types/aar/XM", String.class);

            assertThat(contentAsString).isEqualTo("{\"aarDesignation\":\"XM\",\"carriedGoodsList\":[\"Ingredients\",\"Logs\",\"Parts\"],\"id\":\"" +
                    "\",\"null\":false}");
        }

        @Test
        public void should_notReturnCarTypeByAARType() throws Exception
        {
            final String contentAsString = this.restTemplate.getForObject("http://localhost:" + port + "/models/types/aar/QR", String.class);

            assertThat(contentAsString).isEqualTo("{\"null\":true}");
        }

    }
}