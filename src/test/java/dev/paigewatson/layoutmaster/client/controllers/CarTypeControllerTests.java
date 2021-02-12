package dev.paigewatson.layoutmaster.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.helpers.CarTypeServiceFake;
import dev.paigewatson.layoutmaster.helpers.EntityCreator;
import dev.paigewatson.layoutmaster.models.data.AARTypeDto;
import dev.paigewatson.layoutmaster.models.data.CarTypeDto;
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
import org.springframework.boot.test.mock.mockito.MockBean;
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

import static dev.paigewatson.layoutmaster.helpers.EntityCreator.boxcarType;
import static dev.paigewatson.layoutmaster.helpers.EntityCreator.gondolaType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
            boxcarType = EntityCreator.boxcarType();
            gondolaCarType = EntityCreator.gondolaType();
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
            final List<CarTypeDto<AARType>> carTypeDtoList = Arrays.asList(boxcarType.getDto(), gondolaCarType.getDto());
            carTypeServiceFake.setReturnedCarTypeList(returnedCarTypes);

            //act
            final List<CarTypeDto<AARType>> allCarTypes = carTypeController.getAllCarTypes();
            //assert
            IntStream.range(0, allCarTypes.size()).forEachOrdered(i -> assertThat(allCarTypes.get(i).toString()).isEqualTo(carTypeDtoList.get(i).toString()));
        }

        @Test
        public void should_returnCarTypeByAAR()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(boxcarType);

            //act
            final CarTypeDto<? extends CarType> carTypeByAAR = carTypeController.getCarTypeByAAR("XM");

            //assert
            assertThat(carTypeByAAR.toString()).isEqualTo(boxcarType.getDto().toString());
        }

        @Test
        public void should_notReturnCarTypeByAAR()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(new NullCarType());

            //act
            final CarTypeDto<? extends CarType> carTypeByAAR = carTypeController.getCarTypeByAAR("XM");
            //assert
            assertThat(carTypeByAAR.isNull()).isTrue();
        }

        @Test
        public void should_returnCarTypesT_thatCarry_goods()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeList(Arrays.asList(boxcarType, gondolaCarType));

            //act
            final List<CarTypeDto<AARType>> carTypesThatCarryLogs = carTypeController.getCarTypesThatCarry("Logs");
            //assert
            assertThat(carTypesThatCarryLogs.size()).isEqualTo(2);
        }

        @Test
        public void should_saveCarTypeToRepository()
        {
            //assign
            final CarTypeDto<AARType> boxcarTypeDto = new AARTypeDto("XM", Collections.singletonList("SheetMetal"));

            //act
            carTypeController.addNewCarType(boxcarTypeDto);

            //assert
            assertThat(carTypeServiceFake.savedDtoEntity().getDto().toString()).isEqualTo(boxcarTypeDto.toString());
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

            assertThat(contentAsString).isEqualTo("[{" +
                    "\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    gondolaUUID.toString() +
                    "\",\"aarType\":\"GS\",\"carriedGoods\":[\"Parts\"],\"null\":false}]");
        }

        @Test
        public void should_returnAllCarTypeThatCarry_ExpectedGoods() throws Exception
        {
            List<CarType> returnedCarTypes = Arrays.asList(boxcarType, gondolaCarType);

            when(carTypeService.carTypesThatCarryGoodsType(any())).thenReturn(returnedCarTypes);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/goods/Paper")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\",\"id\":\"" +
                    boxCarUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Parts\",\"Paper\"],\"null\":false}" +
                    ",{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\",\"id\":\"" +
                    gondolaUUID.toString() +
                    "\",\"aarType\":\"GS\",\"carriedGoods\":[\"Parts\"],\"null\":false}]");
        }

        @Test
        public void should_returnCarTypeByAARType() throws Exception
        {
            when(carTypeService.carTypeForAAR(any())).thenReturn(boxcarType);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("{\"@class\":\"dev.paigewatson.layoutmaster.models.data.AARTypeDto\"," +
                    "\"id\":\"" +
                    boxCarUUID.toString() +
                    "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Parts\",\"Paper\"],\"null\":false}");
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

            assertThat(contentAsString).isEqualTo("{\"@class\":\"dev.paigewatson.layoutmaster.models.data.NullCarTypeDto\",\"null\":true}");
        }

        @Test
        public void should_addCarTypeToDatabase() throws Exception
        {

            //assign
            final CarTypeDto<AARType> boxcarTypeDto = boxcarType.getDto();
            mockMvc.perform(MockMvcRequestBuilders.post("/models/types/add")
                    .content(asJsonString(boxcarTypeDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(carTypeService, times(1)).saveCarTypeToDatabase(any());

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
    }
}